package com.example.codeshakeio.scheduled;


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
    private CodeShakeSynchronizationService codeShakeSynchronizationService;

    @Scheduled(cron = "${task-scheduling.cron.run-per-five-minute}")
    public void checkForNewUpdateEdushake() {
        log.info(CommonConstants.METHOD_START_MESSAGE);

       // codeShakeSynchronizationService.checkForNewUpdateForEdushake();

        log.info(CommonConstants.METHOD_END_MESSAGE);

    }

    @Scheduled(cron = "${task-scheduling.cron.run-per-minute}")
    public void updateEdushake() {
        log.info(CommonConstants.METHOD_START_MESSAGE);

       // codeShakeSynchronizationService.doSync();

        log.info(CommonConstants.METHOD_END_MESSAGE);

    }
}
