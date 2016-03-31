package hk.suitup.Service;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.suitup.Bean.Order;

/**
 * Created by hk on 2015/9/5.
 */
public class PurchaseService {
    private Order order;
    private List<String> temp;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PurchaseService(Order order) {
        this.order = order;
        Map map = new HashMap();
        map.put("function", "Purchase");

        Gson gson = new Gson();
        String func_name=gson.toJson(map);

        order.setType("Client");
        String order_str = gson.toJson(order);

        //PurchaseInfo purchaseInfo = new PurchaseInfo("Client",order);
        //String para = gson.toJson(purchaseInfo);

        //form a json array
        temp=new ArrayList<String>();
        temp.add(func_name);
        temp.add(order_str);

    }

    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
    }

    public String formLength(){
        int length=2*(temp.size()-1)+2+temp.get(0).length()+temp.get(1).length();
        Map map = new HashMap();

        Gson gson = new Gson();
        map.put("length", length);
        String length_of_message = gson.toJson(map)+"<EOF>";

        return length_of_message;
    }

    public class PurchaseInfo{
        private String type;
        private Order order;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public PurchaseInfo(String type, Order order) {
            this.type = type;
            this.order = order;
        }
    }
}
