package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository mockProductRepository;
    private Order order;
    private Product hat;
    private Product pants;
    private final String HAT_ID = "hat-id";
    private final String PANTS_ID = "pants-id";

    @Before
    public void addProducts() {
        hat = new Product();
        pants = new Product();
        hat.setId(HAT_ID);
        hat.setDescription("hat");
        pants.setId(PANTS_ID);
        pants.setDescription("pants");
    }

    @Before
    public void addOrder() {
        order = new Order();
        OrderProduct fiveHats = new OrderProduct();
        OrderProduct twoPants = new OrderProduct();

        fiveHats.setAmount(5);
        fiveHats.setProductId(HAT_ID);
        twoPants.setAmount(2);
        twoPants.setProductId(PANTS_ID);
        order.setOrderProduct(new HashSet<>(Arrays.asList(fiveHats, twoPants)));
    }

    @Test
    public void getProductMapTest() {
        Map<String, Product> trueAnswer = new HashMap<>();
        trueAnswer.put(hat.getId(), hat);
        trueAnswer.put(pants.getId(), pants);

        Set<Product> products = new HashSet<>();

        when(mockProductRepository.findByIdIn(anySet())).thenReturn(products);
        Map<String, Product> answer = productService.getProductMap(order, Locale.getDefault());
        assertEquals(answer, trueAnswer);
    }
}
