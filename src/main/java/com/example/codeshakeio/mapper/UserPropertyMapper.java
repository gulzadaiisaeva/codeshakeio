package com.example.codeshakeio.mapper;

import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.enums.OperationType;
import com.example.codeshakeio.model.SynchronizationResults;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;

public class UserPropertyMapper {
	/**
	 * source {SynchronizationResults} destination {UserDTO}
	 */
	public final static PropertyMap<SynchronizationResults, UserDTO> userMap = new PropertyMap<SynchronizationResults, UserDTO>() {
		protected void configure() {

			map().setId(source.getUserId());

		}
	};
}
