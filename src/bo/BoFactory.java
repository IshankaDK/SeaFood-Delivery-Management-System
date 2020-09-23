package bo;

import bo.custom.impl.ClientBoImpl;

public class BoFactory {

    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return (boFactory==null) ? (boFactory = new BoFactory()) : boFactory;
    }

    public enum BOType {
      CLIENT,
    }

    public <T> T getBo(BOType type) {
        switch (type) {
            case CLIENT:
                return (T) new ClientBoImpl();
            default:
                return null;
        }
    }
}
