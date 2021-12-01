package com.example.codeshakeio.dto;

import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.enums.OperationType;
import com.example.codeshakeio.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SynchronizationResultsDTO implements Serializable {

    private String userId;
    private String email;
    private String name;
    private RoleType roleType;
    private String federationId;

    private OperationStatus operationStatus;
    private OperationType operationType;
    private LocalDateTime processTime;
    private LocalDateTime initializingTime;
}
