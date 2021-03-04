package com.wagdynavas.sushi2go.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_ITEM_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORD_ITEM_ORDER_NUMBER", referencedColumnName = "ORD_ID", nullable = false)
    private Order orderId;

    @Column(name = "ORD_ITEM_QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "ORD_ITEM_NAME", nullable = false)
    private String name;

    @Column(name = "ORD_ITEM_SPECIAL_INSTRUCTIONS")
    private String instructions;
}
