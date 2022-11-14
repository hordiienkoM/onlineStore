package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.enums.Brand;
import com.hordiienko.onlinestore.entity.enums.Category;
import com.hordiienko.onlinestore.exception.ProductsDownloadException;
import com.hordiienko.onlinestore.service.util.PasswordGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DownloadService {
    private final Random generator = new Random();
    private final LocalDateTime currentTime = LocalDateTime.now();

    public synchronized List<Product> downloadProducts(Locale locale) {
        try {
            Document doc = Jsoup.connect("https://www.talbots.com/clothing?start=0&sz=500")
                    .timeout(30 * 1000).get();
            Elements descriptions = doc.select(".name-link");
            List<Product> products = new ArrayList<>();
            descriptions.forEach(x -> {
                Product product = new Product();
                product.setDescription(x.text() + " " + PasswordGenerator.getPassword());
                product.setPrice(getRandomPrice());
                product.setBrand(getRandomBrand());
                product.setCategory(getRandomCategory());
                product.setDateCreate(getRandomDateTime());
                products.add(product);
            });
            return products;
        } catch (IOException e) {
            throw new ProductsDownloadException(locale);
        }
    }

    private Category getRandomCategory() {
        int sizeCategory = Category.values().length;
        int random = generator.nextInt(sizeCategory);
        return Category.getById(random);
    }

    private LocalDateTime getRandomDateTime() {
        return currentTime.minusDays(generator.nextInt(90));
    }

    private Brand getRandomBrand() {
        int sizeBrand = Brand.values().length;
        int random = generator.nextInt(sizeBrand);
        return Brand.getById(random);
    }

    private Double getRandomPrice() {
        double randomPrice = generator.nextDouble(20, 800);
        return (int) (Math.round(randomPrice * 100)) / 100.0;
    }
}
