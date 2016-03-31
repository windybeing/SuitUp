package hk.suitup.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hk.suitup.R;

public class ProfileAdapter extends BaseAdapter {

    ProfileFragment context;
    List<ProfileFragment.Property> data;
    int layout;

    public ProfileAdapter(ProfileFragment context, List<ProfileFragment.Property> data,
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
        View v = LayoutInflater.from(context.getActivity()).inflate(layout, null);
        TextView name = (TextView) v.findViewById(R.id.property_name);
        final TextView value = (TextView) v.findViewById(R.id.property_value);
        name.setText(data.get(index).getName()+" : "+data.get(index).getValue());

        return v;
    }

}
