package com.example.codeshakeio.service.impl;

import com.example.codeshakeio.common.CommonConstants;
import com.example.codeshakeio.dto.ParentDTO;
import com.example.codeshakeio.dto.StudentDTO;
import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.externalapirequests.GateKeeperApiRequests;
import com.example.codeshakeio.mapper.*;
import com.example.codeshakeio.model.SynchronizationResults;
import com.example.codeshakeio.repository.SynchronizationResultsRepository;
import com.example.codeshakeio.service.CodeShakeSynchronizationService;
import com.example.codeshakeio.utils.JsonUtils;
import com.example.codeshakeio.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CodeShakeSynchronizationServiceImpl implements CodeShakeSynchronizationService {

    private final SynchronizationResultsRepository synchronizationResultsRepository;
    private final GateKeeperApiRequests gateKeeperApiRequests;

    @Override
    public List<SynchronizationResults> getSynchronizationResults() {
        return synchronizationResultsRepository.findAllByOperationStatusDone();
    }

    @Override
    public void checkForNewUpdateForEdushake() {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        try {

            List<UserDTO> allUsers = new ArrayList<>();

            List<UserDTO> usersFromEdushake = gateKeeperApiRequests.getUsersUsingGET().orElse(new ArrayList<>());
            List<StudentDTO> students = gateKeeperApiRequests.getStudentsUsingGET().orElse(new ArrayList<>());

            allUsers.addAll(ModelMapperUtils.mapAll(
                    ModelMapperUtils.getModelMapper(TeacherPropertyMapper.teacherPropertyMap),
                    gateKeeperApiRequests.getTeachersUsingGET().orElse(new ArrayList<>()),
                    UserDTO.class));

            allUsers.addAll(ModelMapperUtils.mapAll(
                    ModelMapperUtils.getModelMapper(StudentPropertyMapper.studentPropertyMap),
                    students,
                    UserDTO.class));

            List<ParentDTO> parents = new ArrayList<>();
            students.forEach(student -> {
                try {
                    gateKeeperApiRequests.getPersonUsingGET(student.getParentId()).ifPresent(parents::add);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            allUsers.addAll(ModelMapperUtils.mapAll(
                    ModelMapperUtils.getModelMapper(ParentPropertyMapper.parentPropertyMap),
                    parents,
                    UserDTO.class));

            Predicate<UserDTO> notInEduShake = s -> usersFromEdushake.stream().noneMatch(mc -> s.getId().equals(mc.getId()));
            List<UserDTO> usersToBeAdded = allUsers.stream().filter(notInEduShake).collect(Collectors.toList());
            log.info("Users to be added");
            log.info(JsonUtils.objectAsJSON(usersToBeAdded));
            log.info("******************************************************************************\n");
            Predicate<UserDTO> notInUsers = s -> allUsers.stream().noneMatch(mc -> s.getId().equals(mc.getId()));
            List<UserDTO> usersToBeDeleted = usersFromEdushake.stream().filter(notInUsers).collect(Collectors.toList());
            log.info("Users to be deleted");
            log.info(JsonUtils.objectAsJSON(usersToBeDeleted));
            log.info("******************************************************************************\n");

            usersToBeAdded.forEach(user -> {
                if (synchronizationResultsRepository.findByOperationStatusUndone(user.getEmail()).isEmpty()) {
                    log.info(JsonUtils.objectAsJSON(user));
                    SynchronizationResults synchronizationResults = ModelMapperUtils.map(
                            ModelMapperUtils.getModelMapper(SynchronizationResultsAddPropertyMapper.synchronizationResultsMap),
                            user,
                            SynchronizationResults.class);
                    log.info("SynchronizationResults : " + JsonUtils.objectAsJSON(synchronizationResults));
                    synchronizationResultsRepository.save(synchronizationResults);
                }
            });

            usersToBeDeleted.forEach(user -> {
                if (synchronizationResultsRepository.findByOperationStatusUndone(user.getEmail()).isEmpty()) {
                    log.info(JsonUtils.objectAsJSON(user));
                    SynchronizationResults synchronizationResults = ModelMapperUtils.map(
                            ModelMapperUtils.getModelMapper(SynchronizationResultsDeletePropertyMapper.synchronizationResultsMap),
                            user,
                            SynchronizationResults.class);
                    log.info("SynchronizationResults : " + JsonUtils.objectAsJSON(synchronizationResults));
                    synchronizationResultsRepository.save(synchronizationResults);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(CommonConstants.METHOD_END_MESSAGE);

    }

    @Override
    public void doSync() {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        try {

            List<SynchronizationResults> synchronizations = synchronizationResultsRepository.findAllByOperationStatusUnDoneAndAdd();

            if (synchronizations.isEmpty()) {
                log.info("No data available for sync ");
            } else {

                synchronizations.forEach(sync -> {
                    try {
                        gateKeeperApiRequests.registerUsingPOST(sync.getUserId()).ifPresent(sync::setFederationId);
                        log.info("Registering using post ended.FederationId of user: {} : {} ", sync.getEmail(), sync.getFederationId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                ResponseEntity<HttpStatus> responseEntity = gateKeeperApiRequests.saveUsersUsingPOST(ModelMapperUtils.mapAll(
                        ModelMapperUtils.getModelMapper(UserPropertyMapper.userMap
                        ), synchronizations, UserDTO.class
                ));

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    log.info("Saving user is successful");
                    synchronizations.forEach(sync -> {
                        sync.setProcessTime(LocalDateTime.now());
                        sync.setOperationStatus(OperationStatus.DONE);
                        synchronizationResultsRepository.save(sync);
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        log.info(CommonConstants.METHOD_END_MESSAGE);

    }
}
