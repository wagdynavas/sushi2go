package com.wagdynavas.sushi2go.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_ID")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "ORD_USER", nullable = false)
    private User user;

    @Column(name = "ORD_STATUS", nullable = false)
    private String status;

    @Column(name = "ORD_DATE", nullable = false)
    private LocalDate orderDate;

    @OneToMany
    private List<Product> products;

    transient private Product product;

    //private PaymentDetails paymentDetails;
    //TODO: Payment_Details. criar objecto e verificar como funcina todo o mecanoismo  de pagamentos com cartao de cretito


}


