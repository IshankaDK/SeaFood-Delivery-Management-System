package bo.custom;

import dto.SeaFoodDTO;

import java.util.ArrayList;

public interface DefaultBo {
    public int getTotalOrders() throws Exception;
    public int getFinishedDO() throws Exception;
    public int getPendingDO() throws Exception;
    public int getTotalSeafood() throws Exception;
    public ArrayList<SeaFoodDTO> getMostMovable() throws Exception;
    public ArrayList<SeaFoodDTO>  getLeastMovable() throws Exception;

}
