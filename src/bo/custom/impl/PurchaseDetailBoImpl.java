package bo.custom.impl;

import bo.custom.PurchaseDetailBo;
import dao.DaoFactory;
import dao.custom.PurchaseDetailDAO;
import dto.PurchaseDetailDTO;
import entity.PurchaseDetail;

import java.util.ArrayList;

public class PurchaseDetailBoImpl implements PurchaseDetailBo {

    PurchaseDetailDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.PURCHASEDETAIL);
    @Override
    public boolean addPurchaseDetail(ArrayList<PurchaseDetailDTO> purchaseDetailDTOS) throws Exception {
        for (PurchaseDetailDTO purchaseDetailDTO : purchaseDetailDTOS) {
            boolean b = addPurchaseDetail(purchaseDetailDTO);
            if(!b){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addPurchaseDetail(PurchaseDetailDTO detailDTO) throws Exception {
        return dao.save(new PurchaseDetail(detailDTO.getId(), detailDTO.getCode(),
                detailDTO.getQty(),detailDTO.getPurchasedPrice()));
    }
}
