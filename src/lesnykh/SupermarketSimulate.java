package lesnykh;

import lesnykh.SQL.Const;
import lesnykh.SQL.DataBaseHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupermarketSimulate {
    public static void main(String[] args) {
        simulateWeekInSupermarket();
    }

    public static void simulateWeekInSupermarket() {
        Random random = new Random();
        DataBaseHandler DBH = new DataBaseHandler();
        ProductGenerator PG = new ProductGenerator();
        LocalDate curDate = LocalDate.now();

        int productsToAdd = 5;
        System.out.println("В супермаркет поставили " + productsToAdd + " продуктов");
        for (int i = 0; i < productsToAdd; i++) {
            Product product = PG.generateRandomProducts();
            DBH.addProduct(product);
        }

        for (int day = 0; day < 7; day++) {

            System.out.println("День №" + (day + 1) + " в супермаркете");
            curDate = curDate.plusDays(1);
            DBH.deleteExpiredProductInfo(Date.valueOf(curDate));
            DBH.setSale(Date.valueOf(curDate));

            System.out.println("Список всех товаров в магазине: ");
            ResultSet allProductsResult = DBH.getAllProducts();

            try {
                while (allProductsResult.next()) {
                    String productName = allProductsResult.getString(Const.PRODUCT_NAME);
                    String productCategory = allProductsResult.getString(Const.PRODUCT_CATEGORY);
                    double productPrice = allProductsResult.getDouble(Const.PRODUCT_PRICE);
                    Date date = allProductsResult.getDate(Const.PRODUCTION_DATE);
                    Date expireDate = allProductsResult.getDate(Const.EXPIRY_DATE);
                    double quantity = allProductsResult.getDouble(Const.PRODUCT_QUANTITY);
                    String productType = allProductsResult.getString(Const.PRODUCT_TYPE);

                    System.out.println("Название: " + productName + ", Категория: " + productCategory + ", Цена: " + productPrice
                            + ", Дата изготовления: " + date + ", Годен до: " + expireDate + ", Количество: "
                            + quantity + " " + productType);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println();

            int productsToBuy = 1;
            for (int j = 1; j <= 3; j++) {
                System.out.println("Пришел покупатель №" + j + " и совершил покупку.");
                List<Product> purchasedProducts = new ArrayList<>();
                for (int k = 0; k < productsToBuy; k++) {
                    Product productToBuy = PG.generateRandomProducts();
                    int quantityToBuy = random.nextInt((int) productToBuy.getProductQuantity()) / 100 + 1;
                    DBH.decreaseProductQuantity(productToBuy, quantityToBuy);
                    productToBuy.setProductQuantity(quantityToBuy);
                    purchasedProducts.add(productToBuy);
                }
                System.out.println("Список покупок для покупателя №" + j + ": ");
                for (Product product : purchasedProducts) {
                    System.out.println("Название: " + product.getProductName() + ", Категория: " + product.getProductCategory()
                            + ", Цена: " + product.getProductPrice() + ", Дата изготовления: " + product.getProductionDate()
                            + ", Годен до: " + product.getExpireDate() + ", Количество: "
                            + product.getProductQuantity() + " " + product.getProductType());
                }
                productsToBuy++;
            }
            System.out.println();
        }
    }
}
