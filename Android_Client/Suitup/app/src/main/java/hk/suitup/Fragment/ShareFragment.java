package hk.suitup.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import hk.suitup.Activity.ProductDetailActivity;
import hk.suitup.Bean.SuitInfo;
import hk.suitup.Bean.User;
import hk.suitup.R;
import hk.suitup.Activity.SubActivity;
import hk.suitup.SSLAccess.SSLClient;
import hk.suitup.Service.QueryService;
import hk.tools.SwipeRefreshLoadLayout;

public class ShareFragment extends Fragment {

    private View mParentView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLoadLayout mSwipeRefreshLoadLayout;

    private String[] arrays;
    //private ArrayList<String> arrayList;
    private ListRecyclerViewAdapter listRecyclerViewAdapter;

    private List<SuitInfo> suits = new ArrayList<SuitInfo>();
    private Context context;
    private int page;
    private User user;

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.activity_share_fragment, container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.recycler_view);
        mSwipeRefreshLoadLayout = (SwipeRefreshLoadLayout) mParentView.findViewById(R.id.recycler_swipe);

        LinearLayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        page=0;
        QueryService queryService = new QueryService(0,5);

        String length = queryService.formLength();
        SSLClient.sendMessage(length + "\r\n");
        SSLClient.sendMessage(queryService.getTemp());
        SSLClient.getMessageLength();
        String response = SSLClient.getResponse();

        suits = queryService.getFiveSuit(response);


        //for demo
        //suits = queryService.getFiveSuitDemo(page);

        arrays=getResources().getStringArray(R.array.test_demo);

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

                int lastFlag = suits.size();
                page++;
                QueryService queryService = new QueryService(page,5);

                String length = queryService.formLength();
                SSLClient.sendMessage(length + "\r\n");
                SSLClient.sendMessage(queryService.getTemp());
                SSLClient.getMessageLength();
                String response = SSLClient.getResponse();

                List<SuitInfo> temp = queryService.getFiveSuit(response);
                 /*for demo
                 List<SuitInfo> temp = queryService.getFiveSuitDemo(page);
                 */

                for (int i=0;i<5;i++){
                    suits.add(temp.get(i));
                }

                listRecyclerViewAdapter.notifyDataSetChanged();
                mSwipeRefreshLoadLayout.setLoadMore(false);
            }
        }, 1000);
    }

    private class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>
    {
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
        public void onBindViewHolder(ViewHolder holder, int position){
           //for demo
           holder.addDetails(position);
           // holder.addDetailsDemo(position);
        }

        @Override
        public int getItemCount(){
            //return arrayList.size();
            return suits.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView mTextView;

            public ImageView mImageView;

            public ViewHolder( View v )
            {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.name);
                mImageView = (ImageView) v.findViewById(R.id.pic);
            }

            //for demo
            public void addDetailsDemo(final int position){
                mTextView.setText(suits.get(position).getName());
                mImageView.setImageDrawable(getResources().getDrawable(R.drawable.clothes3));
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("suit", suits.get(position));
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        //mContext.startActivity(new Intent(mContext, SubActivity.class));
                    }
                });
            }

            public void addDetails(final int position){
                //mTextView.setText(arrayList.get(position));

                mTextView.setText(suits.get(position).getName());

                byte[] pic= new byte[0];
                try {
                    pic = suits.get(position).getPic_name().getBytes("ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);

                //mImageView.setImageDrawable(mContext.getDrawable(R.drawable.clothes3));
                mImageView.setImageBitmap(bitmap);
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        Bundle mBundle = new Bundle();
                        user = (User) getActivity().getIntent().getSerializableExtra("user");
                        mBundle.putSerializable("suit",suits.get(position));
                        mBundle.putSerializable("user",user);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        //mContext.startActivity(new Intent(mContext, SubActivity.class));
                    }
                });
            }

        }
    }
}

