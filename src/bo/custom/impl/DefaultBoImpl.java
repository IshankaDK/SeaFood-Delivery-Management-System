package bo.custom.impl;

import bo.custom.DefaultBo;
import dao.DaoFactory;
import dao.QueryDAO;

public class DefaultBoImpl implements DefaultBo {
    QueryDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);

    @Override
    public int getTotalOrders() throws Exception {
        return dao.getTotalOrders();
    }

    @Override
    public int getTotalClient() throws Exception {
        return dao.getTotalClient();
    }

    @Override
    public int getTotalDelivery() throws Exception {
        return dao.getTotalDelivery();
    }

    @Override
    public int getTotalSeafood() throws Exception {
        return dao.getTotalSeafood();
    }

}
