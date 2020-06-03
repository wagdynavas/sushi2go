package com.wagdynavas.sushi2go.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "PRODUCT_DESCRIPTION", nullable = false)
    private String productDescription;

    @Column(name = "PRODUCT_PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "PRODUCT_IMG_PATH")
    private String productImagePath;

    @Column(name = "PRODUCT_CATEGORY", nullable = false)
    private String category;

}
