package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {
    @InjectMocks
    private EmailSenderService emailSender;
    private Order order;

    @Before
    public void addOrder() {
        order = new Order();
        Product hat = new Product();
        Product pants = new Product();
        OrderProduct fiveHats = new OrderProduct();
        OrderProduct twoPants = new OrderProduct();

        hat.setId(1L);
        hat.setDescription("hat");
        pants.setId(2L);
        pants.setDescription("pants");
        fiveHats.setAmount(5);
        fiveHats.setProduct(hat);
        twoPants.setAmount(2);
        twoPants.setProduct(pants);
        order.setOrderProduct(new HashSet<>(Arrays.asList(fiveHats, twoPants)));
    }

    @Test
    public void checkListOfProducts() {
        String productList = emailSender.getProductList(order);
        String trueResult1 = "hat: 5\npants: 2\n";
        String trueResult2 = "pants: 2\nhat: 5\n";
        List<String> possibleTrueResults = Arrays.asList(trueResult1, trueResult2);
        Assert.assertTrue(possibleTrueResults.contains(productList));
    }
}
