package entity;

public class PurchaseDetail implements SuperEntity {
    private String id;
    private String code;
    private double qty;
    private double purchasedPrice;

    public PurchaseDetail(String id, String code, double qty, double purchasedPrice) {
        this.setId(id);
        this.setCode(code);
        this.setQty(qty);
        this.setPurchasedPrice(purchasedPrice);
    }

    public PurchaseDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }
}
