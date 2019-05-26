package com.parse.starter.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.starter.Activities.ViewRiderLocation;
import com.parse.starter.R;
import com.parse.starter.SingleItemView;
import com.parse.starter.mUtil.ImageLoader;
import com.parse.starter.models.vrData;
import java.util.ArrayList;
import java.util.List;
import com.parse.starter.R;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<vrData> arraylist;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    String me;
    private List<vrData> openRequestslist = null;
    String them;

    public class ViewHolder {
        ImageView imageurl;
        TextView location;
        TextView message;
        TextView username;
    }

    public ListViewAdapter(Context context, List<vrData> openRequestslist) {
        this.context = context;
        this.openRequestslist = openRequestslist;
        this.inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList();
        this.arraylist.addAll(openRequestslist);
        this.imageLoader = new ImageLoader(context);
    }

    public int getCount() {
        return this.openRequestslist.size();
    }

    public Object getItem(int position) {
        return this.openRequestslist.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = this.inflater.inflate(R.layout.listview_item, null);
            holder.username = (TextView) view.findViewById(R.id.username);
            holder.message = (TextView) view.findViewById(R.id.message);
            holder.imageurl = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.username.setText("UserName is " + this.openRequestslist.get(position).getusername());
        holder.message.setText("Request " + this.openRequestslist.get(position).getMessage() + " Distance from you " + this.openRequestslist.get(position).getParseGeo());
        this.imageLoader.DisplayImage(this.openRequestslist.get(position).getImageUrl(), holder.imageurl);
        return view;
    }

    public void redirect(int position) {
        Intent intent = new Intent(this.context, ViewRiderLocation.class);
        intent.putExtra("rank", this.openRequestslist.get(position).getusername());
        intent.putExtra("country", this.openRequestslist.get(position).getMessage());
        intent.putExtra("timestamp", this.openRequestslist.get(position).getTimeStamp());
        intent.putExtra("flag", this.openRequestslist.get(position).getImageUrl());
        intent.putExtra("longitude", this.openRequestslist.get(position).getOtherLong());
        intent.putExtra("latitude", this.openRequestslist.get(position).getOtherLat());
        intent.putExtra("userLongitude", this.openRequestslist.get(position).getMyLong());
        intent.putExtra("userLatitude", this.openRequestslist.get(position).getMyLat());
        intent.putExtra("objID", this.openRequestslist.get(position).getobjID());
        intent.putExtra("username", this.openRequestslist.get(position).getusername());
        intent.putExtra("type", this.openRequestslist.get(position).getType());
        this.context.startActivity(intent);
    }

    public void notMeRedirect(int position) {
        Intent intent = new Intent(this.context, SingleItemView.class);
        intent.putExtra("objID", this.openRequestslist.get(position).getobjID());
        Log.i(this.openRequestslist.get(position).getobjID(), this.openRequestslist.get(position).getobjID());
        intent.putExtra("rank", this.openRequestslist.get(position).getusername());
        intent.putExtra("country", this.openRequestslist.get(position).getMessage());
        intent.putExtra("timestamp", this.openRequestslist.get(position).getTimeStamp());
        intent.putExtra("flag", this.openRequestslist.get(position).getImageUrl());
        intent.putExtra("longitude", this.openRequestslist.get(position).getOtherLong());
        intent.putExtra("latitude", this.openRequestslist.get(position).getOtherLat());
        intent.putExtra("userLongitude", this.openRequestslist.get(position).getMyLong());
        intent.putExtra("userLatitude", this.openRequestslist.get(position).getMyLat());
        intent.putExtra("username", this.openRequestslist.get(position).getusername());
        intent.putExtra("type", this.openRequestslist.get(position).getType());
        this.context.startActivity(intent);
    }
}
