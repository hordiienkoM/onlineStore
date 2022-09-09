package com.hordiienko.onlinestore.repository;


import com.hordiienko.onlinestore.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
