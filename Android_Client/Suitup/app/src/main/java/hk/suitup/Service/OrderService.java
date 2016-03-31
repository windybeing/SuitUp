package hk.suitup.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.suitup.Bean.Order;

/**
 * Created by hk on 2015/9/10.
 */
public class OrderService implements Serializable{

    private String type;
    private int page;
    private int size;
    private String userid;
    private List<String> temp;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    public OrderService(int page, int size,String userid) {
        this.page = page;
        this.size = size;
        this.userid = userid;

        Map map = new HashMap();
        map.put("function", "Query");

        Gson gson = new Gson();
        String func_name=gson.toJson(map);

        OrderInfo orderInfo = new OrderInfo("OrderByClient",String.valueOf(page),String.valueOf(size),userid);
        String para = gson.toJson(orderInfo);

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

    public List<Order> getFiveOrder(String input){
        JsonParser jsonParser = new JsonParser();
        JsonArray array = jsonParser.parse(input).getAsJsonArray();

        List<Order> orders = new ArrayList<>();

        if(array.get(1).toString().equals("null")||array.get(1)==null) return orders;
        JsonArray order_array = jsonParser.parse(array.get(1).toString()).getAsJsonArray();
        Gson gson = new Gson();

        int times = (5>order_array.size())?order_array.size():5;
        for (int i = 0;i < times; i++){
            Order order = gson.fromJson(order_array.get(i), Order.class);
            if (order.get_id()==null||order.get_id().equals("")) return orders;
            orders.add(order);
        }
        return orders;
    }

    public class OrderInfo {
        private String type;
        private String page;
        private String size;
        private String client_id;

        public OrderInfo(String type, String page, String size,String client_id) {
            this.type = type;
            this.page = page;
            this.size = size;
            this.client_id = client_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }
    }
}
