package com.example.codeshakeio.mapper;

import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.model.SynchronizationResults;
import org.modelmapper.PropertyMap;

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
