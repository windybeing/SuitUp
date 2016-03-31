package hk.suitup.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.suitup.Bean.Manufacturer;
import hk.suitup.Bean.QueryInfo;
import hk.suitup.Bean.SuitInfo;
import hk.suitup.SSLAccess.SSLClient;

/**
 * Created by sony on 2015/7/27.
 */
public class QueryService implements Serializable{

    private String type;
    private int page;
    private int size;
    private List<String> temp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
    }

    public QueryService(int page, int size) {
        this.page = page;
        this.size = size;

        Map map = new HashMap();
        map.put("function", "Query");

        Gson gson = new Gson();
        String func_name=gson.toJson(map);

        QueryInfo queryInfo = new QueryInfo("Suit",String.valueOf(page),String.valueOf(size));
        String para = gson.toJson(queryInfo);

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

    public List<SuitInfo> getFiveSuitDemo(int page){

        List<SuitInfo> suits = new ArrayList<>();
        for(int i = page*5+0 ;i < page*5+5 ;i++){
            Suit suit = new Suit();
            suit.setManufacturer_id("manufacturer"+String.valueOf(i));
            suit.setPrice(100);
            suit.setSuitname(String.valueOf(i));
            suit.setPhoto_id(null);
            suit.set_id(null);
            suit.setInformation(null);
            SuitInfo suitInfo= new SuitInfo();
            suitInfo.setName(String.valueOf(i));
            //suitInfo.setPic_name("clothes3");
            suitInfo.setSuit(suit);
            suits.add(suitInfo);
        }
        return suits;
    }

    public List<SuitInfo> getFiveSuit(String input){
        JsonParser jsonParser = new JsonParser();
        JsonArray array = jsonParser.parse(input).getAsJsonArray();
        JsonArray suit_array = jsonParser.parse(array.get(1).toString()).getAsJsonArray();
        Gson gson = new Gson();

        List<String> photo_id = new ArrayList<>();
        List<String> manufacturers_id = new ArrayList<>();
        List<SuitInfo> suits = new ArrayList<>();
        for (int i = 0;i < 5; i++){
            Suit suit = gson.fromJson(suit_array.get(i), Suit.class);
            SuitInfo suitInfo= new SuitInfo();
            //String pic =  queryImage(suit.get_id());

            photo_id.clear();
            photo_id.add(suit.getPhoto_id());
            GetImageService getImageService = new GetImageService(photo_id,"Photo");
            String length = getImageService.formLength();
            SSLClient.sendMessage(length + "\r\n");
            SSLClient.sendMessage(getImageService.getTemp());

            //receive the signal that server is ready
            SSLClient.getMessageLength();
            String signal = SSLClient.getResponse();

            //receive picture's string
            int a = SSLClient.getMessageLength();
            String pic  = SSLClient.getResponse();

            manufacturers_id.clear();
            manufacturers_id.add(suit.getManufacturer_id());

            suitInfo.setManufacturer_name(getName(manufacturers_id));
            suitInfo.setName(suit.getSuitname());
            suitInfo.setPic_name(pic);
            suitInfo.setSuit(suit);
            suits.add(suitInfo);
        }
        return suits;
    }

    private String getName(List<String> id){

        GetManufacturerService getManufacturerService = new GetManufacturerService(id,"Manufacturer");
        String length = getManufacturerService.formLength();
        SSLClient.sendMessage(length + "\r\n");
        SSLClient.sendMessage(getManufacturerService.getTemp());

        SSLClient.getMessageLength();
        String response = SSLClient.getResponse();

        JsonParser jsonParser = new JsonParser();
        JsonArray array = jsonParser.parse(response).getAsJsonArray();
        JsonArray manufacture_array = jsonParser.parse(array.get(1).toString()).getAsJsonArray();
        Gson gson = new Gson();

        Manufacturer manufacturer = gson.fromJson(manufacture_array.get(0),Manufacturer.class);

        return manufacturer.getManufacturername();
    }

    public class Suit implements Serializable{
        private static final long serialVersionUID = -7060210544600464482L;

        private String manufacturer_id;
        private Double price;
        private List<String> information;
        private String _id;
        private String suitname;
        private String photo_id;

        public String getManufacturer_id() {
            return manufacturer_id;
        }

        public void setManufacturer_id(String manufacturer_id) {
            this.manufacturer_id = manufacturer_id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public List<String> getInformation() {
            return information;
        }

        public void setInformation(List<String> information) {
            this.information = information;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getSuitname() {
            return suitname;
        }

        public void setSuitname(String suitname) {
            this.suitname = suitname;
        }

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }
    }
}
