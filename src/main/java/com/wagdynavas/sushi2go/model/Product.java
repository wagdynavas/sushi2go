package com.wagdynavas.sushi2go.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    @NotNull(message = "Please, type a name for this product!")
    @Length(max = 250, message = "Product name can have a maximum of 250 characters!")
    private String productName;

    @Column(name = "PRODUCT_DESCRIPTION", nullable = false)
    @NotNull
    @Length(max = 1000, message = "Description can have a maximum of 1000 characters!")
    private String productDescription;

    @Column(name = "PRODUCT_PRICE", nullable = false)
    @NotNull(message = "Price can't ne null")
    private BigDecimal price ;

    @Column(name = "PRODUCT_IMG_PATH")
    private String productImagePath;

    @Column(name = "PRODUCT_CATEGORY", nullable = false)
    @NotNull(message = "Please, select a category")
    private String category;

    @Transient
    private Integer quantity;

    @Transient
    private String customerInstructions;


    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, HALF_UP);
    }
}
