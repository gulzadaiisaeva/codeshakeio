package com.example.codeshakeio.mapper;

import com.example.codeshakeio.dto.ParentDTO;
import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.enums.RoleType;
import org.modelmapper.PropertyMap;

public class ParentPropertyMapper {
    /**
     * source {ParentDTO} destination {UserDTO}
     */
    public final static PropertyMap<ParentDTO, UserDTO> parentPropertyMap = new PropertyMap<ParentDTO, UserDTO>() {
        protected void configure() {

            map().setRole(RoleType.PARENT);

        }
    };
}
