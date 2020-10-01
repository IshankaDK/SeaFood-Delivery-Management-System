package dao;

import dao.custom.OrderDAO;
import dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory==null) ? (daoFactory = new DaoFactory()) : daoFactory;
    }

    public enum DAOType{
        CLIENT,BOAT,DRIVER,SEAFOOD,QUERY,PURCHASE,PURCHASEDETAIL,ORDER,ORDERDETAIL,QUICK,QUICKDETAIL,DELIVERY,LOGIN
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
            case ORDER:
                return (T) new OrderDaoImpl();
            case ORDERDETAIL:
                return (T) new OrderDetailDaoImpl();
            case QUICK:
                return (T) new QuickOrderDaoImpl();
            case QUICKDETAIL:
                return (T) new QuickOrderDetailDaoImpl();
            case DELIVERY:
                return (T) new DeliveryOrderDaoImpl();
            case LOGIN:
                return (T) new LoginDaoImpl();
            case QUERY:
                return (T) new QueryDaoImpl();
            default:
                return null;
        }
    }
}
