package hk.suitup.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import hk.suitup.Bean.Cart;
import hk.suitup.Bean.SuitInfo;
import hk.suitup.Bean.User;
import hk.suitup.Fragment.ProfileAdapter;
import hk.suitup.Fragment.ProfileFragment;
import hk.suitup.R;

public class ProductDetailActivity extends AppCompatActivity {

    private SuitInfo suitInfo;
    private TextView mTextview;
    private ImageView mImageview;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Product Detail");

        //set suit's detail information
        suitInfo = (SuitInfo) getIntent().getSerializableExtra("suit");

        mTextview  = (TextView) findViewById(R.id.product_info);
        mImageview = (ImageView) findViewById(R.id.product_image);
        listView = (ListView) findViewById(R.id.listdetail);

        //add to a list for list view
        List<String> data =new ArrayList<>();
        data.add("Manufacturer:  " + suitInfo.getManufacturer_name());
        data.add("Suitname:  " + suitInfo.getSuit().getSuitname());
        data.add("Price:  " + suitInfo.getSuit().getPrice());

        //set list view adapter
        Adapter adapter = new Adapter(getApplicationContext(),data,R.layout.activity_profile_adapter);
        listView.setAdapter(adapter);

        //get picture from the bytes array
        byte[] pic= new byte[0];
        try {
            pic = suitInfo.getPic_name().getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        mImageview.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
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

    public void addShoppingCart(View view){
        // to send target suit for shopping cart

        Intent intent = new Intent(this, ShowShoppingCartActivity.class);
        Bundle bundle = new Bundle();

        ArrayList list = new ArrayList<>();
        Cart cart = new Cart();
        cart.setCount(1);
        cart.setSuitInfo(suitInfo);
        MainActivity.carts.add(cart);
        list.add(MainActivity.carts);

        //add to the user's shopping cart
        User user = (User)getIntent().getSerializableExtra("user");
        bundle.putParcelableArrayList("carts", list);
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        Toast.makeText(this,"Success add to shopping cart!",Toast.LENGTH_LONG).show();
        startActivity(intent);

    }

    private class Adapter extends BaseAdapter{
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
