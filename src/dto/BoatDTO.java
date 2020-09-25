package dto;

public class BoatDTO {
    private String boatId;
    private String name;
    private String ownerName;
    private String ownerContact;

    public BoatDTO(String boatId,String name, String ownerName, String ownerContact) {
        this.setBoatId(boatId);
        this.setName(name);
        this.setOwnerName(ownerName);
        this.setOwnerContact(ownerContact);
    }

    public BoatDTO() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
