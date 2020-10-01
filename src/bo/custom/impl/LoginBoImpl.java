package bo.custom.impl;

import bo.custom.LoginBo;
import dao.DaoFactory;
import dao.custom.LoginDAO;
import dto.LoginDTO;
import entity.Login;

public class LoginBoImpl implements LoginBo {
    LoginDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DAOType.LOGIN);
    @Override
    public boolean saveRegistration(LoginDTO dto) throws Exception {
        return dao.save(new Login(dto.getName(),dto.getUserName(),dto.getPassword()));
    }

    @Override
    public LoginDTO checkLogin(String userName) throws Exception {
        Login login = dao.get(userName);
        if(login!=null){
            return new LoginDTO(login.getName(),login.getName(),login.getPassword());
        }else {
            return null;
        }
    }
}
