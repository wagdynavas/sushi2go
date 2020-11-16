package com.wagdynavas.sushi2go.model;

import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.*;
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
    private String username;

    @Column(name = "USER_PASSWORD", nullable = false)
    @NotNull(message = "Password can not be null!")
    private String password;

    @Column(name = "USER_ROLE", nullable = false)
    private String role;

    transient private String confirmPassword;

    @Column(name = "USER_EMAIL", nullable = false)
    private String email;

    @Column(name = "USER_CREATION_DATE")
    private LocalDate creationDate;

}
