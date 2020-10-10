package bo.custom.impl;

import bo.custom.OrderDetailBo;
import dao.DaoFactory;
import dao.custom.OrderDetailDAO;
import dto.OrderDetailDTO;
import dto.PurchaseDetailDTO;
import entity.OrderDetail;
import entity.PurchaseDetail;

import java.util.ArrayList;

public class OrderDetailBoImpl implements OrderDetailBo {
    OrderDetailDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.ORDERDETAIL);

    @Override
    public boolean addOrderDetail(ArrayList<OrderDetailDTO> orderDetailDTOS) throws Exception {
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOS) {
            boolean b = addOrderDetail(orderDetailDTO);
            if(!b){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addOrderDetail(OrderDetailDTO detailDTO) throws Exception {
        return dao.save(new OrderDetail(detailDTO.getId(), detailDTO.getCode(),
                detailDTO.getQty(),detailDTO.getPrice(),detailDTO.getDiscount()));
    }
}
