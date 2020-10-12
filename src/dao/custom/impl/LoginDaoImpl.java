package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.LoginDAO;
import entity.Boat;
import entity.Login;

import java.sql.ResultSet;
import java.util.ArrayList;

public class LoginDaoImpl implements LoginDAO {
    @Override
    public boolean save(Login login) throws Exception {
        return CrudUtil.execute("INSERT INTO Login VALUES (?,?,md5(?))",
                login.getName(),login.getUserName(),login.getPassword());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public boolean update(Login login) throws Exception {
        return false;
    }

    @Override
    public Login get(String s) throws Exception {
       return null;
    }

    @Override
    public ArrayList<Login> getAll() throws Exception {
        return null;
    }

    @Override
    public Login getLogin(Login login) throws Exception {
        ResultSet set =  CrudUtil.execute("SELECT * FROM Login WHERE userName = ? && password = md5(?)",login.getUserName(),
                login.getPassword());
        if(set.next()){
            return new Login(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3)
            );
        }
        return null;
    }
}
