package hmtechagency.mubeenproject.Models;

public class CostModel {

    String PaperWidth,paperLength,gram,Rate,priceOfPaper,onePiece,RatePricePerPiece,pricePerPiece,silicateValue,
            silicate,LaminationWidth,LaminationLength,LaminationRate,PinValue,LabourValueTotal,NetValue,CostTimeStamps,Date,Printing,LaminationTotal;

    public CostModel() {
    }

    public CostModel(String paperWidth, String paperLength, String gram, String rate, String priceOfPaper, String onePiece, String ratePricePerPiece, String pricePerPiece, String silicateValue, String silicate, String laminationWidth, String laminationLength, String laminationRate, String pinValue, String labourValueTotal, String netValue, String costTimeStamps, String date, String printing, String laminationTotal) {
        PaperWidth = paperWidth;
        this.paperLength = paperLength;
        this.gram = gram;
        Rate = rate;
        this.priceOfPaper = priceOfPaper;
        this.onePiece = onePiece;
        RatePricePerPiece = ratePricePerPiece;
        this.pricePerPiece = pricePerPiece;
        this.silicateValue = silicateValue;
        this.silicate = silicate;
        LaminationWidth = laminationWidth;
        LaminationLength = laminationLength;
        LaminationRate = laminationRate;
        PinValue = pinValue;
        LabourValueTotal = labourValueTotal;
        NetValue = netValue;
        CostTimeStamps = costTimeStamps;
        Date = date;
        Printing = printing;
        LaminationTotal = laminationTotal;
    }

    public String getPaperWidth() {
        return PaperWidth;
    }

    public void setPaperWidth(String paperWidth) {
        PaperWidth = paperWidth;
    }

    public String getPaperLength() {
        return paperLength;
    }

    public void setPaperLength(String paperLength) {
        this.paperLength = paperLength;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getPriceOfPaper() {
        return priceOfPaper;
    }

    public void setPriceOfPaper(String priceOfPaper) {
        this.priceOfPaper = priceOfPaper;
    }

    public String getOnePiece() {
        return onePiece;
    }

    public void setOnePiece(String onePiece) {
        this.onePiece = onePiece;
    }

    public String getRatePricePerPiece() {
        return RatePricePerPiece;
    }

    public void setRatePricePerPiece(String ratePricePerPiece) {
        RatePricePerPiece = ratePricePerPiece;
    }

    public String getPricePerPiece() {
        return pricePerPiece;
    }

    public void setPricePerPiece(String pricePerPiece) {
        this.pricePerPiece = pricePerPiece;
    }

    public String getSilicateValue() {
        return silicateValue;
    }

    public void setSilicateValue(String silicateValue) {
        this.silicateValue = silicateValue;
    }

    public String getSilicate() {
        return silicate;
    }

    public void setSilicate(String silicate) {
        this.silicate = silicate;
    }

    public String getLaminationWidth() {
        return LaminationWidth;
    }

    public void setLaminationWidth(String laminationWidth) {
        LaminationWidth = laminationWidth;
    }

    public String getLaminationLength() {
        return LaminationLength;
    }

    public void setLaminationLength(String laminationLength) {
        LaminationLength = laminationLength;
    }

    public String getLaminationRate() {
        return LaminationRate;
    }

    public void setLaminationRate(String laminationRate) {
        LaminationRate = laminationRate;
    }

    public String getPinValue() {
        return PinValue;
    }

    public void setPinValue(String pinValue) {
        PinValue = pinValue;
    }

    public String getLabourValueTotal() {
        return LabourValueTotal;
    }

    public void setLabourValueTotal(String labourValueTotal) {
        LabourValueTotal = labourValueTotal;
    }

    public String getNetValue() {
        return NetValue;
    }

    public void setNetValue(String netValue) {
        NetValue = netValue;
    }

    public String getCostTimeStamps() {
        return CostTimeStamps;
    }

    public void setCostTimeStamps(String costTimeStamps) {
        CostTimeStamps = costTimeStamps;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPrinting() {
        return Printing;
    }

    public void setPrinting(String printing) {
        Printing = printing;
    }

    public String getLaminationTotal() {
        return LaminationTotal;
    }

    public void setLaminationTotal(String laminationTotal) {
        LaminationTotal = laminationTotal;
    }
}
