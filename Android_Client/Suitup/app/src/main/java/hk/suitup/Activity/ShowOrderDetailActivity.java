package hk.suitup.Activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import hk.suitup.Bean.Order;
import hk.suitup.R;

public class ShowOrderDetailActivity extends ActionBarActivity {

    private Order order;
    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set toolbar
        setContentView(R.layout.activity_show_order_detail);
        order = (Order) getIntent().getSerializableExtra("orderdetail");
        listView = (ListView) findViewById(R.id.order_detail);
        toolbar = (Toolbar) findViewById(R.id.o_detailtool_bar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //generate a list for list view
        List<String> data = new ArrayList<>();
        data.add("Order id : "+order.get_id());
        data.add("Your address : "+order.getAddress());
        data.add("Receiver phone : "+order.getPhonenumber());
        data.add("Receiver address : "+order.getReceiver());
        for (int i = 0 ;i < order.getOrderItems().size();i++){
            data.add("Product "+String.valueOf(i)+" : \n"
                    //+"Suit id : "+order.getOrderItems().get(i).getSuit_id()
                    +"\nPrice : "+order.getOrderItems().get(i).getPrice()
                    +"\nCount : "+order.getOrderItems().get(i).getCount());
        }

        Adapter adapter = new Adapter(getApplicationContext(),data,R.layout.activity_profile_adapter);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_order_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Adapter extends BaseAdapter {
        Context context;
        List<String> data;
        int layout;

        public Adapter(Context context, List<String> data,
                       int layout) {
            super();
            this.context = context;
            this.data = data;
            this.layout = layout;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int arg0) {
            return data.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView (final int index, View view, ViewGroup group) {
            View v = LayoutInflater.from(context).inflate(layout, null);
            TextView name = (TextView) v.findViewById(R.id.property_name);
            name.setText(data.get(index));

            return v;
        }
    }
}
