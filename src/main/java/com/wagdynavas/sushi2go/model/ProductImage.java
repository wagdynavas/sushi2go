package com.wagdynavas.sushi2go.model;


import lombok.Data;

import jakarta.persistence.*;
@Entity
@Data
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "IMAGE_NAME")
    private String imageName;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;


}
