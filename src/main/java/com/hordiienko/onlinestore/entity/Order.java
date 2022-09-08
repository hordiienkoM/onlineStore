package com.hordiienko.onlinestore.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String dateCreation;
    private String address;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

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

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String date) {
        this.dateCreation = dateCreation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
