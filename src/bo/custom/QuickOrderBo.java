package bo.custom;

import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.QuickOrderDTO;
import dto.QuickOrderDetailDTO;

import java.util.ArrayList;

public interface QuickOrderBo {
    public String getQuickOrderId() throws Exception;
    public boolean saveOrder(QuickOrderDTO dto, ArrayList<QuickOrderDetailDTO> orderDetailDTOS) throws Exception;
}
