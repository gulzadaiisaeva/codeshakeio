package com.example.codeshakeio.dto;

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
public class StudentDTO implements Serializable {
    @NotNull
    private String email;
    @NotNull
    private String id;
    @NotNull
    private String name;
    private String parentId;
}
