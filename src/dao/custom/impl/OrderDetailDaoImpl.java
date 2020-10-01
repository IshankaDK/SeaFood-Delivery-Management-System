package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.util.ArrayList;

public class OrderDetailDaoImpl implements OrderDetailDAO {
    @Override
    public boolean save(OrderDetail orderDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO OrderDetail VALUES (?,?,?,?,?)",
               orderDetail.getId(),orderDetail.getCode(),orderDetail.getQty(),orderDetail.getPrice(),
                orderDetail.getDiscount());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws Exception {
        return false;
    }

    @Override
    public OrderDetail get(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws Exception {
        return null;
    }
}
