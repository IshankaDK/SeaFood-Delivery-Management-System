package dao;

import entity.Client;
import entity.Driver;
import entity.SeaFood;

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
    public Client getMatchedClient(String id) throws Exception;
    public int getTotalOrders() throws Exception;
    public int getTotalClient() throws Exception;
    public int getTotalDelivery() throws Exception;
    public int getTotalSeafood() throws Exception;
    public ArrayList<SeaFood> getMostMovable() throws Exception;
    public ArrayList<SeaFood> getLeastMovable() throws Exception;
}
