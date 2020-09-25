package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.BoatDAO;
import entity.Boat;

import java.sql.ResultSet;
import java.util.ArrayList;

public class BoatDaoImpl implements BoatDAO {
    @Override
    public boolean save(Boat boat) throws Exception {
        return CrudUtil.execute("INSERT INTO Boat VALUES (?,?,?,?)",
                boat.getBoatId(),boat.getName(),boat.getOwnerName(),boat.getOwnerContact());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Boat WHERE boatId =?",s);
    }

    @Override
    public boolean update(Boat boat) throws Exception {
        return CrudUtil.execute("UPDATE Boat SET boatName = ? , ownerName = ? , ownerContact = ? WHERE boatId = ? ",
                boat.getName(),boat.getOwnerName(),boat.getOwnerContact(),boat.getBoatId());
    }

    @Override
    public Boat get(String s) throws Exception {
        ResultSet set = CrudUtil.execute("SELECT * FROM Boat WHERE boatId =? ",s);
        if(set.next()){
            return new Boat(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4)
            );
        }
        return null;
    }

    @Override
    public ArrayList<Boat> getAll() throws Exception {
        ResultSet set =  CrudUtil.execute("SELECT * FROM Boat");
        ArrayList<Boat> boatList = new ArrayList<>();
        while (set.next()){
            boatList.add(new Boat(
                    set.getString(1),set.getString(2),set.getString(3),set.getString(4)
            ));
        }
        return boatList;
    }
}
