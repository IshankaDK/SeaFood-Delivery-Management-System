package entity;

public class OrderDetail implements SuperEntity{
    private String id;
    private String code;
    private double qty;
    private double price;
    private double discount;

    public OrderDetail(String id, String code, double qty, double price, double discount) {
        this.setId(id);
        this.setCode(code);
        this.setQty(qty);
        this.setPrice(price);
        this.setDiscount(discount);
    }

    public OrderDetail() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
