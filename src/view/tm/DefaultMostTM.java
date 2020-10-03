package view.tm;

public class DefaultMostTM {
    private String code;
    private String description;
    private double qtyOnHand;
    private double purchasePrice;
    private double sellingPrice;

    public DefaultMostTM(String code, String description, double qtyOnHand, double purchasePrice, double sellingPrice) {
        this.setCode(code);
        this.setDescription(description);
        this.setQtyOnHand(qtyOnHand);
        this.setPurchasePrice(purchasePrice);
        this.setSellingPrice(sellingPrice);
    }

    public DefaultMostTM() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(double qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
