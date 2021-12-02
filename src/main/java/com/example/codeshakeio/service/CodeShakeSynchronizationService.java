package com.example.codeshakeio.service;

import com.example.codeshakeio.model.SynchronizationResults;

import java.util.List;

public interface CodeShakeSynchronizationService {

    List<SynchronizationResults> getSynchronizationResults();

    void checkForNewUpdateForEdushake();

    void doSync();

}
