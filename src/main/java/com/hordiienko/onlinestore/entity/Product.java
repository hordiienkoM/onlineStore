package com.hordiienko.onlinestore.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "product"
    )
    private List<OrderProduct> orderProduct = new ArrayList<>();

    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }
}
