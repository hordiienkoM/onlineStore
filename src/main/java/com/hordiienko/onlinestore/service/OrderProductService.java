package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderProductService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderProductRepository orderProductRepository;

    public void saveOrderProduct(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }

    public void saveOrderProducts(Set<OrderProductPostDTO> orderProductDTOs, Order order) {
        for (OrderProductPostDTO orderProductDTO : orderProductDTOs) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(productService
                    .getProduct(orderProductDTO.getProduct().getId()));
            orderProduct.setAmount(orderProductDTO.getAmount());
            orderProduct.setOrder(order);
            saveOrderProduct(orderProduct);
        }
    }
}
