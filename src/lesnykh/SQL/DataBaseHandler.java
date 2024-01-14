package lesnykh.SQL;

import lesnykh.Product;

import java.sql.*;

public class DataBaseHandler extends Configs {
    Connection dbConnection;


    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void addProduct(Product product) {
        String insert = "INSERT INTO " + Const.PRODUCT_TABLE + "(" + Const.PRODUCT_NAME + "," +
                Const.PRODUCT_CATEGORY + "," + Const.PRODUCT_PRICE + "," + Const.PRODUCTION_DATE + ","
                + Const.EXPIRY_DATE + "," + Const.PRODUCT_QUANTITY + "," + Const.PRODUCT_TYPE + ")" +
                "VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, product.getProductName());
            prSt.setString(2, product.getProductCategory());
            prSt.setDouble(3, product.getProductPrice());
            prSt.setDate(4, product.getProductionDate());
            prSt.setDate(5, product.getExpireDate());
            prSt.setDouble(6, product.getProductQuantity());
            prSt.setString(7, product.getProductType());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllProducts() {
        ResultSet rS = null;

        String select = "SELECT * FROM " + Const.PRODUCT_TABLE;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            rS = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rS;
    }

    public ResultSet getProduct(Product product) {
        ResultSet rS = null;

        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_NAME
                + "=? AND " + Const.PRODUCT_CATEGORY + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, product.getProductName());
            prSt.setString(2, product.getProductCategory());

            rS = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rS;
    }

    public void deleteAllProduct(Product product) {
        String delete = "DELETE FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_NAME
                + "=? AND " + Const.PRODUCT_CATEGORY + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, product.getProductName());
            prSt.setString(2, product.getProductCategory());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void decreaseProductQuantity(Product product, double quantityToDecrease) {
        String updateQuery = "UPDATE " + Const.PRODUCT_TABLE + " SET " + Const.PRODUCT_QUANTITY + " = " +
                Const.PRODUCT_QUANTITY + " - ? WHERE " + Const.PRODUCT_NAME + " = ? AND " +
                Const.PRODUCT_CATEGORY + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(updateQuery);
            prSt.setDouble(1, quantityToDecrease);
            prSt.setString(2, product.getProductName());
            prSt.setString(3, product.getProductCategory());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void applyDiscount(Product product, double discount) {
        String updatePrice = "UPDATE " + Const.PRODUCT_TABLE + " SET " + Const.PRODUCT_PRICE + " = " +
                Const.PRODUCT_PRICE + " * ? WHERE " + Const.PRODUCT_NAME + " = ? AND " +
                Const.PRODUCT_CATEGORY + " = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(updatePrice);
            prSt.setDouble(1, (1 - discount / 100));
            prSt.setString(2, product.getProductName());
            prSt.setString(3, product.getProductCategory());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void setSale(Date saleDate) {
        Date expireDateThreshold = Date.valueOf(saleDate.toLocalDate().plusDays(5));

        try {
            String selectExpiringProducts = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.EXPIRY_DATE + " = ?";
            PreparedStatement prSt = getDbConnection().prepareStatement(selectExpiringProducts);
            prSt.setDate(1, expireDateThreshold);

            ResultSet expiringProductsResultSet = prSt.executeQuery();

            while (expiringProductsResultSet.next()) {
                double currentPrice = expiringProductsResultSet.getDouble(Const.PRODUCT_PRICE);
                double discountedPrice = currentPrice * 0.8;

                String productName = expiringProductsResultSet.getString(Const.PRODUCT_NAME);
                String updatePriceQuery = "UPDATE " + Const.PRODUCT_TABLE + " SET " + Const.PRODUCT_PRICE + "=? WHERE " + Const.PRODUCT_NAME + "=?";
                PreparedStatement updatePriceStatement = getDbConnection().prepareStatement(updatePriceQuery);
                updatePriceStatement.setDouble(1, discountedPrice);
                updatePriceStatement.setString(2, productName);

                updatePriceStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteExpiredProductInfo(Date date) {
        String selectExpired = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.EXPIRY_DATE + " < ?";

        try {
            Connection connection = getDbConnection();
            PreparedStatement selectSt = connection.prepareStatement(selectExpired);
            selectSt.setDate(1, new Date(date.getTime())); // Set the date as a parameter
            ResultSet resultSet = selectSt.executeQuery();

            while(resultSet.next()) {
                Date expireDate = resultSet.getDate(Const.EXPIRY_DATE);
                if (expireDate.before(date) || expireDate.equals(date)) {
                    Product expiredProduct = new Product(null, null, 0, null, null, 0, null);
                    expiredProduct.setProductName(resultSet.getString(Const.PRODUCT_NAME));
                    expiredProduct.setProductCategory(resultSet.getString(Const.PRODUCT_CATEGORY));
                    deleteAllProduct(expiredProduct);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
