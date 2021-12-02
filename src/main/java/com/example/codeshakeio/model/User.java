package com.example.codeshakeio.model;

import com.example.codeshakeio.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type User.
 *
 * @author Gulzada Iisaeva
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @Column(nullable = false)
    private Long id;

    private String federationId;

    private RoleType roleType;

    private String parentId;

    private String name;

    @Column(name = "email", unique = true)
    private String email;


}
