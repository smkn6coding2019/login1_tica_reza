package com.example.gitasoft.login1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataProduct> RumahItems;
    ImageLoader imageLoader;

    public ProductAdapter(Activity activity, List<DataProduct> rumahItems) {
        this.activity = activity;
        this.RumahItems = rumahItems;
    }

    @Override
    public int getCount() {
        return RumahItems.size();
    }

    @Override
    public Object getItem(int i) {
        return RumahItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.list_row_rumah, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) view.findViewById(R.id.foto_rumah);
        TextView judul = (TextView) view.findViewById(R.id.title_rumah);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        TextView harga = (TextView) view.findViewById(R.id.harga);

        DataProduct Rumah = RumahItems.get(i);

        thumbNail.setImageUrl(Rumah.getImage(), imageLoader);
        judul.setText(Rumah.getTitle());
        desc.setText(Rumah.getShortdesc());
        harga.setText(Rumah.getPrice());

        return view;
    }
}