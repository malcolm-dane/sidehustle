package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.location.LocationListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
;

/**
 * Created by temp on 3/19/2017.
 */

public class testing implements Serializable,LocationListener{
        String other;
        String me;
        public static void check(final String other, final ArrayList a,final String me)
        {

            // Construct query to execute
            ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
            // Configure limit and sort order
            //  query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
            query.orderByAscending("createdAt");
            // Execute query to fetch all messages from Parse asynchronously
            // This is equivalent to a SELECT query with SQL
            query.findInBackground(new FindCallback<Message>() {
                public void done(List<Message> messages, ParseException e) {
                    if (e == null) {
                        a.clear();
                        for (Message message : messages) {

                            if (me.equals(message.getreceiverId()) && (message.getUserId().equals(other))) {
                                a.add(message);
                            } else if (other.equals(message.getString("receiver")) && (me.equals(message.getUserId()))) {
                                a.add(message);
                            }


                    } }}

             });
            Intent I= new Intent();
I.putExtra("arry",a);
              };

    public static void grabAllUsers(final ArrayList a,final  String V, final ArrayAdapter c){
        ParseQuery<ParseUser> query = ParseUser.getQuery();

     //

        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    Log.i("asdf", ParseUser.getCurrentUser().getUsername());
                    if (objects.size() > 0) {

                        a.clear();

                        for (ParseUser user : objects) {

                           a.add(user.getUsername());Log.i("user",a.toString());
                        }c.notifyDataSetChanged();}}}});}


    public static void grabUserNames(final ArrayList a, final String me, final ArrayAdapter c)
    {

        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        //  query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    a.clear();
                    for (Message message : messages) {

                        if (me.equals(message.getreceiverId())) {
                            a.add(message.getUserId());
                        } else if (me.equals(message.getUserId())) {
                            a.add(message.getreceiverId());
                        }


                        Set<Message> hashset = new HashSet<>();
                        hashset.addAll(a);
                        a.clear();
                        a.addAll(hashset); c.notifyDataSetChanged();}  }}

        });
        Intent I= new Intent();
        I.putExtra("arry",a);
    };
   public static void grabAndConvert(ParseFile B,ArrayList a){
       byte[] byteArray = new byte[0];
       try {
           byteArray = B.getData();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
     a.add(bitmap);
       } catch (ParseException e1) {
           e1.printStackTrace();
       }

   }

    public static void  grabLocation(LocationManager locationManager,Location location,String Provider,ParseGeoPoint a)
    {

       // location = locationManager.getLastKnownLocation(provider);
    a=new ParseGeoPoint(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}