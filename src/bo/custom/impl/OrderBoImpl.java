package bo.custom.impl;

import bo.BoFactory;
import bo.custom.OrderBo;
import bo.custom.OrderDetailBo;
import bo.custom.SeaFoodBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DBConnection;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Order;
import entity.Purchased;

import java.sql.Connection;
import java.util.ArrayList;

public class OrderBoImpl implements OrderBo {
    QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);
    OrderDAO orderDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.ORDER);
    OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoFactory.BOType.ORDERDETAIL);
    SeaFoodBo seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);

    @Override
    public String getOrderId() throws Exception {
        return qDao.getOrderId();
    }

    @Override
    public boolean saveOrder(OrderDTO dto, ArrayList<OrderDetailDTO> orderDetailDTOS) throws Exception {

        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isSaved = orderDAO.save(new Order(dto.getId(), dto.getDate(), dto.getClientId(), dto.getStatus()));
            boolean isDetailSaved = orderDetailBo.addOrderDetail(orderDetailDTOS);
            boolean isUpdated = seaFoodBo.UpdateStockWhenOrder(orderDetailDTOS);

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

    @Override
    public boolean updateOrder(OrderDTO dto) throws Exception {
        return orderDAO.update(new Order(dto.getId(),dto.getStatus()));
    }
}
