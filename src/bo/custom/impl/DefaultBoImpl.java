package bo.custom.impl;

import bo.custom.DefaultBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dto.SeaFoodDTO;
import entity.SeaFood;

import java.util.ArrayList;

public class DefaultBoImpl implements DefaultBo {
    QueryDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);

    @Override
    public int getTotalOrders() throws Exception {
        return dao.getTotalOrders();
    }

    @Override
    public int getTotalClient() throws Exception {
        return dao.getTotalClient();
    }

    @Override
    public int getTotalDelivery() throws Exception {
        return dao.getTotalDelivery();
    }

    @Override
    public int getTotalSeafood() throws Exception {
        return dao.getTotalSeafood();
    }

    @Override
    public ArrayList<SeaFoodDTO>  getMostMovable() throws Exception {
        ArrayList<SeaFood> arrayList = dao.getMostMovable();
        ArrayList<SeaFoodDTO> dtoList = new ArrayList<>();
        for (SeaFood seaFood : arrayList) {
            dtoList.add(new SeaFoodDTO(seaFood.getCode(),seaFood.getDescription(),seaFood.getQtyOnHand(),
                    seaFood.getPurchasePrice(),seaFood.getSellingPrice()));
        }
        return dtoList;
    }

    @Override
    public ArrayList<SeaFoodDTO>  getLeastMovable() throws Exception {
        ArrayList<SeaFood> arrayList = dao.getLeastMovable();
        ArrayList<SeaFoodDTO> dtoList = new ArrayList<>();
        for (SeaFood seaFood : arrayList) {
            dtoList.add(new SeaFoodDTO(seaFood.getCode(),seaFood.getDescription(),seaFood.getQtyOnHand(),
                    seaFood.getPurchasePrice(),seaFood.getSellingPrice()));
        }
        return dtoList;
    }

}
