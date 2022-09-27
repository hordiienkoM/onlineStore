package com.hordiienko.onlinestore.repository;


import com.hordiienko.onlinestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
