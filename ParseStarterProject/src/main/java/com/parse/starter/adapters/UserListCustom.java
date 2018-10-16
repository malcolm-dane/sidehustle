package com.parse.starter.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.starter.R;

import java.util.ArrayList;

public class UserListCustom extends ArrayAdapter {
    private final Activity context;
    private final Integer[] imageId;
    private final ArrayList web;

    public UserListCustom(Activity context, ArrayList<String> web, Integer[] imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(R.layout.list_single, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        ((TextView) rowView.findViewById(R.id.txt)).setText(this.web.get(position).toString());
        imageView.setImageResource(this.imageId[0].intValue());
        return rowView;
    }
}
