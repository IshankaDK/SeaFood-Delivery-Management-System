package bo.custom.impl;

import bo.custom.DriverBo;
import dao.DaoFactory;
import dao.QueryDAO;
import dao.custom.DriverDao;
import dto.DriverDTO;
import entity.Driver;

import java.util.ArrayList;

public class DriverBoImpl implements DriverBo {

    private DriverDao dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.DRIVER);
    private QueryDAO qDao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.QUERY);

    @Override
    public boolean saveDriver(DriverDTO dto) throws Exception {
        return dao.save(new Driver(dto.getId(),dto.getName(),
                dto.getAddress(),dto.getContact()));
    }

    @Override
    public boolean deleteDriver(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean updateDriver(DriverDTO dto) throws Exception {
        return dao.update(new Driver(dto.getId(),dto.getName(),dto.getAddress(),dto.getContact()));
    }

    @Override
    public DriverDTO getDriver(String id) throws Exception {
        Driver driver = dao.get(id);
        if(driver != null) {
            return new DriverDTO(driver.getId(), driver.getName(),
                    driver.getAddress(), driver.getContact());
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<DriverDTO> getAllDriver() throws Exception {
        ArrayList<Driver>arrayList = dao.getAll();
        ArrayList<DriverDTO> dtoList = new ArrayList<>();
        for (Driver driver : arrayList) {
            dtoList.add(new DriverDTO(driver.getId(),driver.getName(),driver.getAddress(),
                    driver.getContact()));
        }
        return dtoList;
    }

    @Override
    public String getDriverID() throws Exception {
        return qDao.getDriverId();
    }
}
