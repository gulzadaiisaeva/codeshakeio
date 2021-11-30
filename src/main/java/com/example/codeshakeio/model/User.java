

package com.example.codeshakeio.model;

import com.example.codeshakeio.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

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
