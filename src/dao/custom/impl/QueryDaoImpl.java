package dao.custom.impl;

import dao.CrudUtil;
import dao.QueryDAO;
import entity.Client;
import entity.Driver;
import entity.SeaFood;

import java.sql.ResultSet;
import java.util.ArrayList;

public class QueryDaoImpl implements QueryDAO {

    @Override
    public String getCode() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT itemCode FROM SeaFood ORDER BY itemCode DESC LIMIT 1");
        String code="SF001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("SF");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                code="SF00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                code="SF0"+(selectedId+1);
            }else if(selectedId >= 99){
                code="SF"+(selectedId+1);
            }
        }
        return code;
    }

    @Override
    public String getPurchaseId() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT purchasedId FROM Purchased ORDER BY purchasedId DESC LIMIT 1");
        String id="PU001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("PU");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                id="PU00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                id="PU0"+(selectedId+1);
            }else if(selectedId >= 99){
                id="PU"+(selectedId+1);
            }
        }
        return id;
    }

    @Override
    public String getBoatId() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT boatId FROM Boat ORDER BY boatId DESC LIMIT 1");
        String id="B001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("B");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                id="B00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                id="B0"+(selectedId+1);
            }else if(selectedId >= 99){
                id="B"+(selectedId+1);
            }
        }
        return id;
    }

    @Override
    public String getClientId() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT clientId FROM Client ORDER BY clientId DESC LIMIT 1");
        String id="C001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("C");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                id="C00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                id="C0"+(selectedId+1);
            }else if(selectedId >= 99){
                id="C"+(selectedId+1);
            }
        }
        return id;
    }

    @Override
    public String getDriverId() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT driverId FROM Driver ORDER BY driverId DESC LIMIT 1");
        String id="DR001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("DR");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                id="DR00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                id="DR0"+(selectedId+1);
            }else if(selectedId >= 99){
                id="DR"+(selectedId+1);
            }
        }
        return id;
    }

    @Override
    public String getOrderId() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT orderId FROM _Order ORDER BY orderId DESC LIMIT 1");
        String id="OR001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("OR");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                id="OR00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                id="OR0"+(selectedId+1);
            }else if(selectedId >= 99){
                id="OR"+(selectedId+1);
            }
        }
        return id;
    }

    @Override
    public String getQuickOrderId() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT qOrderId FROM QuickOrder ORDER BY qOrderId DESC LIMIT 1");
        String id="QOR001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("QOR");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                id="QOR00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                id="QOR0"+(selectedId+1);
            }else if(selectedId >= 99){
                id="QOR"+(selectedId+1);
            }
        }
        return id;
    }

    @Override
    public String getDoId() throws Exception {
        ResultSet set = CrudUtil.
                execute("SELECT doId FROM DeliveryOrder ORDER BY doId DESC LIMIT 1");
        String id="DOR001";
        if (set.next()){
            String temp=set.getString(1);
            String[] cs = temp.split("DOR");
            int selectedId=Integer.parseInt(cs[1]);
            if (selectedId<9){
                id="DOR00"+(selectedId+1);
            }else if(selectedId >= 9 && selectedId <99){
                id="DOR0"+(selectedId+1);
            }else if(selectedId >= 99){
                id="DOR"+(selectedId+1);
            }
        }
        return id;
    }

    @Override
    public Client getMatchedClient(String id) throws Exception {
        ResultSet set = CrudUtil.execute("SELECT * FROM Client c , _Order o WHERE (c.clientId = o.clientId) && orderId = ?",
                id);
        if(set.next()){
            return new Client(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5)
            );
        }
        return null;
    }

    @Override
    public int getTotalOrders() throws Exception {
        ResultSet set = CrudUtil.execute("SELECT COUNT(orderId) from _Order;");
        if(set.next()){
            return set.getInt(1);
        }
        return 0;
    }

    @Override
    public int getFinishedDO() throws Exception {
        ResultSet set = CrudUtil.execute("SELECT COUNT(doId) from DeliveryOrder WHERE status = 'Done';");
        if(set.next()){
            return set.getInt(1);
        }
        return 0;
    }

    @Override
    public int getPendingDO() throws Exception {
        ResultSet set = CrudUtil.execute("SELECT COUNT(doId) from DeliveryOrder WHERE status = 'On the Way';");
        if(set.next()){
            return set.getInt(1);
        }
        return 0;
    }

    @Override
    public int getTotalSeafood() throws Exception {
        ResultSet set = CrudUtil.execute("SELECT COUNT(itemCode) from Seafood;");
        if(set.next()){
            return set.getInt(1);
        }
        return 0;
    }

    @Override
    public ArrayList<SeaFood> getMostMovable() throws Exception {
        ResultSet set = CrudUtil.execute("SELECT *,COUNT(od.orderId)  FROM Seafood s,OrderDetail od WHERE od.itemCode=s.itemCode GROUP BY od.itemCode ORDER BY COUNT(od.orderId) DESC LIMIT 7");
        ArrayList<SeaFood> seaFoods = new ArrayList<>();
        while (set.next()){
            seaFoods.add(new SeaFood(
                    set.getString(1),set.getString(2),set.getDouble(3),
                    set.getDouble(4),set.getDouble(5)
            ));
        }
        return seaFoods;
    }

    @Override
    public ArrayList<SeaFood> getLeastMovable() throws Exception {
        ResultSet set = CrudUtil.execute("SELECT *,COUNT(od.orderId) FROM Seafood s, OrderDetail od WHERE od.itemCode=s.itemCode GROUP BY od.itemCode ORDER BY COUNT(od.orderId) LIMIT 7");
        ArrayList<SeaFood> seaFoods = new ArrayList<>();
        while (set.next()){
            seaFoods.add(new SeaFood(
                    set.getString(1),set.getString(2),set.getDouble(3),
                    set.getDouble(4),set.getDouble(5)
            ));
        }
        return seaFoods;
    }

}
