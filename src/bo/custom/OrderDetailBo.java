package bo.custom;

import dto.OrderDetailDTO;
import dto.PurchaseDetailDTO;

import java.util.ArrayList;

public interface OrderDetailBo {
    public boolean addOrderDetail(ArrayList<OrderDetailDTO> orderDetailDTOS) throws Exception;
    public boolean addOrderDetail(OrderDetailDTO detailDTO) throws Exception;
}
