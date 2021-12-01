package com.example.codeshakeio.mapper;

import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.model.SynchronizationResults;
import org.modelmapper.PropertyMap;

public class SynchronizationResultsPropertyMapper {
	/**
	 * source {UserDTO} destination {SynchronizationResults}
	 */
	public final static PropertyMap<UserDTO, SynchronizationResults> synchronizationResultsMap = new PropertyMap<UserDTO, SynchronizationResults>() {
		protected void configure() {

			map().setUserId(source.getId());


		}
	};
}
