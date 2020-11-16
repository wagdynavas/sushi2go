package com.wagdynavas.sushi2go.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    @NotNull(message = "Please, type a name for this product!")
    @Length(max = 250, message = "Description can have a maximum of 250 characters!")
    private String productName;

    @Column(name = "PRODUCT_DESCRIPTION", nullable = false)
    @NotNull
    @Length(max = 1000, message = "Description can have a maximum of 1000 characters!")
    private String productDescription;

    @Column(name = "PRODUCT_PRICE", nullable = false)
    @NotNull(message = "Price can't ne null")
    private BigDecimal price;

    @Column(name = "PRODUCT_IMG_PATH")
    private String productImagePath;

    @Column(name = "PRODUCT_CATEGORY", nullable = false)
    @NotNull(message = "Please, select a category")
    private String category;


    transient private Integer Quantity;
}
