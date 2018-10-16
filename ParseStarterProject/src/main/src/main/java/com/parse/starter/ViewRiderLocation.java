package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewRiderLocation extends FragmentActivity {
    String fromUser;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Intent i;Intent j;
    EditText mEditText;
    String editString="";
    RelativeLayout mapLayout;
    ArrayList<String>user;
Map aMap;
    Button button;
    Map<Map,Map> aMap2;
    Map<String,String> aMap1;
    Map<String,Map> aMap3;
    public void back(View view) {

        Intent intent = new Intent(ViewRiderLocation.this, ViewRequests.class);
        startActivity(intent);

    }
    public void getFromUser(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo("requesterUsername", i.getStringExtra("username"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {


                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {
                            fromUser=object.getString("aEmail");

                        }}}}});}
    public void acceptRequest(View view) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo("requesterUsername", i.getStringExtra("username"));
        mEditText=(EditText)findViewById(R.id.messageFromDriver);
        editString=mEditText.getText().toString();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    String driversUsername=ParseUser.getCurrentUser().getUsername();

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {
                            if(object.getObjectId().equals(i.getStringExtra("objid"))) {
                                Log.i("null", "you'regood");
                                object.put("hasBid", "true");
                            }
                               ParseObject bid= ParseObject.create(Bids.class);
                            bid.put(Bids.USER_ID_KEY,ParseUser.getCurrentUser().getUsername());
                            bid.put(Bids.RECEIVER_ID_KEY,i.getStringExtra("username"));
                            bid.put(Bids.Bid_KEY,editString);
                            bid.put(Bids.Object_ID_KEY,i.getStringExtra("objid"));
                           bid.put(Bids.TYPE_ID_KEY,i.getStringExtra("type"));
                                ParseACL parseACL = new ParseACL();
                                parseACL.setPublicWriteAccess(true);//yeah this doesn't look safe
                                parseACL.setPublicReadAccess(true);
                                bid.setACL(parseACL);
                                bid.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Log.i("notnull","done");

                                    if (e == null) {
                                        Log.i("nullAlert","done");
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                Uri.parse("http://maps.google.com/maps?daddr=" + i.getDoubleExtra("latitude", 0) + "," + i.getDoubleExtra("longitude", 0)));
                                        startActivity(intent);
                                }
                            }
                            });}}}}});}
                            //object.addAllUnique("driversUsername",Arrays.asList(ParseUser.getCurrentUser().getUsername()));





        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rider_location);
       aMap=new HashMap<>();
        aMap1=new HashMap<>();
        aMap2=new HashMap<>();
        aMap3=new HashMap<>();
        i = getIntent();
        user= new ArrayList<String>();
        user.clear();
        user.add(i.getStringExtra("username"));
        j = new Intent(ViewRiderLocation.this, UserList.class);
        j.putExtra("username",user.get(0));
        mEditText=(EditText)findViewById(R.id.messageFromDriver);
        button=(Button)findViewById(R.id.ChatButton);

        user= new ArrayList<String>();
        //fromUser=ParseUser.getCurrentUser().get("here").toString();

        setUpMapIfNeeded();

        mapLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        //getFromUser();
        markerDraw();
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(j);
    }
});
      /*  mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                ArrayList<Marker> markers = new ArrayList<Marker>();

                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(i.getDoubleExtra("latitude", 0), i.getDoubleExtra("longitude", 0))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Rider Location").snippet(fromUser)));

                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(i.getDoubleExtra("userLatitude", 0), i.getDoubleExtra("userLongitude", 0))).title("Your Location")));

                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }

                LatLngBounds bounds = builder.build();

                int padding = 100;
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                mMap.animateCamera(cu);



            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {




    }

    public void markerDraw(){

        mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                ArrayList<Marker> markers = new ArrayList<Marker>();
                getFromUser();
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(i.getDoubleExtra("latitude", 0), i.getDoubleExtra("longitude", 0))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Rider Location").snippet(fromUser)));

                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(i.getDoubleExtra("userLatitude", 0), i.getDoubleExtra("userLongitude", 0))).title("Your Location")));

                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }

                LatLngBounds bounds = builder.build();

                int padding = 100;
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                mMap.animateCamera(cu);



            }
        });
    }

}