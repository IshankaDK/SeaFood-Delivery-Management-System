package bo.custom;

import dto.ClientDTO;
import dto.DriverDTO;
import dto.SeaFoodDTO;

import java.util.ArrayList;

public interface DriverBo {
    public boolean saveDriver(DriverDTO dto) throws Exception;
    public boolean deleteDriver(String code) throws Exception;
    public boolean updateDriver(DriverDTO dto) throws Exception;
    public DriverDTO getDriver(String code) throws Exception;
    public ArrayList<DriverDTO> getAllDriver() throws  Exception;
    public String getDriverID() throws  Exception;

}
