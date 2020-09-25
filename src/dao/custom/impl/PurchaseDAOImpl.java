package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.PurchaseDAO;
import entity.Purchased;

import java.util.ArrayList;

public class PurchaseDAOImpl implements PurchaseDAO {
    @Override
    public boolean save(Purchased purchased) throws Exception {
        return CrudUtil.execute("INSERT INTO Purchased (purchasedId,boatId) VALUES (?,?)",
                purchased.getId(),purchased.getBoatId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(Purchased purchased) throws Exception {
        return false;
    }

    @Override
    public Purchased get(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Purchased> getAll() throws Exception {
        return null;
    }
}
