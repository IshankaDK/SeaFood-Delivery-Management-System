package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.SeaFoodDAO;
import entity.Client;
import entity.SeaFood;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SeaFoodDaoImpl implements SeaFoodDAO {
    @Override
    public boolean save(SeaFood seaFood) throws Exception {
        return CrudUtil.execute("INSERT INTO Seafood VALUES (?,?,?,?,?)",
                seaFood.getCode(),seaFood.getDescription(),seaFood.getQtyOnHand(),seaFood.getPurchasePrice(),seaFood.getSellingPrice());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Seafood WHERE itemCode = ?",s);
    }

    @Override
    public boolean update(SeaFood seaFood) throws Exception {
        return CrudUtil.execute("UPDATE Seafood SET description = ? , qtyOnHand = ? , purchasedPrice = ? , sellingPrice = ? WHERE itemCode = ? ",
                seaFood.getDescription(),seaFood.getQtyOnHand(),seaFood.getPurchasePrice(),seaFood.getSellingPrice(),seaFood.getCode());
    }

    @Override
    public SeaFood get(String s) throws Exception {
        ResultSet set = CrudUtil.execute("SELECT * FROM Seafood WHERE itemCode =? ",s);
        if(set.next()){
            return new SeaFood(
                    set.getString(1),
                    set.getString(2),
                    set.getDouble(3),
                    set.getDouble(4),
                    set.getDouble(5)
            );
        }
        return null;
    }

    @Override
    public ArrayList<SeaFood> getAll() throws Exception {
        ResultSet set =  CrudUtil.execute("SELECT * FROM Seafood");
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
