package dto;

import entity.SuperEntity;

import java.util.ArrayList;

public class PurchaseDTO implements SuperEntity {
    private String id;
    private String date;
    private String boatId;

    public PurchaseDTO(String id, String date, String boatId) {
        this.setId(id);
        this.setDate(date);
        this.setBoatId(boatId);
    }

    public PurchaseDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBoatId() {
        return boatId;
    }

    public void setBoatId(String boatId) {
        this.boatId = boatId;
    }

}
