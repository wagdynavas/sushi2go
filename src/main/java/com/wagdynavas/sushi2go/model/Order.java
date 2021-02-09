package com.wagdynavas.sushi2go.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_ID")
    private Long orderId;

    @Column(name = "ORD_STATUS", nullable = false)
    private String status;

    @Column(name = "ORD_DATE", nullable = false)
    private LocalDate orderDate;

    @Column(name = "ORD_CUSTOMER_INSTRUCTIONS", nullable = false)
    private String customerInstructions;

    @Column(name = "ORD_RESTAURANT_BRANCH", nullable = false)
    private String restaurantBranch;

    @Column(name = "ORD_DELIVER_ADDRESS")
    private String deliverAddress;

    @Column(name = "ORD_DELIVER_ADDRESS_COMPLEMENT")
    private String deliverAddressComplement;

    @Column(name = "ORD_DELIVER_INSTRUCTIONS" )
    private String deliverInstructions;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "ORD_TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    transient private Product product;

    //private PaymentDetails paymentDetails;
    //TODO: Payment_Details. criar objecto e verificar como funcina todo o mecanoismo  de pagamentos com cartao de cretito


}


