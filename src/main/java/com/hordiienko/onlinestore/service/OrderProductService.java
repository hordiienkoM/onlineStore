package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderProductService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderProductRepository orderProductRepository;

    public void saveOrderProducts(Set<OrderProductPostDTO> orderProductDTOs, Order order) {
        Set<OrderProduct> orderProducts = converter(orderProductDTOs, order);
        orderProductRepository.saveAll(orderProducts);
    }

    public Set<OrderProduct> converter(Set<OrderProductPostDTO> orderProductDTOs, Order order) {
        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductPostDTO orderProductDTO : orderProductDTOs) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(productService
                    .getProduct(orderProductDTO.getProduct().getId()));
            orderProduct.setAmount(orderProductDTO.getAmount());
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }
}
