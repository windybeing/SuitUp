package hk.suitup.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import hk.suitup.Bean.Cart;
import hk.suitup.Bean.SuitInfo;
import hk.suitup.Bean.User;
import hk.suitup.Fragment.FragmentAdapter;
import hk.suitup.Fragment.HomeFragment;
import hk.suitup.Fragment.ProfileFragment;
import hk.suitup.Fragment.ShareFragment;
import hk.suitup.R;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    private ViewPager mViewPager;

    private SuitInfo suitInfo;
    public static List<Cart> carts;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //keep track of the user information
        user = (User)getIntent().getSerializableExtra("user");
        suitInfo = (SuitInfo) getIntent().getSerializableExtra("cart");

        carts =  new ArrayList<>();
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);

        //ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //add fragment title
        List<String> titles = new ArrayList<>();
        titles.add("Home");
        titles.add("Products");
        titles.add("Profile");

        //new three fragments
        ShareFragment shareFragment = new ShareFragment();
        shareFragment.setContext(getApplicationContext());

        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setUser(user);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(shareFragment);
        fragments.add(profileFragment);

        //set fragment adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);

        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

