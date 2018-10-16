package com.parse.starter.adapters.singleItemView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.parse.starter.R;

import com.parse.starter.models.SPModel;
import java.util.ArrayList;

public class singleItemView extends BaseAdapter {
    private static ArrayList<SPModel> spList;
    private LayoutInflater mInflater;

    static class ViewHolder {
        TextView txtMessage;
        TextView txtName;
        TextView txtPhone;
        TextView txtstatus;

        ViewHolder() {
        }
    }

    public singleItemView(Context context, ArrayList<SPModel> aSP) {
        spList = aSP;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return spList.size();
    }

    public Object getItem(int position) {
        return spList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.customrow, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtstatus = (TextView) convertView.findViewById(R.id.status);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.phone);
            holder.txtMessage = (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtName.setText(spList.get(position).getName());
        holder.txtstatus.setText(spList.get(position).getStatus());
        holder.txtPhone.setText(spList.get(position).getPhone());
        holder.txtMessage.setText(spList.get(position).getMessage());
        return convertView;
    }
}
