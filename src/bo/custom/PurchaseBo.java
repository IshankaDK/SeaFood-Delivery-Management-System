package bo.custom;

import dto.PurchaseDTO;
import dto.PurchaseDetailDTO;
import dto.SeaFoodDTO;

import java.util.ArrayList;

public interface PurchaseBo {
    public String getPurchaseId() throws Exception;
    public boolean savePurchase(PurchaseDTO dto, ArrayList<PurchaseDetailDTO> purchaseDetailDTOS, SeaFoodDTO foodDTO) throws Exception;

}
