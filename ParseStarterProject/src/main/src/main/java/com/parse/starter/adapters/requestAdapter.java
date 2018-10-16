package com.parse.starter.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

public class requestAdapter extends ArrayAdapter {

    private final Activity context;
    private final ArrayList<String> web;
    private final ArrayList<String> weburl;
   private final ArrayList<Bitmap> imageId;
   // private final ArrayList<Bitmap> imageId;
   String a;
    public requestAdapter(Activity context,
                          ArrayList<String> web,ArrayList<Bitmap> imageId,ArrayList<String> weburl) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.weburl = weburl;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.requestlist, parent, false);
            final requestAdapter.ViewHolder holder = new requestAdapter.ViewHolder();
            holder.imageO = (ImageView)convertView.findViewById(R.id.btmap);
            holder.body = (TextView)convertView.findViewById(R.id.txhint);
            convertView.setTag(holder);
        }        final ViewHolder holder = (ViewHolder)convertView.getTag();
       for(int i=0;i<weburl.size();i++){

        if(weburl.listIterator().hasNext()){
      a=weburl.listIterator().next();
          //icasso.with(getContext()).load(weburl.listIterator().next()).into(imageO);
    } }
        final ImageView profileView =  holder.imageO;
           Picasso.with(getContext()).load(a).into(profileView);
           holder.body.setText(web.get(position).toString());
           return convertView;





}
    final class ViewHolder {
        public ImageView imageO;
        public TextView body;
    }}


