package com.parse.starter.newChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.starter.R;
import com.parse.starter.mUtil.ImageLoader;
import com.parse.starter.models.Message;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Message> {
    ImageLoader imageLoader;
    private String mUserId;
    private String receiver;
    List<String> urlList;
    List<String> urlList2;

    final class ViewHolder {
        public TextView body;
        public ImageView imageMe;
        public ImageView imageOther;

        ViewHolder() {
        }
    }

    public ChatListAdapter(Context context, String sender, String receiver, List<Message> messages, List<String> urlList, List<String> list) {
        super(context, 0, messages);
        this.mUserId = sender;
        this.receiver = receiver;
        this.urlList = urlList;
        this.urlList2 = urlList;
        this.imageLoader = new ImageLoader(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
            holder = new ViewHolder();
            holder.imageOther = (ImageView) convertView.findViewById(R.id.ivOther);
            holder.imageMe = (ImageView) convertView.findViewById(R.id.ivMe);
            holder.body = (TextView) convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);
        }
        Message message = getItem(position);
        holder = (ViewHolder) convertView.getTag();
        boolean isMe = message.getUserId().equals(this.mUserId);
        if (isMe) {
            holder.imageMe.setVisibility(0);
            holder.imageOther.setVisibility(8);
            holder.body.setGravity(21);
        } else {
            holder.imageOther.setVisibility(0);
            holder.imageMe.setVisibility(8);
            holder.body.setGravity(19);
        }
        ImageView profileView;
        if (isMe) {
            profileView = holder.imageMe;
        } else {
            profileView = holder.imageOther;
        }
        String B = this.urlList.get(1);
        this.imageLoader.DisplayImage(this.urlList.get(0), holder.imageMe);
        this.imageLoader.DisplayImage(B, holder.imageOther);
        holder.body.setText(message.getBody());
        return convertView;
    }

    private String getProfileUrl(String sender) {
        String hex = "";
        try {
            hex = new BigInteger(MessageDigest.getInstance("MD5").digest(sender.getBytes())).abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }
}
