package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByName(String name);
    List<Order> findAllByIdOrderById(Long id);
}
