package bo.custom;

import dto.LoginDTO;

public interface LoginBo {
    public boolean saveRegistration(LoginDTO dto) throws Exception;
    public LoginDTO checkLogin(String userName) throws Exception;
}
