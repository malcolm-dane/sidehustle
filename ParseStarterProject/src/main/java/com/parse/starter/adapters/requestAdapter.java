package com.parse.starter.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.starter.R;
import com.parse.starter.mUtil.ImageLoader;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class requestAdapter extends ArrayAdapter {
    String a;
    private final Activity context;
    private final ArrayList<Bitmap> imageId;
    ImageLoader imageLoader;
    private final ArrayList<String> web;
    private final ArrayList<String> weburl;

    final class ViewHolder {
        public TextView body;
        public ImageView imageO;

        ViewHolder() {
        }
    }

    public requestAdapter(Activity context, ArrayList<String> web, ArrayList<Bitmap> imageId, ArrayList<String> weburl) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.weburl = weburl;
        this.imageLoader = new ImageLoader(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.requestlist, parent, false);
            holder = new ViewHolder();
            holder.imageO = (ImageView) convertView.findViewById(R.id.btmap);
            holder.body = (TextView) convertView.findViewById(R.id.txhint);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        for (int i = 0; i < this.weburl.size(); i++) {
            if (this.weburl.listIterator().hasNext()) {
                this.a = this.weburl.listIterator().next();
            }
        }
        Picasso.with(getContext()).load(this.a).into(holder.imageO);
        holder.body.setText(this.web.get(position).toString());
        return convertView;
    }
}
