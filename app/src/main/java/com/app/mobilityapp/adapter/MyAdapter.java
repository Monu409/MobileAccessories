package com.app.mobilityapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by Akshay Raj on 06/02/18.
 * akshay@snowcorp.org
 * www.snowcorp.org
 */

public class MyAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList arrayList;

    public MyAdapter(Activity context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void Update(ArrayList arrayList) {
        this.arrayList = arrayList;
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (mInflater != null) {
            convertView = mInflater.inflate(R.layout.list_items, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView imagePath = convertView.findViewById(R.id.imagePath);
        ImageView crossView = convertView.findViewById(R.id.cross_img);
        crossView.setOnClickListener(v -> {
            arrayList.remove(position);
            notifyDataSetChanged();
        });

        if (arrayList.get(position) instanceof Uri)
            imagePath.setText(FileUtils.getPath(context, (Uri) arrayList.get(position)));
        else
            imagePath.setText("" + arrayList.get(position));

        Glide.with(context)
                .asBitmap()
                .load(arrayList.get(position))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true))
                .into(imageView);

        return convertView;
    }
}
