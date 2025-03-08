package com.wagdynavas.sushi2go.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @NotBlank(message = "Type your name please.")
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @NotBlank
    @Email(message = "Insert a valid email please.")
    @Column(name = "CUSTOMER_EMAIL")
    private String customerEmail;

    @NotBlank(message = "Type your phone number please.")
    @Column(name = "CUSTOMER_PHONE")
    private String customerPhone;
}
