package lesnykh;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductGenerator {
    public Product generateRandomProducts() {
        List<Product> originalProducts = new ArrayList<>();

        originalProducts.add(new Product("Шоколад", "Кондитерские изделия", 72, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 518400000), 700, "шт"));
        originalProducts.add(new Product("Молоко", "Молочные продукты", 69, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 432000000), 350, "л"));
        originalProducts.add(new Product("Хлеб", "Хлебобулочные изделия", 50, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 432000000), 100, "шт"));
        originalProducts.add(new Product("Яблоко", "Фрукты", 70, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 864000000), 100, " кг"));
        originalProducts.add(new Product("Чипсы", "Снэки", 105, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 1728000000), 500, " шт"));
        originalProducts.add(new Product("Масло", "Молочные продукты", 160, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 518400000), 600, "шт"));
        originalProducts.add(new Product("Картофель", "Овощи", 15, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 1728000000), 800, "кг"));
        originalProducts.add(new Product("Яйца", "Яйца", 98, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 518400000), 800, "шт"));
        originalProducts.add(new Product("Курица", "Мясо и птица", 240, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 518400000), 500, "кг"));
        originalProducts.add(new Product("Помидоры", "Овощи", 75, new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 1728000000), 800, "кг"));


        Random random = new Random();
        Product randomProduct = originalProducts.remove(random.nextInt(originalProducts.size()));
        return randomProduct;
    }
}
