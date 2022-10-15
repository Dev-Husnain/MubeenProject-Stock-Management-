package hmtechagency.mubeenproject.Models;

public class BillingModel {
    String ProductName,ProductTotal_Price,ProductQuantity,Name,Email,
            Date,Comment,Type,TimeOfIssuesReceived,ProductPrice,Status,Remaining,Month,Year,WeekOfYear,DayOfYear;

    public BillingModel() {
    }

    public BillingModel(String productName, String productTotal_Price, String productQuantity, String name, String email, String date, String comment, String type, String timeOfIssuesReceived, String productPrice, String status, String remaining, String month, String year, String weekOfYear, String dayOfYear) {
        ProductName = productName;
        ProductTotal_Price = productTotal_Price;
        ProductQuantity = productQuantity;
        Name = name;
        Email = email;
        Date = date;
        Comment = comment;
        Type = type;
        TimeOfIssuesReceived = timeOfIssuesReceived;
        ProductPrice = productPrice;
        Status = status;
        Remaining = remaining;
        Month = month;
        Year = year;
        WeekOfYear = weekOfYear;
        DayOfYear = dayOfYear;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductTotal_Price() {
        return ProductTotal_Price;
    }

    public void setProductTotal_Price(String productTotal_Price) {
        ProductTotal_Price = productTotal_Price;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTimeOfIssuesReceived() {
        return TimeOfIssuesReceived;
    }

    public void setTimeOfIssuesReceived(String timeOfIssuesReceived) {
        TimeOfIssuesReceived = timeOfIssuesReceived;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRemaining() {
        return Remaining;
    }

    public void setRemaining(String remaining) {
        Remaining = remaining;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getWeekOfYear() {
        return WeekOfYear;
    }

    public void setWeekOfYear(String weekOfYear) {
        WeekOfYear = weekOfYear;
    }

    public String getDayOfYear() {
        return DayOfYear;
    }

    public void setDayOfYear(String dayOfYear) {
        DayOfYear = dayOfYear;
    }
}
