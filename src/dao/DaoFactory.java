package dao;

import dao.custom.impl.ClientDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory==null) ? (daoFactory = new DaoFactory()) : daoFactory;
    }

    public enum DAOType{
        CLIENT,
    }

    public <T> T getDao(DAOType type){
        switch (type){
            case CLIENT:
                return (T) new ClientDaoImpl();
            default:
                return null;
        }
    }
}
