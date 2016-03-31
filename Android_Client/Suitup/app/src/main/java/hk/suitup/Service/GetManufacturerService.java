package hk.suitup.Service;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hk on 2015/9/13.
 */
public class GetManufacturerService implements Serializable {

    private List<String> temp;
    public GetManufacturerService(List<String> id,String type) {

        Map map = new HashMap();
        map.put("function", "Get");

        Gson gson = new Gson();
        String func_name=gson.toJson(map);

        ManufacturerInfo manufacturerInfo = new ManufacturerInfo(id,type);
        String para = gson.toJson(manufacturerInfo);

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



    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
    }

    private class ManufacturerInfo{
        private List<String> manufacturers_id;
        private String type;

        public ManufacturerInfo(List<String> manufacturers_id, String type) {
            this.manufacturers_id = manufacturers_id;
            this.type = type;
        }

        public List<String> getManufacturers_id() {
            return manufacturers_id;
        }

        public void setManufacturers_id(List<String> manufacturers_id) {
            this.manufacturers_id = manufacturers_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
