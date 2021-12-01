package com.example.codeshakeio.dto;

import com.example.codeshakeio.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
    @NotNull
    private String email;
    @NotNull
    private String federationId;
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private RoleType role;
}
