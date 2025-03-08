package com.wagdynavas.sushi2go.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORY_NAME", nullable = false)
    private String name;
}
