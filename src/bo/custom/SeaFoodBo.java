package bo.custom;

import dto.*;

import java.util.ArrayList;

public interface SeaFoodBo {
    public boolean saveSeaFood(SeaFoodDTO dto) throws Exception;
    public boolean deleteSeaFood(String code) throws Exception;
    public boolean updateSeaFood(SeaFoodDTO dto) throws Exception;
    public SeaFoodDTO getSeaFood(String code) throws Exception;
    public ArrayList<SeaFoodDTO> getAllSeaFood() throws  Exception;
    public String getCode() throws Exception;
    public boolean UpdateStockWhenPurchase(ArrayList<PurchaseDetailDTO> purchaseDetailDTOS) throws Exception;
    public boolean UpdateStockWhenPurchase(PurchaseDetailDTO detailDTO) throws Exception;
    public boolean UpdateStockWhenOrder(ArrayList<OrderDetailDTO> orderDetailDTOS) throws Exception;
    public boolean UpdateStockWhenOrder(OrderDetailDTO detailDTO) throws Exception;
    public boolean UpdateStockWhenQuickOrder(ArrayList<QuickOrderDetailDTO> orderDetailDTOS) throws Exception;
    public boolean UpdateStockWhenQuickOrder(QuickOrderDetailDTO detailDTO) throws Exception;
}
