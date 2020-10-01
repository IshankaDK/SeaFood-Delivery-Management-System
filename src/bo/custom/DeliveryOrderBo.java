package bo.custom;

import dto.ClientDTO;
import dto.DeliveryOrderDTO;
import dto.DriverDTO;

import java.util.ArrayList;

public interface DeliveryOrderBo {
    public String getDoId() throws Exception;
    public ClientDTO getClient(String id) throws Exception;
    public boolean saveDO(DeliveryOrderDTO dto) throws Exception;
    public DeliveryOrderDTO searchDO(String id) throws Exception;
    public boolean updateDO(DeliveryOrderDTO dto,String s) throws Exception;
}
