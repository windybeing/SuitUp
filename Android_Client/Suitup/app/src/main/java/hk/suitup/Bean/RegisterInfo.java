package hk.suitup.Bean;

/**
 * Created by hk on 2015/9/4.
 */
public class RegisterInfo {

    private String type;
    private String username;
    private String password;
    private String phonenumber;
    private String email;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegisterInfo(String type, String username, String password, String phonenumber, String email) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
        this.email = email;
    }
}
