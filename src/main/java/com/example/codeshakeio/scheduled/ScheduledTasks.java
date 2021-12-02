package com.example.codeshakeio.scheduled;


import com.example.codeshakeio.common.CommonConstants;
import com.example.codeshakeio.externalapirequests.GateKeeperApiRequests;
import com.example.codeshakeio.repository.SynchronizationResultsRepository;
import com.example.codeshakeio.service.CodeShakeSynchronizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
