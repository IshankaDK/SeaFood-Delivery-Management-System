package dao.custom;

import dao.CrudDAO;
import entity.SeaFood;

public interface SeaFoodDAO extends CrudDAO<SeaFood,String> {
    public boolean updateWhenPurchase(SeaFood seaFood) throws Exception;
    public boolean updateWhenOrder(SeaFood seaFood) throws Exception;
}
