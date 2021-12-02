

package com.example.codeshakeio.controller;

import com.example.codeshakeio.common.CommonUtils;
import com.example.codeshakeio.dto.*;
import com.example.codeshakeio.enums.resultcode.FailureResultCode;
import com.example.codeshakeio.enums.resultcode.SuccessResultCode;
import com.example.codeshakeio.exception.unchecked.ResourceNotFoundException;
import com.example.codeshakeio.externalapirequests.GateKeeperApiRequests;
import com.example.codeshakeio.service.CodeShakeSynchronizationService;
import com.example.codeshakeio.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gulzada Iisaeva
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/codeshake")
public class CodeShakeController {

    private final GateKeeperApiRequests gateKeeperApiRequests;
    private final CodeShakeSynchronizationService codeShakeSynchronizationService;

    /**
     * Get all student list.
     *
     * @return the list
     */
    @GetMapping(path = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDTO>> getStudents() throws Exception {

        LocalDateTime serviceHitTime = LocalDateTime.now();

        List<StudentDTO> students = gateKeeperApiRequests.getStudentsUsingGET()
                .orElse(new ArrayList<>());

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, students);
    }


    @CrossOrigin
    @GetMapping(path = "/syncronization-results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SynchronizationResultsDTO>> getSynchronizationInfos() throws Exception {

        LocalDateTime serviceHitTime = LocalDateTime.now();

        List<SynchronizationResultsDTO> synchronizationResults = ModelMapperUtils.mapAll(
                codeShakeSynchronizationService.getSynchronizationResults(),
                SynchronizationResultsDTO.class);

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, synchronizationResults);
    }

    @GetMapping(path = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> doSynchronization() throws Exception {

        LocalDateTime serviceHitTime = LocalDateTime.now();

        codeShakeSynchronizationService.checkForNewUpdateForEdushake();
        codeShakeSynchronizationService.doSync();

        return CommonUtils.createNoContentResponseEntity(serviceHitTime,
                LocalDateTime.now(), SuccessResultCode.UPDATE_SUCCESSFUL);
    }

    /**
     * Gets students by id.
     *
     * @param studentId the user id
     * @return the users by id
     * @throws Exception the resource not found exception
     */
    @GetMapping(path = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String studentId) throws Exception {

        LocalDateTime serviceHitTime = LocalDateTime.now();

        StudentDTO student = gateKeeperApiRequests.getStudentUsingGET(studentId)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message(String.format("Getting teacher with %s failed", studentId))
                        .failureResultCode(FailureResultCode.OBJECT_NOT_DEFINED)
                        .build());

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, student);

    }


    /**
     * Get all teacher list.
     *
     * @return the list
     */
    @GetMapping(path = "/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeacherDTO>> getTeachers() throws Exception {
        LocalDateTime serviceHitTime = LocalDateTime.now();

        List<TeacherDTO> teachers = gateKeeperApiRequests.getTeachersUsingGET()
                .orElse(new ArrayList<>());

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, teachers);
    }

    /**
     * Gets teacher by id.
     *
     * @param teacherId the teacher id
     * @return the teachers
     * @throws Exception the resource not found exception
     */
    @GetMapping(path = "/teacher/{teacherId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable String teacherId) throws Exception {
        LocalDateTime serviceHitTime = LocalDateTime.now();

        TeacherDTO teacher = gateKeeperApiRequests.getTeacherUsingGET(teacherId)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message(String.format("Getting teacher with %s failed", teacherId))
                        .failureResultCode(FailureResultCode.OBJECT_NOT_DEFINED)
                        .build());

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, teacher);
    }


    /**
     * Gets family info by id.
     *
     * @param id the family id
     * @return
     * @throws Exception the resource not found exception
     */
    @GetMapping(path = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParentDTO> getPerson(@PathVariable String id) throws Exception {
        LocalDateTime serviceHitTime = LocalDateTime.now();

        ParentDTO parent = gateKeeperApiRequests.getPersonUsingGET(id)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message(String.format("Getting person with %s failed", id))
                        .failureResultCode(FailureResultCode.PERSISTENCE_ERROR)
                        .build());

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, parent);
    }

    /**
     * @param id registration the user
     * @return the user
     */
    @PostMapping(path = "/registration/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerUsingPOST(@PathVariable String id) throws Exception {

        LocalDateTime serviceHitTime = LocalDateTime.now();

        String federationId = gateKeeperApiRequests.registerUsingPOST(id)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message(String.format("Registration user with %s failed", id))
                        .failureResultCode(FailureResultCode.PERSISTENCE_ERROR)
                        .build());

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, federationId).getBody();
    }

    /**
     * Get all user list.
     *
     * @return the list
     */
    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() throws Exception {
        LocalDateTime serviceHitTime = LocalDateTime.now();

        List<UserDTO> users = gateKeeperApiRequests.getUsersUsingGET()
                .orElse(new ArrayList<>());

        return CommonUtils.createResponseEntity(HttpStatus.OK, HttpHeaders.EMPTY, serviceHitTime, LocalDateTime.now(),
                SuccessResultCode.GET_INFO_SUCCESSFUL, users);
    }

    /**
     * Saving users
     *
     * @param users saving the users
     * @return the user
     */
    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUsersUsingPOST(@RequestBody List<UserDTO> users) throws Exception {

        LocalDateTime serviceHitTime = LocalDateTime.now();

        gateKeeperApiRequests.saveUsersUsingPOST(users);

        return CommonUtils.createNoContentResponseEntity(serviceHitTime,
                LocalDateTime.now(), SuccessResultCode.UPDATE_SUCCESSFUL);
    }

    /**
     * Delete user user.
     *
     * @param userIds
     */
    @DeleteMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUsersUsingPOST(@RequestParam(name = "userIds") final List<String> userIds) throws Exception {

        LocalDateTime serviceHitTime = LocalDateTime.now();


        return CommonUtils.createNoContentResponseEntity(serviceHitTime,
                LocalDateTime.now(), SuccessResultCode.DELETE_SUCCESSFUL);
    }


}
