package view.tm;

public class OrderTM {
    private String code;
    private String description;
    private double qty;
    private double price;
    private double discount;
    private double total;

    public OrderTM(String code, String description, double qty, double price, double discount, double total) {
        this.setCode(code);
        this.setDescription(description);
        this.setQty(qty);
        this.setPrice(price);
        this.setDiscount(discount);
        this.setTotal(total);
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
