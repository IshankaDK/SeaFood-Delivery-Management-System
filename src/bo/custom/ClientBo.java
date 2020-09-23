package bo.custom;

import dto.ClientDTO;

import java.util.ArrayList;

public interface ClientBo {
    public boolean saveClient(ClientDTO dto) throws Exception;
    public boolean deleteClient(String id) throws Exception;
    public boolean updateClient(ClientDTO dto) throws Exception;
    public ArrayList<ClientDTO> getAllClient() throws  Exception;

}
