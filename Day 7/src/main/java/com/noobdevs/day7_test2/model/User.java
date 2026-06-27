package com.noobdevs.day7_test2.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    // 1:1 Relationship - Owning Side
    @Valid
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile userProfile;

    // ─────────────────────────────────────────────────────────────────
    // FETCH TYPE DEMO — change FetchType.LAZY to FetchType.EAGER here,
    // restart the app, then hit /api/demo/fetch/{id}/lazy again.
    // With LAZY  → 2 SQL statements in the console (User + Orders separately)
    // With EAGER → 1 SQL LEFT JOIN in the console (User + Orders together)
    // ─────────────────────────────────────────────────────────────────
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
