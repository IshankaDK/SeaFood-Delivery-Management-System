package entity;

public class Purchased implements SuperEntity {
    private String id;
    private String date;
    private String boatId;

    public Purchased(String id, String date, String boatId) {
        this.setId(id);
        this.setDate(date);
        this.setBoatId(boatId);
    }

    public Purchased() {
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
