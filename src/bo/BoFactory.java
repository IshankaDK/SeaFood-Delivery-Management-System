package bo;

import bo.custom.impl.*;
import dao.custom.impl.OrderDetailDaoImpl;

public class BoFactory {

    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return (boFactory==null) ? (boFactory = new BoFactory()) : boFactory;
    }

    public enum BOType {
      CLIENT,BOAT,DRIVER,SEAFOOD,ORDER,DELIVERY,PURCHASE,QUICKORDER,PURCHASEDETAIL,ORDERDETAIL,QUICKDETAIL,LOGIN
    }

    public <T> T getBo(BOType type) {
        switch (type) {
            case CLIENT:
                return (T) new ClientBoImpl();
            case BOAT:
                return (T) new BoatBoImpl();
            case DRIVER:
                return (T) new DriverBoImpl();
            case SEAFOOD:
                return (T) new SeaFoodBoImpl();
            case PURCHASE:
                return (T) new PurchaseBoImpl();
            case ORDER:
                return (T) new OrderBoImpl();
            case ORDERDETAIL:
                return (T) new OrderDetailBoImpl();
            case QUICKORDER:
                return (T) new QuickOrderBoImpl();
            case QUICKDETAIL:
                return (T) new QuickOrderDetailBoImpl();
            case DELIVERY:
                return (T) new DeliveryOrderBoImpl();
            case PURCHASEDETAIL:
                 return (T) new PurchaseDetailBoImpl();
            case LOGIN:
                return (T) new LoginBoImpl();
            default:
                return null;
        }
    }
}
