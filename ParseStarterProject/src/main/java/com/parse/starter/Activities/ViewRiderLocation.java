package com.parse.starter.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.models.Bids;
import com.parse.starter.models.aRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.parse.starter.R;

public class ViewRiderLocation extends FragmentActivity {
    Map aMap;
    Map<String, String> aMap1;
    Map<Map, Map> aMap2;
    Map<String, Map> aMap3;
    ParseObject bid;
    Button button;
    String editString = "";
    String fromUser;
    Intent i;
    Intent j;
    private AdView mAdView;
    EditText mEditText;
    private GoogleMap mMap;
    RelativeLayout mapLayout;
    ParseObject theObject;
    ArrayList<String> user;

    public void back(View view) {
        startActivity(new Intent(this, ViewRequests.class));
    }

    public void getFromUser() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo(aRequest.USER_ID_KEY, this.i.getStringExtra("username"));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        ViewRiderLocation.this.fromUser = object.getString("aEmail");
                    }
                }
            }
        });
    }

    public void acceptRequest(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo(aRequest.USER_ID_KEY, this.i.getStringExtra("username"));
        this.mEditText = (EditText) findViewById(R.id.messageFromDriver);
        this.editString = this.mEditText.getText().toString();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    String driversUsername = ParseUser.getCurrentUser().getUsername();
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            if (object.getObjectId().equals(ViewRiderLocation.this.i.getStringExtra("objID"))) {
                                Log.i("null", "you'regood");
                                object.put("hasBid1", "yes");
                                object.saveInBackground();
                            }
                            ParseObject bid = ParseObject.create(Bids.class);
                            bid.put(ViewRiderLocation.this.i.getStringExtra("username"), ViewRiderLocation.this.i.getStringExtra("username"));
                            bid.put("sender", ParseUser.getCurrentUser().getUsername());
                            bid.put("receiver", ViewRiderLocation.this.i.getStringExtra("username"));
                            bid.put(Bids.Bid_KEY, ViewRiderLocation.this.editString);
                            bid.put("objID", ViewRiderLocation.this.i.getStringExtra("objID"));
                            bid.put("type", ViewRiderLocation.this.i.getStringExtra("type"));
                            ParseACL parseACL = new ParseACL();
                            parseACL.setPublicWriteAccess(true);
                            parseACL.setPublicReadAccess(true);
                            bid.setACL(parseACL);
                            bid.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Log.i("nullAlert", "done");
                                        ViewRiderLocation.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" + ViewRiderLocation.this.i.getDoubleExtra("latitude", 0.0d) + "," + ViewRiderLocation.this.i.getDoubleExtra("longitude", 0.0d))));
                                        return;
                                    }
                                    Log.i("notNull", "Printing stack");
                                    e.printStackTrace();
                                }
                            });
                        }
                        return;
                    }
                    return;
                }
                Log.i("notNull", "Printing stack2");
                e.printStackTrace();
            }
        });
    }

    public void findIt() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo("objID", this.i.getStringExtra("objID"));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    String driversUsername = ParseUser.getCurrentUser().getUsername();
                    Log.i("null", "you'regood");
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            if (object.getObjectId().equals(ViewRiderLocation.this.i.getStringExtra("objID"))) {
                                Log.i("null", "you'regood");
                                object.getRelation("aRequest").add(ViewRiderLocation.this.bid);
                                object.put("hasBid", Boolean.valueOf(true));
                                object.put("hasaBid", "yes");
                                object.saveInBackground(new SaveCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            ViewRiderLocation.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" + ViewRiderLocation.this.i.getDoubleExtra("latitude", 0.0d) + "," + ViewRiderLocation.this.i.getDoubleExtra("longitude", 0.0d))));
                                            return;
                                        }
                                        Log.i("not fixed", e.toString());
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rider_location);
        MobileAds.initialize(getApplicationContext(), " ca-app-pub-3993853389831349~2393643917");
        this.mAdView = (AdView) findViewById(R.id.adView);
        this.mAdView.loadAd(new Builder().build());
        this.aMap = new HashMap();
        this.aMap1 = new HashMap();
        this.aMap2 = new HashMap();
        this.aMap3 = new HashMap();
        this.i = getIntent();
        this.user = new ArrayList();
        this.user.clear();
        this.user.add(this.i.getStringExtra("username"));
        this.j = new Intent(this, UserList.class);
        this.j.putExtra("username", this.user.get(0));
        this.mEditText = (EditText) findViewById(R.id.messageFromDriver);
        this.button = (Button) findViewById(R.id.ChatButton);
        this.user = new ArrayList();
        setUpMapIfNeeded();
        this.mapLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        markerDraw();
        this.button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ViewRiderLocation.this.startActivity(ViewRiderLocation.this.j);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (this.mMap == null) {
            this.mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (this.mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
    }

    public void markerDraw() {
        this.mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                Double theirLat = Double.valueOf(ViewRiderLocation.this.i.getDoubleExtra("latitude", 0.0d));
                Double theirLong = Double.valueOf(ViewRiderLocation.this.i.getDoubleExtra("longitude", 0.0d));
                theirLat = Double.valueOf(theirLat.doubleValue() + 0.0023d);
                theirLong = Double.valueOf(theirLong.doubleValue() + 0.0023d);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                ArrayList<Marker> markers = new ArrayList();
                ViewRiderLocation.this.getFromUser();
                markers.add(ViewRiderLocation.this.mMap.addMarker(new MarkerOptions().position(new LatLng(theirLat.doubleValue(), theirLong.doubleValue())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Requester Location is obscured by about 3 miles until you accept").snippet(ViewRiderLocation.this.fromUser)));
                markers.add(ViewRiderLocation.this.mMap.addMarker(new MarkerOptions().position(new LatLng(ViewRiderLocation.this.i.getDoubleExtra("userLatitude", 0.0d), ViewRiderLocation.this.i.getDoubleExtra("userLongitude", 0.0d))).title("Your Location")));
                Iterator it = markers.iterator();
                while (it.hasNext()) {
                    builder.include(((Marker) it.next()).getPosition());
                }
                ViewRiderLocation.this.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
            }
        });
    }
}
