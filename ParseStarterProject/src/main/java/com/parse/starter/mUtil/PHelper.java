package com.parse.starter.mUtil;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.models.aRequest;
import java.util.List;

public class PHelper {
    public static ParseGeoPoint B;
    public static String ChatUserName;
    String aResponse;
    String mfromDriver;
    String name;

    public void userPut(String a, String B) {
        ParseUser.getCurrentUser().put(a, B);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
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
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseUser driver : objects) {
                        PHelper.B = driver.getParseGeoPoint("location");
                        PHelper.this.setGeo(PHelper.B);
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
        return B;
    }

    public void PutNQuery(final ParseGeoPoint mUser) {
        ParseQuery<ParseObject> query = new ParseQuery("Requests");
        query.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        object.put(aRequest.requesterLocation, mUser);
                        object.saveInBackground();
                    }
                }
            }
        });
    }

    public void mPutGeo(String aUser, final String aUsersLocation, final ParseGeoPoint mUser) {
        ParseQuery<ParseObject> query = new ParseQuery("Requests");
        query.whereEqualTo(aUser, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        object.put(aUsersLocation, mUser);
                        object.saveInBackground();
                    }
                }
            }
        });
    }

    public static void CancelIt(String aUserName) {
        ParseQuery<ParseObject> query = new ParseQuery("Requests");
        query.whereEqualTo(aUserName, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        object.deleteInBackground();
                    }
                }
            }
        });
    }

    public static void putResponse(final String response, final String B, final String c) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
        query.whereEqualTo(aRequest.USER_ID_KEY, B);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("fuck", "fuck");
                    for (ParseObject object : objects) {
                        if (object.getString("Chat") == null) {
                            object.put("Chat", c);
                            object.put("AMessage", response);
                            Log.i("put message", "put message");
                        } else if (!(object.getString("Chat") == null || object.getString("Chat").equalsIgnoreCase(B))) {
                            new ParseObject("Requests").put("aEmail", response);
                            Log.i("responding", "response");
                        }
                    }
                }
            }
        });
    }

    public void getSponse(String B) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
        query.whereEqualTo("username", B);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        PHelper.this.aResponse = object.getString("mEmail");
                    }
                }
            }
        });
    }
}
