package entity;

public class DeliveryOrder implements SuperEntity {
    private String doId;
    private String orderID;
    private String driverID;
    private String departure;
    private String arrival;
    private String fee;
    private String status;

    public DeliveryOrder() {
    }

    public DeliveryOrder(String doId, String orderID, String driverID, String departure, String arrival, String fee, String status) {
        this.setDoId(doId);
        this.setOrderID(orderID);
        this.setDriverID(driverID);
        this.setDeparture(departure);
        this.setArrival(arrival);
        this.setFee(fee);
        this.setStatus(status);
    }


    public DeliveryOrder(String doId, String arrival, String status) {
        this.setDoId(doId);
        this.setArrival(arrival);
        this.setStatus(status);
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
