package bo.custom.impl;

import bo.BoFactory;
import bo.custom.PurchaseBo;
import bo.custom.PurchaseDetailBo;
import bo.custom.SeaFoodBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.PurchaseDAO;
import dao.custom.PurchaseDetailDAO;
import dao.custom.SeaFoodDAO;
import db.DBConnection;
import dto.PurchaseDTO;
import dto.PurchaseDetailDTO;
import dto.SeaFoodDTO;
import entity.PurchaseDetail;
import entity.Purchased;
import entity.SeaFood;

import java.sql.Connection;
import java.util.ArrayList;


public class PurchaseBoImpl implements PurchaseBo {
    QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);
    PurchaseDAO pDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.PURCHASE);
    PurchaseDetailBo purchaseDetailBo = BoFactory.getInstance().getBo(BoFactory.BOType.PURCHASEDETAIL);
    SeaFoodBo seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);


    @Override
    public String getPurchaseId() throws Exception {
        return qDao.getPurchaseId();
    }

    @Override
    public boolean savePurchase(PurchaseDTO dto,ArrayList<PurchaseDetailDTO> detailDTO) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            if(pDao.save(new Purchased(dto.getId(),dto.getDate(),dto.getBoatId()))){
                if(purchaseDetailBo.addPurchaseDetail(detailDTO)){
                    if(seaFoodBo.UpdateStockWhenPurchase(detailDTO)){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
