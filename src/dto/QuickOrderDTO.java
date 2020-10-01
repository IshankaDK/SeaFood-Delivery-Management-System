package dto;

public class QuickOrderDTO {
    private String id;
    private String date;

    public QuickOrderDTO(String id, String date) {
        this.setId(id);
        this.setDate(date);
    }

    public QuickOrderDTO() {
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
