package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.PurchaseDetailDAO;
import entity.PurchaseDetail;

import java.util.ArrayList;

public class PurchaseDetailDaoImpl implements PurchaseDetailDAO {
    @Override
    public boolean save(PurchaseDetail purchaseDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO PurchaseDetail VALUES (?,?,?,?)",purchaseDetail.getId(),
                purchaseDetail.getCode(),purchaseDetail.getQty(),purchaseDetail.getPurchasedPrice());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(PurchaseDetail purchaseDetail) throws Exception {
        return false;
    }

    @Override
    public PurchaseDetail get(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<PurchaseDetail> getAll() throws Exception {
        return null;
    }
}
