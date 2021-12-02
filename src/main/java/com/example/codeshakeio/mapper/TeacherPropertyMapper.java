package com.example.codeshakeio.mapper;

import com.example.codeshakeio.dto.TeacherDTO;
import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.enums.RoleType;
import org.modelmapper.PropertyMap;

public class TeacherPropertyMapper {
    /**
     * source {TeacherDTO} destination {UserDTO}
     */
    public final static PropertyMap<TeacherDTO, UserDTO> teacherPropertyMap = new PropertyMap<TeacherDTO, UserDTO>() {
        protected void configure() {

            map().setRole(RoleType.TEACHER);

        }
    };
}
