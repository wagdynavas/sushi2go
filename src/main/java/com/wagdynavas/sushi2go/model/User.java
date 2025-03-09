package com.wagdynavas.sushi2go.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "WN_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_USERNAME", nullable = false, unique = true)
    @NotBlank(message = "username cannot be empty.")
    private String username;

    @Column(name = "USER_PASSWORD", nullable = false)
    @NotBlank(message = "Password cannot be empty.")
    private String password;

    @Column(name = "USER_ROLE", nullable = false)
    @NotBlank(message = "Select a role please.")
    private String role;

    @Transient
    private String confirmPassword;

    @Column(name = "USER_EMAIL", nullable = false)
    @NotBlank(message = "Email cannot be empty. Please enter a valid email, e.g., name@example.com")
    @Email(message = "Invalid email format. Please enter a valid email, e.g., name@example.com")
    private String email;

    @Column(name = "USER_CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "USER_RESTAURANT_BRANCH_PROFILE", nullable = false)
    @NotBlank(message = "Choose a restaurant branch please.")
    private String userRestaurantBranch;

}
