package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;


public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByUserId(Long userId, Pageable pageable);
}
