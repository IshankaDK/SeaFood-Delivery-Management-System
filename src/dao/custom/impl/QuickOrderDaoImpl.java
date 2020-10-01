package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QuickOrderDAO;
import entity.QuickOrder;

import java.util.ArrayList;

public class QuickOrderDaoImpl implements QuickOrderDAO {
    @Override
    public boolean save(QuickOrder quickOrder) throws Exception {
        return CrudUtil.execute("INSERT INTO QuickOrder (qOrderId) VALUES (?)",quickOrder.getId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(QuickOrder quickOrder) throws Exception {
        return false;
    }

    @Override
    public QuickOrder get(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<QuickOrder> getAll() throws Exception {
        return null;
    }
}
