package com.example.codeshakeio.repository;

import com.example.codeshakeio.model.SynchronizationResults;
import com.example.codeshakeio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 *
 * @author Gulzada Iisaeva
 */
@Repository
public interface SynchronizationResultsRepository extends JpaRepository<SynchronizationResults, Long> {


}
