package hk.suitup.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import hk.suitup.Bean.Cart;
import hk.suitup.Bean.Order;
import hk.suitup.Bean.OrderItem;
import hk.suitup.Bean.SuitInfo;
import hk.suitup.Bean.User;
import hk.suitup.Fragment.ShoppingCartAdapter;
import hk.suitup.R;
import hk.suitup.SSLAccess.SSLClient;
import hk.suitup.Service.PurchaseService;

public class ShowShoppingCartActivity extends ActionBarActivity {

    private SuitInfo suitInfo;

    private TextView textView;
    private ListView listView;
    private EditText editText;
    private Button button;
    private List<Cart> carts;
    private ShoppingCartAdapter shoppingCartAdapter;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateTotal(){
        float total = 0;
        for(int i = 0;i<carts.size();i++){
            total+=carts.get(i).getCount()*carts.get(i).getSuitInfo().getSuit().getPrice();
            textView.setText(String.valueOf(total));
        }
    }

    private PurchaseService purchaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shopping_cart);
        carts= new ArrayList<>();
        carts = (List<Cart>)getIntent().getParcelableArrayListExtra("carts").get(0);
        textView = (TextView) findViewById(R.id.shoppingcart_list);
        button = (Button) findViewById(R.id.buy_button);
        listView = (ListView) findViewById(R.id.shopcart_listview);
        shoppingCartAdapter = new ShoppingCartAdapter(this,carts,R.layout.activity_shopping_cart_adapter);
        listView.setAdapter(shoppingCartAdapter);
        editText = (EditText) findViewById(R.id.address_input);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = editText.getText().toString();
                if(address.isEmpty()||address.equals(null)){
                    editText.setError("This field can't be empty");
                }
                else{

                    Order tmp = formOrder(carts,address);

                    //new service to generate message
                purchaseService = new PurchaseService(tmp);
                SSLClient.sendMessage(purchaseService.formLength() + "\r\n");
                SSLClient.sendMessage(purchaseService.getTemp());

                int response_length = SSLClient.getMessageLength();
                BufferedReader reader = SSLClient.getReader();

                //get response
                char[] response = new char[response_length];
                try {
                    reader.read(response, 0, response_length);
                }
                catch (Exception e){
                    return;
                }

                String response_str;
                response_str = new String(response);

                Boolean ret = SSLClient.checkReturn(response_str);

                if (ret==true) {
                    carts.clear();
                    MainActivity.carts.clear();
                    //set mention information
                    updateTotal();
                    Toast.makeText(getApplicationContext(), "Success! You can check the record in orders.", Toast.LENGTH_LONG).show();
                    shoppingCartAdapter = new ShoppingCartAdapter(ShowShoppingCartActivity.this, carts, R.layout.activity_shopping_cart_adapter);
                    listView.setAdapter(shoppingCartAdapter);
                }
                else{
                    //set mention information
                    Toast.makeText(getApplicationContext(), "The network may fail, please login to retry", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                }
            }
        });
        updateTotal();
    }

    private Order formOrder(List<Cart> carts,String address){
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        for(int i = 0 ; i<carts.size(); i++){
            //set order items
            OrderItem orderItem = new OrderItem();
            orderItem.setCount(carts.get(i).getCount());
            orderItem.setManufacturer_id(carts.get(i).getSuitInfo().getSuit().getManufacturer_id());
            orderItem.setSuit_id(carts.get(i).getSuitInfo().getSuit().get_id());
            orderItem.setPrice(carts.get(i).getSuitInfo().getSuit().getPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        user= (User)getIntent().getSerializableExtra("user");
        //set order's parameters
        order.setAddress(address);
        order.setReceiver(user.getUsername());
        order.setPhonenumber(user.getPhonenumber());
        order.setClient_id(user.get_id());

        return order;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public SuitInfo getSuitInfo() {
        return suitInfo;
    }

    public void setSuitInfo(SuitInfo suitInfo) {
        this.suitInfo = suitInfo;
    }
}
