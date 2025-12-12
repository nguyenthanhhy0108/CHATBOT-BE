package com.example.be.model.entity;

import com.example.be.model.standard.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "APP_USER")
public class User extends BaseAuditableEntity {

    @Column(name = "USERNAME", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "EMAIL", length = 150)
    private String email;

    @Column(name = "PASSWORD_HASH", nullable = false, columnDefinition = "TEXT")
    private String passwordHash;
}
