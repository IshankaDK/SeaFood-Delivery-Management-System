package bo;

import bo.custom.impl.*;

public class BoFactory {

    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return (boFactory==null) ? (boFactory = new BoFactory()) : boFactory;
    }

    public enum BOType {
      CLIENT,BOAT,DRIVER,SEAFOOD,ORDER,DELIVERY,PURCHASE,QUICKORDER
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
            case QUICKORDER:
                return (T) new QuickOrderBoImpl();
            case DELIVERY:
                return (T) new DeliveryOrderBoImpl();
            default:
                return null;
        }
    }
}
