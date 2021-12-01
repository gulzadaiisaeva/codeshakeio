package com.example.codeshakeio.mapper;

import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.enums.OperationType;
import com.example.codeshakeio.model.SynchronizationResults;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;

public class SynchronizationResultsPropertyMapper {
	/**
	 * source {UserDTO} destination {SynchronizationResults}
	 */
	public final static PropertyMap<UserDTO, SynchronizationResults> synchronizationResultsMap = new PropertyMap<UserDTO, SynchronizationResults>() {
		protected void configure() {

			skip(destination.getId());
			map().setUserId(source.getId());
			map().setOperationStatus(OperationStatus.UNDONE);
			map().setOperationType(OperationType.ADD);
			map().setInitializingTime(LocalDateTime.now());

		}
	};
}
