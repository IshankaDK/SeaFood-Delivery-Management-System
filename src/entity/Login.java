package entity;

public class Login implements SuperEntity {
    private String name;
    private String userName;
    private String password;

    public Login(String name, String userName, String password) {
        this.setName(name);
        this.setUserName(userName);
        this.setPassword(password);
    }

    public Login() {
    }
    public Login(String userName, String password) {
        this.setUserName(userName);
        this.setPassword(password);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
