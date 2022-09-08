package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.OrderUser;
import org.springframework.data.repository.CrudRepository;

public interface OrderUserRepository extends CrudRepository<OrderUser, Long> {
}
