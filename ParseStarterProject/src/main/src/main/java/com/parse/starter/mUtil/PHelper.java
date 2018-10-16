package com.parse.starter.mUtil;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

//import static com.parse.starter.R.id.driver;
/*Rough draft of conveince methods to test and be used for refactoring to be more inline with what I would like the code to be.
Interchangeable methods to be used by any user to Query and Get. An interface would probably be the better solution
 but in java only void and blank methods can be used in the interfaces. So only the put methods would really make sense in an interface
*\
 */
/**
 * Created by IhateSudo on 1/14/17.
 */

public class PHelper {
    String mfromDriver;
    String name;
 String aResponse;
    public static String ChatUserName;
    public static ParseGeoPoint B;

    public void userPut(String a, String B) {
        ParseUser.getCurrentUser().put(a, B);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Myapp", "test");
                }


            }
        });
    }


    public void mQuery(String a) {
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("username", a);
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseUser driver : objects) {

                            B = driver.getParseGeoPoint("location");
                            setGeo(B);
                        }

                    }

                }
            }
        });

        Log.i("C assigned", "ass");
    }

    public void setGeo(ParseGeoPoint C) {
        B = C;
    }

    public ParseGeoPoint getGeo(String driverUsername) {
        mQuery(driverUsername);
        ParseGeoPoint C = B;
        return C;
    }

    public void PutNQuery(final ParseGeoPoint mUser) {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");
        query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            object.put("requesterLocation", mUser);
                            object.saveInBackground();

                        }
                    }

                }

            }
        });
    }

    public void mPutGeo(String aUser, final String aUsersLocation, final ParseGeoPoint mUser) {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");
        query.whereEqualTo(aUser, ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            object.put(aUsersLocation, mUser);
                            object.saveInBackground();

                        }
                    }

                }

            }
        });
    }

    public static void CancelIt(String aUserName) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");
        query.whereEqualTo(aUserName, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            object.deleteInBackground();

                        }


                    }

                }

            }
        });
    }

   public static void putResponse(final String response, final String B,final String c) {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
    query.whereEqualTo("requesterUsername", B);
    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {

            if (e == null) {

                Log.i("fuck","fuck");
                    for (ParseObject object : objects) {
if (object.getString("Chat")==null){
    object.put("Chat",c);
    object.put("AMessage",response);
    Log.i("put message","put message");
}


else{
                        if(object.getString("Chat")!=null&&!object.getString("Chat").equalsIgnoreCase(B)){
                            ParseObject chat= new ParseObject("Requests");
                            chat.put("aEmail",response);
                        Log.i("responding","response");
                        }
                        }}


}}});}
    public void getSponse(final String B){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
    query.whereEqualTo("username", B);
    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {

            if (e == null) {

                for (ParseObject object : objects) {
                            aResponse = object.getString("mEmail");
                    }
                }}});

}

    }

