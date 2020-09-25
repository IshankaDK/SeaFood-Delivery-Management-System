package view.tm;

import javafx.scene.control.Button;

public class BoatTM {
    private String boatId;
    private String name;
    private String ownerName;
    private String ownerContact;
    private Button btnDelete;
    private Button btnUpdate;

    public BoatTM(String boatId,String name, String ownerName, String ownerContact, Button btnDelete, Button btnUpdate) {
        this.setBoatId(boatId);
        this.setName(name);
        this.setOwnerName(ownerName);
        this.setOwnerContact(ownerContact);
        this.setBtnDelete(btnDelete);
        this.setBtnUpdate(btnUpdate);
    }

    public BoatTM() {
    }

    public String getBoatId() {
        return boatId;
    }

    public void setBoatId(String boatId) {
        this.boatId = boatId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
