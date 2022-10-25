package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.exception.ProductsDownloadException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class DownloadService {

    public List<Product> downloadProducts(Locale locale) {
        try {
            Document doc = Jsoup.connect("https://www.talbots.com/clothing?start=0&sz=500")
                    .timeout(30*1000).get();
            Elements prices = doc.select(".product-sales-price");
            Elements descriptions = doc.select(".name-link");
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < prices.size(); i++) {
                Product product = new Product();
                product.setDescription(descriptions.get(i).text());
                String price = prices.get(i).text();
                product.setPrice(prices.get(i).text());
                if (isPrice(price)) {
                    products.add(product);
                }
            }
            return products;
        } catch (IOException e) {
            throw new ProductsDownloadException(locale);
        }
    }

    private boolean isPrice(String price) {
        String pattern = "^[$]\\d+[.]\\d{2}( - [$]\\d+[.]\\d{2})?$";
        return Pattern.matches(pattern, price);
    }
}
