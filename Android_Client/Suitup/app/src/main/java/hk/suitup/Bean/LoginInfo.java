package hk.suitup.Bean;

/**
 * Created by sony on 2015/7/22.
 */
public class LoginInfo {

    private String type;
    private String username;
    private String password;

    public LoginInfo(String type,String username, String password) {
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
