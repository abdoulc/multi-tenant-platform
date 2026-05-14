package com.abdel.io.platform.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "platform_users", schema = "public")
@Data
public class PlatformUser {

    @Id
    private UUID id;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    private boolean enabled = true;

    private Instant createdAt;
}