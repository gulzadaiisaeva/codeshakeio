package com.example.codeshakeio.scheduled;


import com.example.codeshakeio.dto.ParentDTO;
import com.example.codeshakeio.dto.StudentDTO;
import com.example.codeshakeio.dto.TeacherDTO;
import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.enums.OperationType;
import com.example.codeshakeio.externalapirequests.GateKeeperApiRequests;
import com.example.codeshakeio.mapper.ParentPropertyMapper;
import com.example.codeshakeio.mapper.StudentPropertyMapper;
import com.example.codeshakeio.mapper.SynchronizationResultsPropertyMapper;
import com.example.codeshakeio.mapper.TeacherPropertyMapper;
import com.example.codeshakeio.model.SynchronizationResults;
import com.example.codeshakeio.repository.SynchronizationResultsRepository;
import com.example.codeshakeio.utils.JsonUtils;
import com.example.codeshakeio.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final GateKeeperApiRequests gateKeeperApiRequests;
    private final SynchronizationResultsRepository synchronizationResultsRepository;

    //@Scheduled(cron = "${task-scheduling.cron.run-per-five-minute}")
    public void checkAndUpdateEdushake() {
        try{

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

            List<ParentDTO> parents= new ArrayList<>();
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

            usersToBeAdded.forEach(user->{
                if(synchronizationResultsRepository.findByOperationStatusUndone(user.getEmail()).isEmpty()){
                    log.info(JsonUtils.objectAsJSON(user));
                    SynchronizationResults synchronizationResults = ModelMapperUtils.map(
                            ModelMapperUtils.getModelMapper(SynchronizationResultsPropertyMapper.synchronizationResultsMap),
                            user,
                            SynchronizationResults.class);
                    log.info("SynchronizationResults : " + JsonUtils.objectAsJSON(synchronizationResults));
                    synchronizationResultsRepository.save(synchronizationResults);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
