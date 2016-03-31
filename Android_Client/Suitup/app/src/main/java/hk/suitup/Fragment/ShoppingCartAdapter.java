package hk.suitup.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import hk.suitup.Activity.MainActivity;
import hk.suitup.Activity.ShowShoppingCartActivity;
import hk.suitup.Bean.Cart;
import hk.suitup.R;

public class ShoppingCartAdapter extends BaseAdapter {
    ShowShoppingCartActivity context;
    List<Cart> data;
    int layout;

    public ShoppingCartAdapter(ShowShoppingCartActivity context, List<Cart> data,
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
    public View getView (final int index, View view, ViewGroup group){
        View v= LayoutInflater.from(context).inflate(layout, null);
        TextView name=(TextView) v.findViewById(R.id.shopcartlist_item_name);
        name.setText(data.get(index).getSuitInfo().getName());
        TextView price=(TextView) v.findViewById(R.id.shopcartlist_item_price);
        price.setText("Price: "+data.get(index).getSuitInfo().getSuit().getPrice());
        final EditText count=(EditText) v.findViewById(R.id.shopcartlist_item_acount);
        count.setText(String.valueOf(data.get(index).getCount()));

        ImageButton sub=(ImageButton) v.findViewById(R.id.shopcartlist_item_sub);
        ImageButton add = (ImageButton) v.findViewById(R.id.shopcartlist_item_add);
        //set sub button click listener
        sub.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View arg0) {
                                       int n = Integer.parseInt(count.getText().toString());
                                       n--;
                                       if (n < 1) n = 1;
                                       count.setText(String.valueOf(n));
                                       data.get(index).setCount(n);
                                       MainActivity.carts = data;
                                       context.updateTotal();
                                   }
                               }
        );
        //set add button click listener
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int n = Integer.parseInt(count.getText().toString())+1;
                count.setText(String.valueOf(n));
                data.get(index).setCount(n);
                MainActivity.carts = data;
                context.updateTotal();
            }});
        return v;
    }
}
