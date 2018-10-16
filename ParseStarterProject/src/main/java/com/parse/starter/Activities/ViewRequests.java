package com.parse.starter.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.cast.TextTrackStyle;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.adapters.requestAdapter;
import com.parse.starter.finalizeIt;
import com.parse.starter.models.Bids;
import com.parse.starter.models.aRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ru.katso.livebutton.LiveButton;
import com.parse.starter.R;

public class ViewRequests extends AppCompatActivity implements LocationListener {
    static final String TAG = ViewRequests.class.getSimpleName();
    String MessageFromUser;
    boolean ViewingOwnREquest;
    Bitmap aBit;
    requestAdapter arrayAdapter;
    int c;
    private boolean flagOff;
    ArrayList<Bitmap> fromParse;
    ArrayList<Double> latitudes;
    ListView listView;
    ArrayList<String> listViewContent123;
    Location location;
    LocationManager locationManager;
    ArrayList<Double> longitudes;
    ArrayList<String> message;
    ArrayList<String> mytype;
    ArrayList<String> objID;
    ArrayList<String> objurl;
    String provider;
    ArrayList<String> type;
    ArrayList<String> usernames;
    ArrayList<String> usersRequests;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);
        this.flagOff = false;
        this.ViewingOwnREquest = false;
        LiveButton mRefreshB = (LiveButton) findViewById(R.id.Refresh);
        LiveButton mMyReqests = (LiveButton) findViewById(R.id.MyRequests);
        this.usersRequests = new ArrayList();
        this.listView = (ListView) findViewById(R.id.listView123);
        this.listViewContent123 = new ArrayList();
        this.objID = new ArrayList();
        this.objurl = new ArrayList();
        this.usernames = new ArrayList();
        this.latitudes = new ArrayList();
        this.longitudes = new ArrayList();
        this.message = new ArrayList();
        this.type = new ArrayList();
        this.mytype = new ArrayList();
        this.fromParse = new ArrayList();
        this.listViewContent123.add("Finding nearby requesters");
        this.arrayAdapter = new requestAdapter(this, this.listViewContent123, this.fromParse, this.objurl);
        this.listView.setAdapter(this.arrayAdapter);
        mRefreshB.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ViewRequests.this.updateLocation();
                ViewRequests.this.arrayAdapter.notifyDataSetChanged();
            }
        });
        mMyReqests.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ViewRequests.this.ViewingOwnREquest = true;
                ViewRequests.this.listViewContent123.clear();
                ViewRequests.this.arrayAdapter.notifyDataSetChanged();
                ViewRequests.this.UpdateIt();
            }
        });
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (!ViewRequests.this.ViewingOwnREquest) {
                    Intent i = new Intent(ViewRequests.this, ViewRiderLocation.class);
                    i.putExtra("username", ViewRequests.this.usernames.get(position));
                    i.putExtra("MessageFromUser", ViewRequests.this.message.get(position));
                    i.putExtra("latitude", ViewRequests.this.latitudes.get(position));
                    i.putExtra("longitude", ViewRequests.this.longitudes.get(position));
                    i.putExtra("userLatitude", ViewRequests.this.location.getLatitude());
                    i.putExtra("userLongitude", ViewRequests.this.location.getLongitude());
                    i.putExtra("objID", ViewRequests.this.objID.get(position));
                    i.putExtra("type", ViewRequests.this.type.get(position));
                    ViewRequests.this.startActivity(i);
                }
                if (ViewRequests.this.ViewingOwnREquest) {
                    Intent i = new Intent(ViewRequests.this, finalizeIt.class);
                    i.putExtra("username", ViewRequests.this.usernames.get(position));
                    i.putExtra("objID", ViewRequests.this.mytype.get(position));
                    i.putExtra("type", ViewRequests.this.type.get(position));
                    i.putExtra("bid", ViewRequests.this.message.get(position));
                    i.putExtra("theirOBJ", ViewRequests.this.objID.get(position));
                    ViewRequests.this.startActivity(i);
                }
            }
        });
        updateReq();
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        this.provider = this.locationManager.getBestProvider(new Criteria(), false);
        this.locationManager.requestLocationUpdates(this.provider, 400, TextTrackStyle.DEFAULT_FONT_SCALE, this);
        this.location = this.locationManager.getLastKnownLocation(this.provider);
        if (this.location != null) {
            updateLocation();
            this.arrayAdapter.notifyDataSetChanged();
        }
    }

    public void updateReq() {
        this.usersRequests.clear();
        this.mytype.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ViewRequests.this.flagOff = true;
                    Log.i("notnull", "Updateit");
                    for (ParseObject object : objects) {
                        ViewRequests.this.usersRequests.add(object.getObjectId());
                        ViewRequests.this.mytype.add(object.getString("type"));
                        object.getObjectId();
                    }
                }
            }
        });
    }

    public void UpdateIt() {
        this.message.clear();
        this.objID.clear();
        this.type.clear();
        this.mytype.clear();
        for (int i = 0; i < this.usersRequests.size(); i++) {
            this.c = i;
            ParseQuery<ParseObject> OQuery = ParseQuery.getQuery("Bids");
            OQuery.whereEqualTo(ParseUser.getCurrentUser().getUsername(), ParseUser.getCurrentUser().getUsername());
            OQuery.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        Log.i("nullAlert", "1done");
                        for (ParseObject object : objects) {
                            Log.i("if", "done");
                            ViewRequests.this.type.add(object.getString("type"));
                            ViewRequests.this.message.add(object.getString(Bids.Bid_KEY));
                            ViewRequests.this.usernames.add(object.getString("sender"));
                            ViewRequests.this.objID.add(object.getObjectId());
                            ViewRequests.this.mytype.add(object.getString("objId)_ "));
                            ViewRequests.this.listViewContent123.add("Your Request: " + object.getString("type") + "You have Bids: " + object.get(Bids.Bid_KEY) + " from user " + object.getString("sender"));
                            ViewRequests.this.arrayAdapter.notifyDataSetChanged();
                        }
                        return;
                    }
                    Log.i("elseAlert", "done");
                }
            });
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ViewRequests.this.flagOff = true;
                    Log.i("notnull", "Updateit");
                    for (ParseObject object : objects) {
                        if (object.getParseFile("aImage") != null && object.getString("hasBid") == null) {
                            ViewRequests.this.listViewContent123.add("Your Request: " + object.getString("type") + " has no Bids");
                            ViewRequests.this.arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    public void updateLocation() {
        final ParseGeoPoint userLocation = new ParseGeoPoint(this.location.getLatitude(), this.location.getLongitude());
        ParseUser.getCurrentUser().put("location", userLocation);
        ParseUser.getCurrentUser().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo("RequestOpen", "Open_REquest");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("MyApp", objects.toString());
                    if (objects.size() > 0) {
                        ViewRequests.this.listViewContent123.clear();
                        ViewRequests.this.usernames.clear();
                        ViewRequests.this.latitudes.clear();
                        ViewRequests.this.longitudes.clear();
                        ViewRequests.this.message.clear();
                        ViewRequests.this.objID.clear();
                        ViewRequests.this.objurl.clear();
                        ViewRequests.this.type.clear();
                        ViewRequests.this.fromParse.clear();
                        for (ParseObject object : objects) {
                            Double distanceOneDP;
                            if (!(object.get(aRequest.driverUsernameKey) != null || object.get(aRequest.USER_ID_KEY) == null || object.get(aRequest.USER_ID_KEY).toString().equals(ParseUser.getCurrentUser().getUsername()))) {
                                distanceOneDP = Double.valueOf(((double) Math.round(Double.valueOf(userLocation.distanceInMilesTo((ParseGeoPoint) object.get(aRequest.requesterLocation))).doubleValue() * 10.0d)) / 10.0d);
                                ViewRequests.this.MessageFromUser = object.getString("aEmail");
                                ViewRequests.this.message.add(ViewRequests.this.MessageFromUser);
                                ViewRequests.this.listViewContent123.add(distanceOneDP.toString() + " miles Username is:" + object.getString(aRequest.USER_ID_KEY) + "Type of Request is" + object.getString("type") + "Request Details are" + object.getString("aEmail") + "Status:You haven't answered");
                                ViewRequests.this.type.add(object.getString("type"));
                                ViewRequests.this.objID.add(object.getObjectId());
                                ViewRequests.this.usernames.add(object.getString(aRequest.USER_ID_KEY));
                                ViewRequests.this.latitudes.add(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLatitude()));
                                ViewRequests.this.longitudes.add(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLongitude()));
                            }
                            if (object.get(aRequest.driverUsernameKey) != null && object.get(aRequest.driverUsernameKey).equals(ParseUser.getCurrentUser().getUsername())) {
                                if (object.getParseFile("aImage") == null) {
                                    ViewRequests.this.objurl.add("http://mdane-devportfolio.com/images1/PicNoteScreencap.png");
                                } else {
                                    ViewRequests.this.objurl.add(object.getParseFile("aImage").getUrl());
                                }
                                Log.i("MyApp", object.toString());
                                ViewRequests.this.type.add(object.getString("requestType"));
                                distanceOneDP = Double.valueOf(((double) Math.round(Double.valueOf(userLocation.distanceInMilesTo((ParseGeoPoint) object.get(aRequest.requesterLocation))).doubleValue() * 10.0d)) / 10.0d);
                                ViewRequests.this.MessageFromUser = object.getString("aEmail");
                                ViewRequests.this.message.add(ViewRequests.this.MessageFromUser);
                                ViewRequests.this.listViewContent123.add(distanceOneDP.toString() + " miles Username is:" + object.getString(aRequest.USER_ID_KEY) + object.getString("aEmail") + "Status: You answered");
                                ViewRequests.this.usernames.add(object.getString(aRequest.USER_ID_KEY));
                                ViewRequests.this.longitudes.add(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLongitude()));
                            }
                        }
                        return;
                    }
                    Toast.makeText(ViewRequests.this.getApplicationContext(), "Something Happened. Is your connection spooty? Hit Refresh", 1).show();
                }
            }
        });
        this.arrayAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_requests, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
    }

    public void onLocationChanged(Location location) {
        updateLocation();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
