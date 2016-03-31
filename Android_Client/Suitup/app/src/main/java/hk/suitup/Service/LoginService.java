package hk.suitup.Service;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.suitup.Bean.LoginInfo;

/**
 * Created by sony on 2015/7/27.
 */
public class LoginService {

    private List<String> temp;
    private String username;
    private String password;

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

    public LoginService(String username, String password) {
        this.username = username;
        this.password = password;

        Map map = new HashMap();
        map.put("function", "Login");

        Gson gson = new Gson();
        String func_name=gson.toJson(map);
        LoginInfo loginInfo=new LoginInfo("Client",username,password);
        String para = gson.toJson(loginInfo);

        //form a json array
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
