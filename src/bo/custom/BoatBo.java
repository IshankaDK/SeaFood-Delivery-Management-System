package bo.custom;

import dto.BoatDTO;
import dto.ClientDTO;

import java.util.ArrayList;

public interface BoatBo {
    public boolean saveBoat(BoatDTO dto) throws Exception;
    public boolean deleteBoat(String id) throws Exception;
    public boolean updateBoat(BoatDTO dto) throws Exception;
    public BoatDTO getBoat(String id) throws Exception;
    public ArrayList<BoatDTO> getAllBoat() throws  Exception;
    public String getBoatId() throws Exception;
}
