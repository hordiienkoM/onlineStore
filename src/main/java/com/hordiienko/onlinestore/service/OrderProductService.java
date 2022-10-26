package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class OrderProductService {
    @Autowired
    private ProductService productService;


    public Set<OrderProduct> convert(Set<OrderProductPostDTO> orderProductDTOs, Order order, Locale locale) {
        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductPostDTO orderProductDTO : orderProductDTOs) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(productService
                    .getProduct(orderProductDTO.getProduct().getId(), locale));
            orderProduct.setAmount(orderProductDTO.getAmount());
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }

}
