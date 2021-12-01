package com.example.codeshakeio.service.impl;

import com.example.codeshakeio.model.SynchronizationResults;
import com.example.codeshakeio.repository.SynchronizationResultsRepository;
import com.example.codeshakeio.service.CodeShakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CodeShakeServiceImpl implements CodeShakeService {

    private final SynchronizationResultsRepository synchronizationResultsRepository;

    @Override
    public List<SynchronizationResults> getSynchronizationResults() {
        return synchronizationResultsRepository.findAllByOperationStatusDone();
    }
}
