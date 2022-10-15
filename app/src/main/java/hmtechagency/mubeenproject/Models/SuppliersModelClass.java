package hmtechagency.mubeenproject.Models;

public class SuppliersModelClass {

    String SupplierName,SupplierEmail,SupplierPhone,SupplierAddress,SupplierBankDetail,SupplierDiscount,SupplierNotes,SupplierTimeStamps;

    public SuppliersModelClass() {
    }

    public SuppliersModelClass(String supplierName, String supplierEmail, String supplierPhone, String supplierAddress, String supplierBankDetail, String supplierDiscount, String supplierNotes, String supplierTimeStamps) {
        SupplierName = supplierName;
        SupplierEmail = supplierEmail;
        SupplierPhone = supplierPhone;
        SupplierAddress = supplierAddress;
        SupplierBankDetail = supplierBankDetail;
        SupplierDiscount = supplierDiscount;
        SupplierNotes = supplierNotes;
        SupplierTimeStamps = supplierTimeStamps;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getSupplierEmail() {
        return SupplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        SupplierEmail = supplierEmail;
    }

    public String getSupplierPhone() {
        return SupplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        SupplierPhone = supplierPhone;
    }

    public String getSupplierAddress() {
        return SupplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        SupplierAddress = supplierAddress;
    }

    public String getSupplierBankDetail() {
        return SupplierBankDetail;
    }

    public void setSupplierBankDetail(String supplierBankDetail) {
        SupplierBankDetail = supplierBankDetail;
    }

    public String getSupplierDiscount() {
        return SupplierDiscount;
    }

    public void setSupplierDiscount(String supplierDiscount) {
        SupplierDiscount = supplierDiscount;
    }

    public String getSupplierNotes() {
        return SupplierNotes;
    }

    public void setSupplierNotes(String supplierNotes) {
        SupplierNotes = supplierNotes;
    }

    public String getSupplierTimeStamps() {
        return SupplierTimeStamps;
    }

    public void setSupplierTimeStamps(String supplierTimeStamps) {
        SupplierTimeStamps = supplierTimeStamps;
    }
}
