package bo.custom;

import dto.DriverDTO;
import dto.SeaFoodDTO;

import java.util.ArrayList;

public interface SeaFoodBo {
    public boolean saveSeaFood(SeaFoodDTO dto) throws Exception;
    public boolean deleteSeaFood(String code) throws Exception;
    public boolean updateSeaFood(SeaFoodDTO dto) throws Exception;
    public SeaFoodDTO getSeaFood(String code) throws Exception;
    public ArrayList<SeaFoodDTO> getAllSeaFood() throws  Exception;
    public String getCode() throws Exception;
}
