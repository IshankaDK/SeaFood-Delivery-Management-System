package bo.custom.impl;

import bo.BoFactory;
import bo.custom.DeliveryOrderBo;
import bo.custom.OrderBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.DeliveryOrderDAO;
import db.DBConnection;
import dto.*;
import entity.Client;
import entity.DeliveryOrder;
import entity.Driver;

import java.util.ArrayList;

public class DeliveryOrderBoImpl implements DeliveryOrderBo {

    QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);
    DeliveryOrderDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.DELIVERY);
    OrderBo orderBo = BoFactory.getInstance().getBo(BoFactory.BOType.ORDER);

    @Override
    public String getDoId() throws Exception {
        return qDao.getDoId();
    }

    @Override
    public ClientDTO getClient(String id) throws Exception {
        Client client = qDao.getMatchedClient(id);
        if(client != null) {
            return new ClientDTO(client.getId(), client.getName(),
                    client.getAddress(), client.getContact(), client.getType());
        }else {
            return null;
        }
    }

    @Override
    public boolean saveDO(DeliveryOrderDTO dto) throws Exception {
        return dao.save(new DeliveryOrder(dto.getDoId(),dto.getOrderID(),dto.getDriverID(),dto.getDeparture(),
                dto.getArrival(),dto.getFee(),dto.getStatus()));
    }

    @Override
    public DeliveryOrderDTO searchDO(String id) throws Exception {
        DeliveryOrder order = dao.get(id);
        if(order != null) {
            return new DeliveryOrderDTO(order.getDoId(),order.getOrderID(),order.getDriverID(),order.getDeparture(),
                    order.getArrival(),order.getFee(),order.getStatus());
        }else {
            return null;
        }
    }

    @Override
    public boolean updateDO(DeliveryOrderDTO dto,String s) throws Exception {
        try{
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isUpdateDO = dao.update(new DeliveryOrder(dto.getDoId(), dto.getArrival(),dto.getStatus()));
            boolean isUpdateOrder = orderBo.updateOrder(new OrderDTO(s,dto.getStatus()));
            if (isUpdateDO && isUpdateOrder ) {
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
