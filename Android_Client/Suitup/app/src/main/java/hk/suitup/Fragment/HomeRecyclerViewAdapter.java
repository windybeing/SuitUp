package hk.suitup.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hk.suitup.R;
import hk.suitup.Activity.SubActivity;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private String[] names={"Demo1","Demo2","Demo3"};

    private Context mContext;

    public HomeRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTextView.setText(names[position]);

        //set drawable
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        //Bitmap picture = BitmapFactory.decodeFile(items.get(position).getPicturePaths().get(0), options);
        Bitmap picture=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/clothes"+String.valueOf((position)%3+1)+".jpg"));

        //holder.mImageView.setImageDrawable(mContext.getDrawable(R.drawable.clothes3));
        holder.mImageView.setImageBitmap(picture);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SubActivity.class));
            }
        });
    }

    @Override
    public int getItemCount(){
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public ImageView mImageView;

        public ViewHolder( View v )
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
            mImageView = (ImageView) v.findViewById(R.id.pic);
        }
    }
}

