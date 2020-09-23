package view.tm;

import javafx.scene.control.Button;

public class ClientTM {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String type;
    private Button btnDelete;
    private Button btnUpdate;

    public ClientTM(String id, String name, String address, String contact, String type, Button btnDelete, Button btnUpdate) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContact(contact);
        this.setType(type);
        this.setBtnDelete(btnDelete);
        this.setBtnUpdate(btnUpdate);
    }

    public ClientTM() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
