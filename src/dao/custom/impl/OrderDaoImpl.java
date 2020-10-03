package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.util.ArrayList;

public class OrderDaoImpl implements OrderDAO {
    @Override
    public boolean save(Order order) throws Exception {
       return CrudUtil.execute("INSERT INTO _Order  VALUES (?,?,?,?)",
                order.getId(),order.getDate(),order.getClientId(),order.getStatus());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(Order order) throws Exception {
        return CrudUtil.execute("UPDATE _Order SET paymentStatus = ? WHERE orderId = ? ",order.getStatus(),order.getId());
    }

    @Override
    public Order get(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Order> getAll() throws Exception {
        return null;
    }
}
