package bo.custom;

import dto.PurchaseDetailDTO;

import java.util.ArrayList;

public interface PurchaseDetailBo {

    public boolean addPurchaseDetail(ArrayList<PurchaseDetailDTO> purchaseDetailDTOS) throws Exception;
    public boolean addPurchaseDetail(PurchaseDetailDTO detailDTO) throws Exception;
}
