package com.parse.starter.MVC;

import android.location.Location;
import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.models.aRequest;
import com.parse.starter.models.vrData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class staticUtils {
    private static ArrayList<vrData> aList;
    private static HashMap aMap;

    public static ArrayList<vrData> getRequests(final Location location) {
        aList = new ArrayList();
        ParseQuery query = new ParseQuery("aRequest");
        final ParseGeoPoint userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        query.whereNotEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                vrData map;
                if (e == null) {
                    for (ParseObject object : objects) {
                        ParseFile image = (ParseFile) object.get("aImage");
                        map = new vrData();
                        map.setusername(object.get(aRequest.USER_ID_KEY).toString());
                        map.setusername((String) object.get(aRequest.USER_ID_KEY));
                        map.setMessage((String) object.get("aEmail"));
                        map.setOther(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLatitude()));
                        map.setOtherLong(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLongitude()));
                        map.setmyLat(Double.valueOf(location.getLatitude()));
                        map.setMyLong(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLongitude()));
                        map.setType(object.getString("type"));
                        Double ParseGeo = Double.valueOf(((double) Math.round(Double.valueOf(userLocation.distanceInMilesTo((ParseGeoPoint) object.get(aRequest.requesterLocation))).doubleValue() * 10.0d)) / 10.0d);
                        Double.toString(ParseGeo.doubleValue());
                        map.setParseGeo(Double.toString(ParseGeo.doubleValue()));
                        if (object.getParseFile("aImage") != null) {
                            map.setImageUrl(object.getParseFile("aImage").getUrl());
                        }
                        map.setTimeStamp((String) object.get("_id"));
                        map.setobjID(object.getObjectId().toString());
                        Log.i("i", "here");
                        staticUtils.aList.add(map);
                    }
                    return;
                }
                map = new vrData();
                map.setMessage("nothing here");
                staticUtils.aList.add(map);
            }
        });
        return aList;
    }
}
