package dao.custom;

import dao.CrudDAO;
import entity.Login;

public interface LoginDAO extends CrudDAO<Login,String> {
    public Login getLogin(Login login) throws Exception;
}
