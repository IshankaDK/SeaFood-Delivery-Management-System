package bo.custom.impl;

import bo.custom.ClientBo;
import dao.DaoFactory;
import dao.custom.ClientDAO;
import dto.ClientDTO;
import entity.Client;

import java.util.ArrayList;

public class ClientBoImpl implements ClientBo {
    private ClientDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.CLIENT);


    @Override
    public boolean saveClient(ClientDTO dto) throws Exception {
        return dao.save(new Client(dto.getId(),dto.getName(),
                dto.getAddress(),dto.getContact(),dto.getType()));
    }

    @Override
    public boolean deleteClient(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean updateClient(ClientDTO dto) throws Exception {
        return dao.update(new Client(dto.getId(),dto.getName(),dto.getAddress(),dto.getContact(),dto.getType()));
    }

    @Override
    public ArrayList<ClientDTO> getAllClient() throws Exception {
        ArrayList<Client>arrayList = dao.getAll();
        ArrayList<ClientDTO> dtoList = new ArrayList<>();
        for (Client client : arrayList) {
            dtoList.add(new ClientDTO(client.getId(),client.getName(),client.getAddress(),
                    client.getContact(),client.getType()));
        }
        return dtoList;
    }
}
