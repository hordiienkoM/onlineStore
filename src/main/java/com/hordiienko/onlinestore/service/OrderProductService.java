package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.dto.parent_interface.OrderProductInfo;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderProductService {
    @Autowired
    private ProductService productService;


    public Set<OrderProduct> convert(Set<OrderProductPostDTO> orderProductDTOs, Order order, Locale locale) {
        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductPostDTO orderProductDTO : orderProductDTOs) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(orderProductDTO.getProductId());
            orderProduct.setAmount(orderProductDTO.getAmount());
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }

    public void addInfo(OrderProductInfo dto,
                        OrderProduct orderProduct,
                        Locale locale) {
        String description = productService
                .getProduct(orderProduct.getProductId(), locale)
                .getDescription();
        dto.setDescription(description);
    }

    protected String getOrderProductInfo(Order order, Locale locale) {
        Set<OrderProduct> orderProducts = order.getOrderProduct();
        Map<String, Product> productMap = productService.getProductMap(order, locale);
        StringBuilder orderProductInfo = new StringBuilder();
        orderProducts
                .forEach(el -> {
                            appendInfo(el, productMap, orderProductInfo);
                        }
                );
        return orderProductInfo.toString();
    }

    protected void appendInfo(OrderProduct orderProduct,
                              Map<String, Product> productMap,
                              StringBuilder orderProductInfo) {
        String description = productMap
                .get(orderProduct.getProductId())
                .getDescription();
        int amount = orderProduct.getAmount();
        orderProductInfo
                .append(description)
                .append(": ")
                .append(amount)
                .append("\n");
    }

}
