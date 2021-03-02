package com.wagdynavas.sushi2go.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
