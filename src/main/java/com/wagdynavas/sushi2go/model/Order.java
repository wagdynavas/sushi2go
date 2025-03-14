package com.wagdynavas.sushi2go.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_ID")
    private Long orderId;

    @Column(name = "ORD_STATUS", nullable = false)
    private String status;

    @Column(name = "ORD_DATE", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "ORD_CUSTOMER_INSTRUCTIONS")
    private String customerInstructions;

    @Column(name = "ORD_DELIVERY_ADDRESS")
    private String deliveryAddress;

    @Column(name = "ORD_DELIVERY_ADDRESS_COMPLEMENT")
    private String deliveryAddressComplement;

    @Column(name = "ORD_DELIVERY_INSTRUCTIONS" )
    private String deliveryInstructions;

    @Transient
    private List<Product> products;

    @Column(name = "ORD_TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "ORD_RESTAURANT_BRANCH", nullable = false)
    private String restaurantBranch;

    @Column(name = "ORD_SUB_TOTAL_AMOUNT")
    private BigDecimal subTotalAmount;

    @Transient
    private Product product;

    @Column(name = "ORD_TIP", nullable = false)
    private BigDecimal tip;

    @Transient
    private BigDecimal tipPercentage;

    @Column(name = "ORD_TAX")
    private BigDecimal tax;

    @OneToOne
    @Valid
    @NotNull
    @JoinColumn(name = "ORD_CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    private Customer customer;
}


