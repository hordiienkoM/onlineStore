package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderProductServiceTest {
    @InjectMocks
    private OrderProductService orderProductService;
    @Mock
    private ProductService mockProductService;
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
    public void checkOrderProductInfo() {
        Map<String, Product> productMap = new HashMap<>();
        productMap.put(hat.getId(), hat);
        productMap.put(pants.getId(), pants);

        when(mockProductService.getProductMap(order, Locale.getDefault()))
                .thenReturn(productMap);

        String productList = orderProductService.getOrderProductInfo(order, Locale.getDefault());
        String trueResult1 = "hat: 5\npants: 2\n";
        String trueResult2 = "pants: 2\nhat: 5\n";
        List<String> possibleTrueResults = Arrays.asList(trueResult1, trueResult2);
        Assert.assertTrue(possibleTrueResults.contains(productList));
    }
}
