package com.hordiienko.onlinestore.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hordiienko.onlinestore.entity.enums.Brand;
import com.hordiienko.onlinestore.entity.enums.Category;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document("product")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @ToString.Exclude
    private String id;
    private String description;
    @DecimalMin("0.0")
    private Double price;
    private LocalDateTime dateCreate;
    @Enumerated(EnumType.STRING)
    private Brand brand;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ToString.Exclude
    private Set<Long> orderProducts = new HashSet<>();
}
