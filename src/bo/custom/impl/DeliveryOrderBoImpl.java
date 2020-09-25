package bo.custom.impl;

import bo.custom.DeliveryOrderBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dto.ClientDTO;
import dto.DriverDTO;
import entity.Driver;

import java.util.ArrayList;

public class DeliveryOrderBoImpl implements DeliveryOrderBo {

    QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);

    @Override
    public String getDoId() throws Exception {
        return qDao.getDoId();
    }


}
