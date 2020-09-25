package bo.custom.impl;

import bo.custom.QuickOrderBo;
import dao.DaoFactory;
import dao.QueryDAO;

public class QuickOrderBoImpl implements QuickOrderBo {
    QueryDAO queryDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);
    @Override
    public String getQuickOrderId() throws Exception {
        return queryDAO.getQuickOrderId();
    }
}
