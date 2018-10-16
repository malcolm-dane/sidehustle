package com.parse.starter.MVC;

import android.location.Location;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by temp on 3/5/2017.
 */

public class helperMethods {

    public static  void updateLocation(final Location userLocation, final String UserType){
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");

    query.whereEqualTo(UserType,ParseUser.getCurrentUser().getUsername());

    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {

            if (e == null) {

                if (objects.size() > 0) {

                    for (ParseObject object : objects) {

                        object.put(UserType, userLocation);
                        object.saveInBackground();

                    }


                }

            }

        }
    });


}}
