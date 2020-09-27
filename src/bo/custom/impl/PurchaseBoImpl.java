package bo.custom.impl;

import bo.custom.PurchaseBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.PurchaseDAO;
import dao.custom.PurchaseDetailDAO;
import dao.custom.SeaFoodDAO;
import dto.PurchaseDTO;
import dto.PurchaseDetailDTO;
import dto.SeaFoodDTO;
import entity.PurchaseDetail;
import entity.Purchased;
import entity.SeaFood;

import java.util.ArrayList;


public class PurchaseBoImpl implements PurchaseBo {
    QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);
    PurchaseDAO pDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.PURCHASE);
    PurchaseDetailDAO detailDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.PURCHASEDETAIL);
    SeaFoodDAO foodDAO = DaoFactory.getInstance().getDao(DaoFactory.DAOType.SEAFOOD);


    @Override
    public String getPurchaseId() throws Exception {
        return qDao.getPurchaseId();
    }

    @Override
    public boolean savePurchase(PurchaseDTO dto, ArrayList<PurchaseDetailDTO> purchaseDetailDTOS, SeaFoodDTO foodDTO) throws Exception {
         boolean isPurchaseSaved =  pDao.save(new Purchased(dto.getId(),dto.getDate(),dto.getBoatId()));
        // boolean isPurchaseDetailSaved = detailDAO.save(purchaseDetailDTOS);
         boolean isUpdated = foodDAO.update(new SeaFood(foodDTO.getCode(),foodDTO.getDescription(),foodDTO.getQtyOnHand(),
                 foodDTO.getPurchasePrice(),foodDTO.getSellingPrice()));

         if(isPurchaseSaved && /*isPurchaseDetailSaved &&*/ isUpdated){
             return true;
         }
         return false;
    }
}
