package bo.custom;

import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.util.ArrayList;

public interface OrderBo {
    public String getOrderId() throws Exception;
    public boolean saveOrder(OrderDTO dto, ArrayList<OrderDetailDTO> orderDetailDTOS) throws Exception;
    public boolean updateOrder(OrderDTO dto) throws Exception;
}
