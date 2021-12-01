package com.example.codeshakeio.mapper;

import com.example.codeshakeio.dto.StudentDTO;
import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.enums.RoleType;
import org.modelmapper.PropertyMap;

public class StudentPropertyMapper {
	/**
	 * source {StudentDTO} destination {SynchronizationResults}
	 */
	public final static PropertyMap<StudentDTO, UserDTO> studentPropertyMap = new PropertyMap<StudentDTO, UserDTO>() {
		protected void configure() {

			map().setRoleType(RoleType.STUDENT);

		}
	};
}
