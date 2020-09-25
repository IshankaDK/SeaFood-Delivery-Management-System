package dao;

import entity.Driver;

import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public String getCode() throws Exception;
    public String getPurchaseId() throws Exception;
    public String getBoatId() throws Exception;
    public String getClientId() throws Exception;
    public String getDriverId() throws Exception;
    public String getOrderId() throws Exception;
    public String getQuickOrderId() throws Exception;
    public String getDoId() throws Exception;
}
