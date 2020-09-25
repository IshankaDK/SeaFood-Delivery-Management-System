package bo.custom.impl;

import bo.custom.BoatBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.BoatDAO;
import dto.BoatDTO;
import entity.Boat;

import java.util.ArrayList;

public class BoatBoImpl implements BoatBo {

    private BoatDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.BOAT);
    private QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);

    @Override
    public boolean saveBoat(BoatDTO dto) throws Exception {
        return dao.save(new Boat(dto.getBoatId(),dto.getName(),dto.getOwnerName(),
                dto.getOwnerContact()));
    }

    @Override
    public boolean deleteBoat(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean updateBoat(BoatDTO dto) throws Exception {
        return dao.update(new Boat(dto.getBoatId(),dto.getName(),dto.getOwnerName(),dto.getOwnerContact()));
    }

    @Override
    public BoatDTO getBoat(String id) throws Exception {
        Boat boat = dao.get(id);
        if(boat != null) {
            return new BoatDTO(boat.getBoatId(),boat.getName(),boat.getOwnerName(),
                    boat.getOwnerContact());
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<BoatDTO> getAllBoat() throws Exception {
        ArrayList<Boat>arrayList = dao.getAll();
        ArrayList<BoatDTO> dtoList = new ArrayList<>();
        for (Boat boat : arrayList) {
            dtoList.add(new BoatDTO(boat.getBoatId(),boat.getName(),boat.getOwnerName(),boat.getOwnerContact()));
        }
        return dtoList;
    }

    @Override
    public String getBoatId() throws Exception {
        return qDao.getBoatId();
    }
}
