package bo.custom.impl;

import bo.custom.OrderBo;
import dao.DaoFactory;
import dao.QueryDAO;

public class OrderBoImpl implements OrderBo {
    QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);
    @Override
    public String getOrderId() throws Exception {
        return qDao.getOrderId();
    }
}
