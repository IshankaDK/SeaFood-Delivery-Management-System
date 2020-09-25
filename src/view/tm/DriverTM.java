package view.tm;

import javafx.scene.control.Button;

public class DriverTM {
    private String id;
    private String name;
    private String address;
    private String contact;
    private Button btnDelete;
    private Button btnUpdate;

    public DriverTM(String id, String name, String address, String contact, Button btnDelete, Button btnUpdate) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContact(contact);
        this.setBtnDelete(btnDelete);
        this.setBtnUpdate(btnUpdate);
    }

    public DriverTM() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
