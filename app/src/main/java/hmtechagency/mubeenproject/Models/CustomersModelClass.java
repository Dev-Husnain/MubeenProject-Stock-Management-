package hmtechagency.mubeenproject.Models;

public class CustomersModelClass {
    String CustomerName,CustomerEmail,CustomerPhone,CustomerAddress,CustomerBankDetail,CustomerDiscount,CustomerNotes,CustomerTimeStamps;

    public CustomersModelClass() {
    }

    public CustomersModelClass(String customerName, String customerEmail, String customerPhone, String customerAddress, String customerBankDetail, String customerDiscount, String customerNotes, String customerTimeStamps) {
        CustomerName = customerName;
        CustomerEmail = customerEmail;
        CustomerPhone = customerPhone;
        CustomerAddress = customerAddress;
        CustomerBankDetail = customerBankDetail;
        CustomerDiscount = customerDiscount;
        CustomerNotes = customerNotes;
        CustomerTimeStamps = customerTimeStamps;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerBankDetail() {
        return CustomerBankDetail;
    }

    public void setCustomerBankDetail(String customerBankDetail) {
        CustomerBankDetail = customerBankDetail;
    }

    public String getCustomerDiscount() {
        return CustomerDiscount;
    }

    public void setCustomerDiscount(String customerDiscount) {
        CustomerDiscount = customerDiscount;
    }

    public String getCustomerNotes() {
        return CustomerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        CustomerNotes = customerNotes;
    }

    public String getCustomerTimeStamps() {
        return CustomerTimeStamps;
    }

    public void setCustomerTimeStamps(String customerTimeStamps) {
        CustomerTimeStamps = customerTimeStamps;
    }
}
