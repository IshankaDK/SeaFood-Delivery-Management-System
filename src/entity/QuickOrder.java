package entity;

public class QuickOrder implements SuperEntity {
    private String id;
    private String date;

    public QuickOrder(String id, String date) {
        this.setId(id);
        this.setDate(date);
    }

    public QuickOrder() {
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
}
