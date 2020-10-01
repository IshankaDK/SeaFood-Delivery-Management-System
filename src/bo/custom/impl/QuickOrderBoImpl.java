package bo.custom.impl;

import bo.BoFactory;
import bo.custom.QuickOrderBo;
import bo.custom.QuickOrderDetailBo;
import bo.custom.SeaFoodBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.QuickOrderDAO;
import db.DBConnection;
import dto.OrderDTO;
import dto.QuickOrderDTO;
import dto.QuickOrderDetailDTO;
import entity.Order;
import entity.QuickOrder;

import java.util.ArrayList;

public class QuickOrderBoImpl implements QuickOrderBo {
    QueryDAO queryDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);
    QuickOrderDAO quickOrderDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUICK);
    QuickOrderDetailBo quickOrderDetailBo = BoFactory.getInstance().getBo(BoFactory.BOType.QUICKDETAIL);
    SeaFoodBo seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);

    @Override
    public String getQuickOrderId() throws Exception {
        return queryDAO.getQuickOrderId();
    }

    @Override
    public boolean saveOrder(QuickOrderDTO dto, ArrayList<QuickOrderDetailDTO> orderDetailDTOS) throws Exception {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isSaved = quickOrderDAO.save(new QuickOrder(dto.getId(), dto.getDate()));
            boolean isDetailSaved = quickOrderDetailBo.addOrderDetail(orderDetailDTOS);
            boolean isUpdated = seaFoodBo.UpdateStockWhenQuickOrder(orderDetailDTOS);

            if (isSaved && isDetailSaved && isUpdated) {
                DBConnection.getInstance().getConnection().commit();
                return true;
            } else {
                DBConnection.getInstance().getConnection().rollback();
                return false;
            }

        }finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
