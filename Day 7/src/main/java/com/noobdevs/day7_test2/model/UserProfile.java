package com.noobdevs.day7_test2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number must be 7–15 digits, optionally starting with +")
    private String phoneNumber;

    // Bi-directional mapping (Optional)
    @OneToOne(mappedBy = "userProfile")
    private User user;
}
