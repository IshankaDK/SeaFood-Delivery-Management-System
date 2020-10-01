package entity;

public class Order implements SuperEntity{
    private String id;
    private String date;
    private String clientId;
    private String status;

    public Order(String id, String date, String clientId, String status) {
        this.setId(id);
        this.setDate(date);
        this.setClientId(clientId);
        this.setStatus(status);
    }

    public Order() {

    }

    public Order(String id, String status) {
        this.setId(id);
        this.setStatus(status);
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
