package hk.suitup.Service;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.suitup.Bean.RegisterInfo;

/**
 * Created by hk on 2015/9/4.
 */
public class RegisterService {

    private List<String> temp;
    private String username;
    private String password;
    private String phonenumber;
    private String email;

    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
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

    public RegisterService(String username, String password, String phonenumber, String email) {
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
        this.email = email;

        Map map = new HashMap();
        map.put("function", "Register");

        Gson gson = new Gson();
        String func_name=gson.toJson(map);

        RegisterInfo registerInfo = new RegisterInfo("Client",username,password,phonenumber,email);
        String para = gson.toJson(registerInfo);

        temp=new ArrayList<String>();
        temp.add(func_name);
        temp.add(para);
    }

    public String formLength(){
        int length=2*(temp.size()-1)+2+temp.get(0).length()+temp.get(1).length();
        Map map = new HashMap();

        Gson gson = new Gson();
        map.put("length", length);
        String length_of_message = gson.toJson(map)+"<EOF>";

        return length_of_message;
    }
}
