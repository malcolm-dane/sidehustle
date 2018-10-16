package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.starter.mUtil.ImageLoader;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<vrData> arraylist;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    private List<vrData> openRequestslist = null;

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

    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = this.inflater.inflate(R.layout.listview_item, null);
            holder.username = (TextView) view.findViewById(R.id.username);
            holder.message = (TextView) view.findViewById(R.id.message);
            holder.imageurl = (ImageView) view.findViewById(2131624036);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.username.setText("UserName is " + this.openRequestslist.get(position).getusername());
        holder.message.setText("Request " + this.openRequestslist.get(position).getMessage() + " Distance from you " + this.openRequestslist.get(position).getParseGeo());
        this.imageLoader.DisplayImage(this.openRequestslist.get(position).getImageUrl(), holder.imageurl);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(ListViewAdapter.this.context, SingleItemView.class);
                intent.putExtra("rank", ListViewAdapter.this.openRequestslist.get(position).getusername());
                intent.putExtra("country", ListViewAdapter.this.openRequestslist.get(position).getMessage());
                intent.putExtra("flag", ListViewAdapter.this.openRequestslist.get(position).getImageUrl());
                ListViewAdapter.this.context.startActivity(intent);
            }
        });
        return view;
    }
}
