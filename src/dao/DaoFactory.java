package dao;

import dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory==null) ? (daoFactory = new DaoFactory()) : daoFactory;
    }

    public enum DAOType{
        CLIENT,BOAT,DRIVER,SEAFOOD,QUERY,PURCHASE,PURCHASEDETAIL
    }

    public <T> T getDao(DAOType type){
        switch (type){
            case CLIENT:
                return (T) new ClientDaoImpl();
            case BOAT:
                return (T) new BoatDaoImpl();
            case DRIVER:
                return (T) new DriverDoaImpl();
            case SEAFOOD:
                return (T) new SeaFoodDaoImpl();
            case PURCHASE:
                return (T) new PurchaseDAOImpl();
            case PURCHASEDETAIL:
                return (T) new PurchaseDetailDaoImpl();
            case QUERY:
                return (T) new QueryDaoImpl();
            default:
                return null;
        }
    }
}
