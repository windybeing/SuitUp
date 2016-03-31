package hk.suitup.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hk.suitup.Bean.Order;
import hk.suitup.Bean.SuitInfo;
import hk.suitup.Bean.User;
import hk.suitup.R;
import hk.suitup.SSLAccess.SSLClient;
import hk.suitup.Service.OrderService;
import hk.suitup.Service.QueryService;
import hk.tools.SwipeRefreshLoadLayout;

import android.support.v7.widget.Toolbar;

public class ShowOrdersActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLoadLayout mSwipeRefreshLoadLayout;
    private int page;
    private List<Order> orders;
    private User user;
    private ListRecyclerViewAdapter listRecyclerViewAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_order);
        mSwipeRefreshLoadLayout = (SwipeRefreshLoadLayout) findViewById(R.id.recycler_swipe_order);

        LinearLayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        toolbar = (Toolbar) findViewById(R.id.ordertool_bar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //set strict mode
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        page=0;

        //get the user id
        user = (User) getIntent().getSerializableExtra("user");
        OrderService orderService = new OrderService(0,5,user.get_id());

        String length = orderService.formLength();
        //send message to the server
        SSLClient.sendMessage(length + "\r\n");
        SSLClient.sendMessage(orderService.getTemp());

        //get response
        SSLClient.getMessageLength();
        String response = SSLClient.getResponse();

        orders = orderService.getFiveOrder(response);

        //set recycler view adapter
        listRecyclerViewAdapter=new ListRecyclerViewAdapter(mRecyclerView.getContext());
        mRecyclerView.setAdapter(listRecyclerViewAdapter);

        mSwipeRefreshLoadLayout.setOnRefreshListener(new SwipeRefreshLoadLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        mSwipeRefreshLoadLayout.setLoadMoreListener(new SwipeRefreshLoadLayout.LoadMoreListener() {
            @Override
            public void loadMore() {
                loadMoreData();
            }
        });
    }

    private void refreshContent()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mRecyclerView.setAdapter(listRecyclerViewAdapter);
                mSwipeRefreshLoadLayout.setRefreshing(false);
            }
        },1000);
    }
    private void loadMoreData()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //set the handler of load more data
                int lastFlag = orders.size();
                page++;
                OrderService orderService = new OrderService(page,5,user.get_id());

                String length = orderService.formLength();
                SSLClient.sendMessage(length + "\r\n");
                SSLClient.sendMessage(orderService.getTemp());

                //get response
                SSLClient.getMessageLength();
                String response = SSLClient.getResponse();

                List<Order> temp = orderService.getFiveOrder(response);

                //add to the list
                for (int i=0;i<temp.size();i++){
                    orders.add(temp.get(i));
                }

                listRecyclerViewAdapter.notifyDataSetChanged();
                mSwipeRefreshLoadLayout.setLoadMore(false);
            }
        }, 40);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_orders, menu);
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

    private class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {
        private Context mContext;

        public ListRecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //for demo
            holder.addDetails(position);
            // holder.addDetailsDemo(position);
        }

        @Override
        public int getItemCount() {
            //return arrayList.size();
            return orders.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public ImageView mImageView;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.name);
                mImageView = (ImageView) v.findViewById(R.id.pic);
            }

            public void addDetails(final int position) {
                //mTextView.setText(orders.get(position).get_id());

                //get orders time stamp
                Date date = new Date(orders.get(position).getTime().getTime());
                SimpleDateFormat fmtDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E ");

                mTextView.setText(fmtDate.format(date)+"\nOrder " + position);
                mImageView.setImageDrawable(getDrawable(R.drawable.clothes3));
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //start the detail activity
                        Intent intent = new Intent(getApplicationContext(),ShowOrderDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderdetail",orders.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

        }
    }
}
