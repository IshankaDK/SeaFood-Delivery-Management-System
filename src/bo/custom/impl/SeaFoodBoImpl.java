package bo.custom.impl;

import bo.custom.SeaFoodBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.SeaFoodDAO;
import dto.*;
import entity.Client;
import entity.SeaFood;

import java.util.ArrayList;

public class SeaFoodBoImpl implements SeaFoodBo {
    SeaFoodDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.SEAFOOD);
    QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);

    @Override
    public boolean saveSeaFood(SeaFoodDTO dto) throws Exception {
        return dao.save(new SeaFood(dto.getCode(),dto.getDescription(),
                dto.getQtyOnHand(),dto.getPurchasePrice(),dto.getSellingPrice()));
    }

    @Override
    public boolean deleteSeaFood(String code) throws Exception {
        return dao.delete(code);
    }

    @Override
    public boolean updateSeaFood(SeaFoodDTO dto) throws Exception {
        return dao.update(new SeaFood(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getPurchasePrice(),dto.getSellingPrice()));
    }

    @Override
    public SeaFoodDTO getSeaFood(String code) throws Exception {
        SeaFood seaFood= dao.get(code);
        if(seaFood != null) {
            return new SeaFoodDTO(seaFood.getCode(), seaFood.getDescription(),
                    seaFood.getQtyOnHand(),seaFood.getPurchasePrice(),seaFood.getSellingPrice());
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<SeaFoodDTO> getAllSeaFood() throws Exception {
        ArrayList<SeaFood>arrayList = dao.getAll();
        ArrayList<SeaFoodDTO> dtoList = new ArrayList<>();
        for (SeaFood seaFood : arrayList) {
            dtoList.add(new SeaFoodDTO(seaFood.getCode(),seaFood.getDescription(),seaFood.getQtyOnHand(),
                    seaFood.getPurchasePrice(),seaFood.getSellingPrice()));
        }
        return dtoList;
    }
    @Override
    public String getCode() throws Exception {
        return qDao.getCode();
    }

    @Override
    public boolean UpdateStockWhenPurchase(ArrayList<PurchaseDetailDTO> purchaseDetailDTOS) throws Exception {
        for (PurchaseDetailDTO purchaseDetailDTO : purchaseDetailDTOS) {
            boolean b = UpdateStockWhenPurchase(purchaseDetailDTO);
            if(!b){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean UpdateStockWhenPurchase(PurchaseDetailDTO detailDTO) throws Exception {
        return dao.updateWhenPurchase(new SeaFood(detailDTO.getCode(),detailDTO.getQty()));
    }

    @Override
    public boolean UpdateStockWhenOrder(ArrayList<OrderDetailDTO> orderDetailDTOS) throws Exception {
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOS) {
            boolean b = UpdateStockWhenOrder(orderDetailDTO);
            if(!b){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean UpdateStockWhenOrder(OrderDetailDTO detailDTO) throws Exception {
        return dao.updateWhenOrder(new SeaFood(detailDTO.getCode(),detailDTO.getQty()));
    }

    @Override
    public boolean UpdateStockWhenQuickOrder(ArrayList<QuickOrderDetailDTO> orderDetailDTOS) throws Exception {
        for (QuickOrderDetailDTO orderDetailDTO : orderDetailDTOS) {
            boolean b = UpdateStockWhenQuickOrder(orderDetailDTO);
            if(!b){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean UpdateStockWhenQuickOrder(QuickOrderDetailDTO detailDTO) throws Exception {
        return dao.updateWhenOrder(new SeaFood(detailDTO.getCode(),detailDTO.getQty()));
    }
}
