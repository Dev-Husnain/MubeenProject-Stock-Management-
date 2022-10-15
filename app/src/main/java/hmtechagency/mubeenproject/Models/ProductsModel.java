package hmtechagency.mubeenproject.Models;

public class ProductsModel {
    String ProductName,ProductPrice,currentDate,currentTime,ProductQuantity,ProductTotalPrice,ProductImage;
    String TimeStamps;

    public ProductsModel() {
    }


    public ProductsModel(String productName, String productPrice, String currentDate, String currentTime, String productQuantity, String productTotalPrice, String productImage, String timeStamps) {
        ProductName = productName;
        ProductPrice = productPrice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        ProductQuantity = productQuantity;
        ProductTotalPrice = productTotalPrice;
        ProductImage = productImage;
        TimeStamps = timeStamps;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductTotalPrice() {
        return ProductTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        ProductTotalPrice = productTotalPrice;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getTimeStamps() {
        return TimeStamps;
    }

    public void setTimeStamps(String timeStamps) {
        TimeStamps = timeStamps;
    }
}
