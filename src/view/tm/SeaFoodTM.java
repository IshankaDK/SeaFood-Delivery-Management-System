package view.tm;

import javafx.scene.control.Button;

public class SeaFoodTM {
    private String code;
    private String description;
    private double qtyOnHand;
    private double purchasePrice;
    private double sellingPrice;
    private Button btnDelete;
    private Button btnUpdate;

    public SeaFoodTM(String code, String description, double qtyOnHand, double purchasePrice, double sellingPrice, Button btnDelete, Button btnUpdate) {
        this.setCode(code);
        this.setDescription(description);
        this.setQtyOnHand(qtyOnHand);
        this.setPurchasePrice(purchasePrice);
        this.setSellingPrice(sellingPrice);
        this.setBtnDelete(btnDelete);
        this.setBtnUpdate(btnUpdate);
    }

    public SeaFoodTM() {
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

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(Button btnUpdate) {
        this.btnUpdate = btnUpdate;
    }
}
