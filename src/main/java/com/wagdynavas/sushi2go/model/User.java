package com.wagdynavas.sushi2go.model;

import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_USERNAME", nullable = false, unique = true)
    @NotBlank(message = "username cannot be empty.")
    @NotNull(message = "username cannot be empty.")
    private String username;

    @Column(name = "USER_PASSWORD", nullable = false)
    @NotBlank(message = "Password cannot be empty.")
    @NotNull(message = "Password can not be null!")
    private String password;

    @Column(name = "USER_ROLE", nullable = false)
    @NotBlank(message = "Select a role please.")
    private String role;

    transient private String confirmPassword;

    @Column(name = "USER_EMAIL", nullable = false)
    @NotBlank(message = "email cannot be empty.")
    @NotNull(message = "email cannot be empty.")
    private String email;

    @Column(name = "USER_CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "USER_RESTAURANT_BRANCH_PROFILE", nullable = false)
    @NotBlank(message = "Choose a restaurant branch please.")
    @NotNull(message = "Choose a restaurant branch please.")
    private String userRestaurantBranch;

}
