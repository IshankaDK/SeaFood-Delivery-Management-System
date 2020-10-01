package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QuickOrderDetailDAO;
import entity.QuickOrderDetail;

import java.util.ArrayList;

public class QuickOrderDetailDaoImpl implements QuickOrderDetailDAO {
    @Override
    public boolean save(QuickOrderDetail quickOrderDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO QuickOrderDetail VALUES (?,?,?,?,?)",
                quickOrderDetail.getId(),quickOrderDetail.getCode(),quickOrderDetail.getQty(),quickOrderDetail.getPrice(),
                quickOrderDetail.getDiscount());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(QuickOrderDetail quickOrderDetail) throws Exception {
        return false;
    }

    @Override
    public QuickOrderDetail get(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<QuickOrderDetail> getAll() throws Exception {
        return null;
    }
}
