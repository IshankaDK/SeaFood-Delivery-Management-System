package bo.custom.impl;

import bo.custom.QuickOrderDetailBo;
import dao.DaoFactory;
import dao.custom.OrderDetailDAO;
import dao.custom.QuickOrderDetailDAO;
import dto.OrderDetailDTO;
import dto.QuickOrderDetailDTO;
import entity.OrderDetail;
import entity.QuickOrderDetail;

import java.util.ArrayList;

public class QuickOrderDetailBoImpl implements QuickOrderDetailBo {

    QuickOrderDetailDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUICKDETAIL);

    @Override
    public boolean addOrderDetail(ArrayList<QuickOrderDetailDTO> orderDetailDTOS) throws Exception {
        for (QuickOrderDetailDTO orderDetailDTO : orderDetailDTOS) {
            boolean b = addOrderDetail(orderDetailDTO);
            if(!b){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addOrderDetail(QuickOrderDetailDTO detailDTO) throws Exception {
        return dao.save(new QuickOrderDetail(detailDTO.getId(), detailDTO.getCode(),
                detailDTO.getQty(),detailDTO.getPrice(),detailDTO.getDiscount()));
    }
}
