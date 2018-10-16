package com.parse.starter.MVC;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.models.Bids;
import com.parse.starter.models.vrData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class helperMethods {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 60000;
    static ArrayList<vrData> aList;
    static String aOBJID;
    private static boolean close;
    private static Location location;
    static Double myLong;
    static Double mylat;
    private static Double thedistance;

    private static class LocationExtender implements LocationListener {
        private LocationExtender() {
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

    public static void getBid(final String objID, final ArrayList a) {
        ParseQuery<ParseObject> query = new ParseQuery("Bids");
        query.whereNotEqualTo(ParseUser.getCurrentUser().toString(), ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    HashMap aMap = new HashMap();
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            if (object.get("objID").equals(objID)) {
                                aMap.put("bidValue", object.getString(Bids.Bid_KEY));
                                aMap.put("sender", object.getString("sender"));
                                a.add(aMap);
                            }
                        }
                    }
                }
            }
        });
    }

    public static Double nearPhilly(Context m) {
        LocationManager locationManager = (LocationManager) m.getSystemService("location");
        locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 10.0f, new LocationExtender() {
        });
        Log.d("GPS Enabled", "GPS Enabled");
        if (locationManager != null) {
            location = locationManager.getLastKnownLocation("gps");
            if (location != null) {
                mylat = Double.valueOf(location.getLatitude());
                myLong = Double.valueOf(location.getLongitude());
            }
            thedistance = calculateDistance(mylat.doubleValue(), myLong.doubleValue(), Double.valueOf(39.9526d).doubleValue(), Double.valueOf(-75.1652d).doubleValue());
        }
        return thedistance;
    }

    public static Double calculateDistance(double latitudeA, double longitudeA, double latitudeB, double longitudeB) {
        Double distance = Double.valueOf((((double) 20924640) * Math.acos(((Math.cos(Math.toRadians(latitudeA)) * Math.cos(Math.toRadians(latitudeB))) * Math.cos(Math.toRadians(longitudeB) - Math.toRadians(longitudeA))) + (Math.sin(Math.toRadians(latitudeA)) * Math.sin(Math.toRadians(latitudeB))))) / 5000.0d);
        Log.i("Double", Double.toString(distance.doubleValue()));
        return distance;
    }
}
