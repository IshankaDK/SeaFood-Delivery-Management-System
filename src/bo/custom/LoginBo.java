package bo.custom;

import dto.LoginDTO;
import entity.Login;

public interface LoginBo {
    public boolean saveRegistration(LoginDTO dto) throws Exception;
    public LoginDTO checkLogin(LoginDTO dto) throws Exception;
}
