package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.DeliveryOrderDAO;
import entity.DeliveryOrder;
import entity.Driver;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DeliveryOrderDaoImpl implements DeliveryOrderDAO {
    @Override
    public boolean save(DeliveryOrder deliveryOrder) throws Exception {
        return CrudUtil.execute("INSERT INTO DeliveryOrder VALUES (?,?,?,?,?,?,?)",
                deliveryOrder.getDoId(),deliveryOrder.getOrderID(),deliveryOrder.getDriverID(),deliveryOrder.getDeparture(),
                deliveryOrder.getArrival(),deliveryOrder.getFee(),deliveryOrder.getStatus());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(DeliveryOrder deliveryOrder) throws Exception {
        return CrudUtil.execute("UPDATE DeliveryOrder SET arrivalTime = ?, status = ? WHERE doId =? && Status = 'On the Way'",
                deliveryOrder.getArrival(),deliveryOrder.getStatus(),deliveryOrder.getDoId());
    }

    @Override
    public DeliveryOrder get(String s) throws Exception {
        ResultSet set = CrudUtil.execute("SELECT * FROM DeliveryOrder WHERE doId =?",s);
        if(set.next()) {
            return new DeliveryOrder(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    set.getString(6),
                    set.getString(7)
            );
        }
        return null;
    }

    @Override
    public ArrayList<DeliveryOrder> getAll() throws Exception {
        return null;
    }
}
