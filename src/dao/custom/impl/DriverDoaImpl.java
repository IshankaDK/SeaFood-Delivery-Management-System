package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.DriverDao;
import entity.Client;
import entity.Driver;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DriverDoaImpl implements DriverDao {
    @Override
    public boolean save(Driver driver) throws Exception {
        return CrudUtil.execute("INSERT INTO Driver VALUES (?,?,?,?)",
                driver.getId(),driver.getName(),driver.getAddress(),driver.getContact());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute(" DELETE FROM Driver WHERE driverId =?",s);
    }

    @Override
    public boolean update(Driver driver) throws Exception {
        return CrudUtil.execute("UPDATE Driver SET driverName = ? , driverAddress = ? , driverContact = ?  WHERE driverId = ? ",
                driver.getName(),driver.getAddress(),driver.getContact(),driver.getId());
    }

    @Override
    public Driver get(String s) throws Exception {
        ResultSet set = CrudUtil.execute("SELECT * FROM Driver WHERE driverId =?  ",s);
        if(set.next()){
            return new Driver(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4)
            );
        }
        return null;
    }

    @Override
    public ArrayList<Driver> getAll() throws Exception {
        ResultSet set =  CrudUtil.execute("SELECT * FROM Driver");
        ArrayList<Driver> driverArrayList = new ArrayList<>();
        while (set.next()){
            driverArrayList.add(new Driver(
                    set.getString(1),set.getString(2),set.getString(3),
                    set.getString(4)
            ));
        }
        return driverArrayList;
    }
}
