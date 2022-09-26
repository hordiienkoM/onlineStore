package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}