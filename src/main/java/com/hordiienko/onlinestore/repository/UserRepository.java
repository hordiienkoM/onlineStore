package com.hordiienko.onlinestore.repository;


import com.hordiienko.onlinestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Set<User> findByOrdersIsNotNull();
}
