package lesnykh;

import java.sql.Date;

public class Product {
    private String productName;
    private String productCategory;
    private double productPrice;
    private Date productionDate;
    private Date expireDate;
    private double productQuantity;
    private String productType;

    public Product(String productName, String productCategory, double productPrice, Date productionDate,
                   Date expireDate, double productQuantity, String productType) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productionDate = productionDate;
        this.expireDate = expireDate;
        this.productQuantity = productQuantity;
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductType() { return productType; }

    public void setProductType() { this.productType = productType; }
}
