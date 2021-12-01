package com.example.codeshakeio.repository;

import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.model.SynchronizationResults;
import com.example.codeshakeio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 *
 * @author Gulzada Iisaeva
 */
@Repository
public interface SynchronizationResultsRepository extends JpaRepository<SynchronizationResults, Long> {


    Optional<SynchronizationResults> findByEmail(String email);

    @Query(value = "SELECT * FROM synchronization_results t WHERE operation_status= 'UNDONE' AND email =:email", nativeQuery = true)
    Optional<SynchronizationResults> findByOperationStatusUndone(String email);

    @Query(value = "SELECT * FROM synchronization_results t WHERE operation_status= 'DONE'", nativeQuery = true)
    List<SynchronizationResults> findAllByOperationStatusDone();

    @Query(value = "SELECT * FROM synchronization_results t WHERE operation_status= 'UNDONE' and operation_type='ADD'", nativeQuery = true)
    List<SynchronizationResults> findAllByOperationStatusUnDoneAndAdd();
}
