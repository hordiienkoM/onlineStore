package com.hordiienko.onlinestore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="ORDERS")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private GregorianCalendar dateCreation;
    private String address;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "order"
    )
    private List<OrderProduct> OrderProduct = new ArrayList<>();

    public List<com.hordiienko.onlinestore.entity.OrderProduct> getOrderProduct() {
        return OrderProduct;
    }

    public void setOrderProduct(List<com.hordiienko.onlinestore.entity.OrderProduct> orderProduct) {
        OrderProduct = orderProduct;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GregorianCalendar getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(GregorianCalendar dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
