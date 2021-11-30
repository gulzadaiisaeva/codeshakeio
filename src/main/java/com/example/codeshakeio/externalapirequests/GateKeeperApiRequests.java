package com.example.codeshakeio.externalapirequests;

import com.example.codeshakeio.common.CommonConstants;
import com.example.codeshakeio.common.CommonFunctions;
import com.example.codeshakeio.dto.ParentDTO;
import com.example.codeshakeio.dto.StudentDTO;
import com.example.codeshakeio.dto.TeacherDTO;
import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.request.CommonRequestResponseService;
import com.example.codeshakeio.request.GetRequest;
import com.example.codeshakeio.request.PostRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GateKeeperApiRequests implements InitializingBean {

    private final GetRequest getRequest;
    private final PostRequest postRequest;
    private final CommonRequestResponseService commonRequestResponseService;

    @Value(value = "${gatekeeper.host}")
    private String gateKeeperApiBaseUri;



    @Override
    public void afterPropertiesSet() throws Exception {

    }


    public Optional<List<StudentDTO>> getStudentsUsingGET() throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/source/student");

        log.info("request url: {}", requestUrl);

        TypeReference<List<StudentDTO>> typeReferenceForResponse = new TypeReference<>() {
        };

        ResponseEntity<List<StudentDTO>> response = getRequest.get(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                typeReferenceForResponse
        );

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return Optional.of(Objects.requireNonNull(response.getBody()));
    }

    public Optional<StudentDTO> getStudentUsingGET(String id) throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/source/student/",id);

        log.info("request url: {}", requestUrl);

        TypeReference<StudentDTO> typeReferenceForResponse = new TypeReference<>() {
        };

        ResponseEntity<StudentDTO> response = getRequest.get(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                typeReferenceForResponse
        );

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return Optional.of(Objects.requireNonNull(response.getBody()));
    }

    public Optional<List<TeacherDTO>> getTeachersUsingGET() throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/source/teacher");

        log.info("request url: {}", requestUrl);

        TypeReference<List<TeacherDTO>> typeReferenceForResponse = new TypeReference<>() {
        };

        ResponseEntity<List<TeacherDTO>> response = getRequest.get(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                typeReferenceForResponse
        );

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return Optional.of(Objects.requireNonNull(response.getBody()));
    }

    public Optional<TeacherDTO> getTeacherUsingGET(String id) throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);

        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/source/teacher/",id);

        log.info("request url: {}", requestUrl);

        TypeReference<TeacherDTO> typeReferenceForResponse = new TypeReference<>() {
        };

        ResponseEntity<TeacherDTO> response = getRequest.get(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                typeReferenceForResponse
        );

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return Optional.of(Objects.requireNonNull(response.getBody()));
    }

    public Optional<ParentDTO> getPersonUsingGET(String id) throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);

        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/government/person/",id);

        log.info("request url: {}", requestUrl);

        TypeReference<ParentDTO> typeReferenceForResponse = new TypeReference<>() {
        };

        ResponseEntity<ParentDTO> response = getRequest.get(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                typeReferenceForResponse
        );

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return Optional.of(Objects.requireNonNull(response.getBody()));
    }

    public Optional<List<UserDTO>> getUsersUsingGET() throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/target/user");

        log.info("request url: {}", requestUrl);

        TypeReference<List<UserDTO>> typeReferenceForResponse = new TypeReference<>() {
        };

        ResponseEntity<List<UserDTO>> response = getRequest.get(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                typeReferenceForResponse
        );

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return Optional.of(Objects.requireNonNull(response.getBody()));
    }

    @SuppressWarnings("unchecked")
    public HttpStatus saveUsersUsingPOST(List<UserDTO> users) throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/target/user");

        log.info("request url: {}", requestUrl);

        ResponseEntity<HttpStatus> response = (ResponseEntity<HttpStatus>) postRequest.post(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                users,
                new ParameterizedTypeReference<HttpStatus>() {
                });

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return response.getBody();
    }

    @SuppressWarnings("unchecked")
    public Optional<String> registerUsingPOST(String id) throws Exception {
        log.info(CommonConstants.METHOD_START_MESSAGE);
        String requestUrl = CommonFunctions.concatenateStrings(gateKeeperApiBaseUri,"/api/sync/federation/registration/",id);

        log.info("request url: {}", requestUrl);

        ResponseEntity<String> response = (ResponseEntity<String>) postRequest.post(
                new URI(requestUrl),
                commonRequestResponseService.prepareHttpHeader(),
                new org.json.JSONObject(),
                new ParameterizedTypeReference<String>() {
                });

        log.info(CommonConstants.METHOD_END_MESSAGE);
        return Optional.of(Objects.requireNonNull(response.getBody()));
    }


}
