package com.example.codeshakeio.model;


import com.example.codeshakeio.enums.OperationStatus;
import com.example.codeshakeio.enums.OperationType;
import com.example.codeshakeio.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "synchronization_results")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SynchronizationResults implements Serializable {

    @Id
    @SequenceGenerator(name = "synchronization_results_seq", sequenceName = "synchronization_results_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "synchronization_results_seq")
    @Column(nullable = false)
    private Long id;

    private String userId;
    private String email;
    private String name;
    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String federationId;

    @Enumerated(EnumType.STRING)
    private OperationStatus operationStatus;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private LocalDateTime processTime;
    private LocalDateTime initializingTime;

}
