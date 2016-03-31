package hk.suitup.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import hk.suitup.Activity.ChangeInfoActivity;
import hk.suitup.Activity.MainActivity;
import hk.suitup.Activity.ShowOrdersActivity;
import hk.suitup.Activity.ShowShoppingCartActivity;
import hk.suitup.Bean.Cart;
import hk.suitup.Bean.User;
import hk.suitup.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View mParentView;
    private RecyclerView mRecyclerView;
    private TextView textView;
    private Button button;
    private Button cartbutton;
    private Button orderbutton;
    private Button changeButton;

    private User user;

    public ProfileFragment() {
    }

    public static List<Property> properties;
    private ListView listView;
    private ProfileAdapter profileAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_profile, container, false);
        properties = new ArrayList<>();
        Property temp = new Property();
        temp.setName("Email");temp.setValue(user.getEmail());
        properties.add(temp);
        Property temp1 = new Property();
        temp1.setName("Password");temp1.setValue(user.getPassword());
        properties.add(temp1);
        Property temp2 = new Property();
        temp2.setName("Address");temp2.setValue(user.getAddress());
        properties.add(temp2);
        Property temp3 = new Property();
        temp3.setName("Age");temp3.setValue(user.getAge());
        properties.add(temp3);
        Property temp4 = new Property();
        temp4.setName("Phone number");temp4.setValue(user.getPhonenumber());
        properties.add(temp4);

        listView = (ListView) mParentView.findViewById(R.id.listview);
        profileAdapter = new ProfileAdapter(this,properties,R.layout.activity_profile_adapter);
        listView.setAdapter(profileAdapter);

        cartbutton = (Button) mParentView.findViewById(R.id.cart_button);
        orderbutton = (Button) mParentView.findViewById(R.id.order_button);
        changeButton = (Button) mParentView.findViewById(R.id.change_button);

        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowShoppingCartActivity.class);
                Bundle bundle = new Bundle();
                ArrayList list = new ArrayList<>();

                list.add(MainActivity.carts);

                bundle.putParcelableArrayList("carts", list);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowOrdersActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeInfoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void setUser(User user) {
        this.user = user;
    }

    public class Property{
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void clear(){
            this.name = null;
            this.value = null;
        }
    }
}
