package com.app.mobilityapp.adapter;

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

import java.util.ArrayList;

/**
 * Created by Akshay Raj on 06/02/18.
 * akshay@snowcorp.org
 * www.snowcorp.org
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList arrayList;

    public MyAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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

        if (arrayList.get(position) instanceof Uri)
            imagePath.setText(FileUtils.getPath(context, (Uri) arrayList.get(position)));
        else
            imagePath.setText(""+arrayList.get(position));

        Glide.with(context)
                .load(arrayList.get(position))
                .into(imageView);

        return convertView;
    }
}
