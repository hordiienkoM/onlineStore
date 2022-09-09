package com.hordiienko.onlinestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @JsonIgnore
    private String password;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Order> orders = new HashSet<>();
}
