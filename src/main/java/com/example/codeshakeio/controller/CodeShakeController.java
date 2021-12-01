

package com.example.codeshakeio.controller;

import com.example.codeshakeio.dto.*;
import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.exception.ResourceNotFoundException;
import com.example.codeshakeio.externalapirequests.GateKeeperApiRequests;
import com.example.codeshakeio.model.User;
import com.example.codeshakeio.repository.SynchronizationResultsRepository;
import com.example.codeshakeio.repository.UserRepository;
import com.example.codeshakeio.scheduled.ScheduledTasks;
import com.example.codeshakeio.service.CodeShakeService;
import com.example.codeshakeio.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * The type User controller.
 *
 * @author Gulzada Iisaeva
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/codeshake")
public class CodeShakeController {

  private final UserRepository userRepository;
  private final GateKeeperApiRequests gateKeeperApiRequests;
  private final CodeShakeService codeShakeService;
  private final ScheduledTasks scheduledTasks;

  /**
   * Get all student list.
   *
   * @return the list
   */
  @GetMapping("/student")
  public ResponseEntity<List<StudentDTO>>  getStudents() throws Exception {

    List<StudentDTO> students = gateKeeperApiRequests.getStudentsUsingGET()
            .orElse(new ArrayList<>());

    return ResponseEntity.ok().body(students);
  }


  @GetMapping("/syncronization-info")
  public ResponseEntity<List<SynchronizationResultsDTO>>  getSynchronizationInfos() throws Exception {

    scheduledTasks.checkForNewUpdateEdushake();
    List<SynchronizationResultsDTO> synchronizationResults = ModelMapperUtils.mapAll(
            codeShakeService.getSynchronizationResults(),
            SynchronizationResultsDTO.class);

    return ResponseEntity.ok().body(synchronizationResults);
  }

  @GetMapping("/syncronization")
  public ResponseEntity<HttpStatus>  doSynchronization() throws Exception {

    scheduledTasks.updateEdushake();

    return ResponseEntity.ok().body(HttpStatus.OK);
  }

  /**
   * Gets students by id.
   *
   * @param studentId the user id
   * @return the users by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/student/{studentId}")
  public ResponseEntity<StudentDTO>  getStudent(@PathVariable String studentId) throws Exception {

    StudentDTO student = gateKeeperApiRequests.getStudentUsingGET(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found on :: " + studentId));

    return ResponseEntity.ok().body(student);
  }


  /**
   * Get all teacher list.
   *
   * @return the list
   */
  @GetMapping("/teacher")
  public ResponseEntity<List<TeacherDTO>>  getTeachers() throws Exception {

    List<TeacherDTO> students = gateKeeperApiRequests.getTeachersUsingGET()
            .orElse(new ArrayList<>());

    return ResponseEntity.ok().body(students);
  }

  /**
   * Gets teacher by id.
   *
   * @param teacherId the teacher id
   * @return the users by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/teacher/{teacherId}")
  public ResponseEntity<TeacherDTO>  getTeacher(@PathVariable String teacherId) throws Exception {

    TeacherDTO students = gateKeeperApiRequests.getTeacherUsingGET(teacherId)
            .orElseThrow(() -> new ResourceNotFoundException("teacher not found on :: " + teacherId));

    return ResponseEntity.ok().body(students);
  }


  /**
   * Gets family info by id.
   *
   * @param id the family id
   * @return the users by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/person/{id}")
  public ResponseEntity<ParentDTO>  getPerson(@PathVariable String id) throws Exception {

    ParentDTO students = gateKeeperApiRequests.getPersonUsingGET(id)
            .orElseThrow(() -> new ResourceNotFoundException("person not found on :: " + id));

    return ResponseEntity.ok().body(students);
  }
  /**
   * Create user user.
   *
   * @param id registration the user
   * @return the user
   */
  @PostMapping("/registration/{id}")
  public String registerUsingPOST(@PathVariable String id) throws Exception {
    return gateKeeperApiRequests.registerUsingPOST(id)
            .orElseThrow(() -> new ResourceNotFoundException("registration: " + id));
  }

  /**
   * Get all user list.
   *
   * @return the list
   */
  @GetMapping("/user")
  public ResponseEntity<List<UserDTO>>  getUsers() throws Exception {

    List<UserDTO> students = gateKeeperApiRequests.getUsersUsingGET()
            .orElse(new ArrayList<>());

    return ResponseEntity.ok().body(students);
  }

  /**
   * Create user user.
   *
   * @param users registration the user
   * @return the user
   */
  @PostMapping("/user")
  public ResponseEntity<HttpStatus> saveUsersUsingPOST(@RequestBody List<UserDTO> users) throws Exception {
    return gateKeeperApiRequests.saveUsersUsingPOST(users);
  }


  /**
   * Delete user map.
   *
   * @param userId the user id
   * @return the map
   * @throws Exception the exception
   */
  @DeleteMapping("/user/{id}")
  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

    userRepository.delete(user);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
