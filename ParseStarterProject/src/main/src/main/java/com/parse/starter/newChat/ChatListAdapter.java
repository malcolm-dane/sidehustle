package com.parse.starter.newChat;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.starter.mUtil.ImageLoader;
import com.parse.starter.models.Message;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Message> {
    private String receiver;
    private String mUserId;
    List<String>urlList;
    List<String>urlList2;
    ImageLoader imageLoader;
    public ChatListAdapter(Context context, String sender, String receiver, List<Message> messages,List<String>urlList,List<String>urlList2) {
       super(context,0,messages);
        this.mUserId =sender;
    this.receiver=receiver;
        this.urlList=urlList;
        this.urlList2=urlList;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageOther = (ImageView)convertView.findViewById(R.id.ivOther);
            holder.imageMe = (ImageView)convertView.findViewById(R.id.ivMe);
            holder.body = (TextView)convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);
        }
        final Message message = getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserId);
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.imageMe.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            ;
            } else {
            holder.imageOther.setVisibility(View.VISIBLE);
            holder.imageMe.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        }
        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;
       // message.setURL(urlList.get(0));
        //imageLoader.clearCache();
        final String a=urlList.get(0);
        final String B=urlList.get(1);
        imageLoader.DisplayImage(a,holder.imageMe);
        imageLoader.DisplayImage(B,holder.imageOther);


        holder.body.setText(message.getBody());
        return convertView;
    }

    // Create a gravatar image based on the hash value obtained fromsender
    private  String getProfileUrl(final String sender) {

        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(sender.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    final class ViewHolder {
        public ImageView imageOther;
        public ImageView imageMe;
        public TextView body;
    }
}