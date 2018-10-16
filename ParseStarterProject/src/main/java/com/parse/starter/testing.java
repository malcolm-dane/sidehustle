package com.parse.starter;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.models.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class testing implements Serializable, LocationListener {
    String me;
    String other;

    public static void check(final String other, final ArrayList a, final String me) {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    a.clear();
                    for (Message message : messages) {
                        if (me.equals(message.getreceiverId()) && message.getUserId().equals(other)) {
                            a.add(message);
                        } else if (other.equals(message.getString("receiver")) && me.equals(message.getUserId())) {
                            a.add(message);
                        }
                    }
                }
            }
        });
        new Intent().putExtra("arry", a);
    }

    public static void grabAllUsers(final ArrayList a, String V, final ArrayAdapter c) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    Log.i("asdf", ParseUser.getCurrentUser().getUsername());
                    if (objects.size() > 0) {
                        a.clear();
                        for (ParseUser user : objects) {
                            a.add(user.getUsername());
                            Log.i("user", a.toString());
                        }
                        c.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public static void grabUserNames(final ArrayList a, final String me, final ArrayAdapter c) {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.orderByAscending("createdAt");
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
                        Set<Message> hashset = new HashSet();
                        hashset.addAll(a);
                        a.clear();
                        a.addAll(hashset);
                        c.notifyDataSetChanged();
                    }
                }
            }
        });
        new Intent().putExtra("arry", a);
    }

    public static void grabAndConvert(ParseFile B, ArrayList a) {
        byte[] bArr = new byte[0];
        try {
            bArr = B.getData();
            a.add(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public static void grabLocation(LocationManager locationManager, Location location, String Provider, ParseGeoPoint a) {
        a = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
    }

    public void onLocationChanged(Location location) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
