package bo.custom;

import dto.OrderDetailDTO;
import dto.QuickOrderDetailDTO;
import entity.QuickOrderDetail;

import java.util.ArrayList;

public interface QuickOrderDetailBo {
    public boolean addOrderDetail(ArrayList<QuickOrderDetailDTO> orderDetailDTOS) throws Exception;
    public boolean addOrderDetail(QuickOrderDetailDTO detailDTO) throws Exception;
}
