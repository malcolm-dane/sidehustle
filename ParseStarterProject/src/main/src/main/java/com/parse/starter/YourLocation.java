package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.mUtil.LocationUtility;
import com.parse.starter.models.aRequest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YourLocation extends FragmentActivity implements LocationListener,LocationHelper {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    LocationManager locationManager;
    String provider;
    ArrayList<String>usersRequests;
    ArrayList<ParseFile>uRequests;
    TextView infoTextView;
    Button requestButton;
    Button cancelIt;
    Button sendButton;
    Button refresh;
    String aEmail;
    String eMyproblem;
    Boolean requestActive = false;
    String driverUsername = "";
    ParseGeoPoint driverLocation = new ParseGeoPoint(0,0);
    String requestType;
    private String hintText;
    Location location;
    Handler handler = new Handler();
    ImageButton aButton;
    Button sendItButton;
    ImageButton aButton1;
    ImageButton aButton2;
    ImageButton aButton3;
    ImageButton aButton4;
    EditText erequestDetails;
    ParseGeoPoint userLocation;
    ListView jobs;
    ArrayAdapter mJobAdapter;
    ArrayAdapter mAdapter;
    Button aSendButton;  EditText describe;
    final   String[] web = {
            "chores: Random tasks you don't want to do",
            "worker: for jobs that require skill",
            "nanny:for a gig that requires watching or caring for another",
            "other: some other request",
            "coder:some type of computer work",
            "Jump Start:You're car battery is dead, bummer.Get someone to jump it",
            "Gym Buddy: It's time to lose the freshmen 15. But you don't know how. Somone does. Find them"
    } ;
    Integer[] imageId = {
            R.drawable.chores,
            R.drawable.work,
            R.drawable.nanny,
            R.drawable.other,
            R.drawable.coder,
            R.drawable.needjump,
            R.drawable.gymbuddy,
    };





    public void Cancelit(View view){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");

        query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            object.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Log.i("Delete", "Canceled");
                                }
                            });

                        }


                    }

                }

            }
        });
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_location);
        setUpMapIfNeeded();
        usersRequests=new ArrayList<>();
        requestButton=(Button)findViewById(R.id.request) ;

        infoTextView = (TextView) findViewById(R.id.infoTextView);
        requestButton = (Button) findViewById(R.id.request);

        uRequests=new ArrayList<>();

        sendButton=(Button)findViewById(R.id.SendButton);
        refresh=(Button)findViewById(R.id.Refresh);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
setUpLocation(location);

        if (location != null) {
            updateLocation(location);

        }
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBuilder();
            }
        });



    }
    public void updateIt(View view){
        // aEmail=eMyproblem.getText().toString();
        //this method updates the mEmail that is a users initial message to anyone who is using the application.
        usersRequests.clear();
        ParseQuery<ParseObject> query=ParseQuery.getQuery("Requests");
        query.whereEqualTo("requesterUsername",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    for(ParseObject object:objects){
                        if(object.getString("requesterUsername").equals(String.valueOf(ParseUser.getCurrentUser().getUsername()))){
                            usersRequests.add(object.getObjectId());

                        }

                    }
                }
            }
        });}
    public void RequestBuilder() {

        //  final Button aSendButton final   EditText describe

        setContentView(R.layout.simplelistq);

        CustomList adapter = new
                CustomList(YourLocation.this, web, imageId);
        ListView list=(ListView)findViewById(R.id.aLV);
        Button attach=(Button)findViewById(R.id.attach);
        attach.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,0);

            }
        });
        describe= (EditText)findViewById(R.id.AET) ;
        describe.setVisibility(View.INVISIBLE);
        aSendButton=(Button)findViewById(R.id.AB) ;
        aSendButton.setVisibility(View.INVISIBLE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                aSendButton.setVisibility(View.VISIBLE);
                describe.setVisibility(View.VISIBLE);
                describe.setHint("You Clicked at " +web[+ position]+" explain what you need done!");
                requestType=web[+position];
                aSendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String aEmail=describe.getText().toString();
                        ParseObject newRequest=ParseObject.create(aRequest.class);
                      newRequest.put("RequestOpen","Open_REquest");
                        newRequest.put(aRequest.USER_ID_KEY,ParseUser.getCurrentUser().getUsername());
                        newRequest.put(aRequest.TYPE_ID_KEY,requestType);
                        newRequest.put("requesterLocation",new ParseGeoPoint(location.getLatitude(), location.getLongitude()));
                        newRequest.put("aEmail",aEmail);
                        if(!uRequests.isEmpty()){
                            Log.i("NOT EMPTY","NOT EMPTY");
                            newRequest.put("aImage",uRequests.get(0));
                   newRequest.put("hasPhoto",true);}
                            else{newRequest.put("hasPhoto",false);};
                        ParseACL acl=new ParseACL();
                        acl.setPublicReadAccess(true);
                        acl.setPublicReadAccess(true);
                        newRequest.setACL(acl);
                        newRequest.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){
                                    Log.i("null alert","null");
                                    Intent newInt=new Intent(YourLocation.this,vw2.class);
                                    startActivity(newInt);
                                }else{Log.i("k",e.getMessage());}}
                        });
                    }
                });
            }
        });}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();
                uRequests.clear();
                ParseFile file = new ParseFile("image.png", byteArray);
                uRequests.add(file);
                RequestBuilder();
            }catch(Exception r){}}}



    public void updateLocation(final Location location) {



        if (driverUsername.equals("")) {

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));

            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location"));

        }



        if (!driverUsername.equals("")) {

            ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
            userQuery.whereEqualTo("username", driverUsername);
            userQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseUser driver : objects) {

                                driverLocation = driver.getParseGeoPoint("location");

                            }

                        }

                    }
                }
            });

            if (driverLocation.getLatitude() != 0 && driverLocation.getLongitude() != 0) {

                Log.i("AppInfo", driverLocation.toString());

                Double distanceInMiles = driverLocation.distanceInMilesTo(new ParseGeoPoint(location.getLatitude(), location.getLongitude()));

                Double distanceOneDP = (double) Math.round(distanceInMiles * 10) / 10;

                infoTextView.setText("Your driver is " + distanceOneDP.toString() + " miles away ");


                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                ArrayList<Marker> markers = new ArrayList<Marker>();

                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Driver Location")));

                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location")));

                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }

                LatLngBounds bounds = builder.build();

                int padding = 100;
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                mMap.animateCamera(cu);
            }




            userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("aRequest");

            query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseObject object : objects) {

                                object.put("requesterLocation", userLocation);
                                object.saveInBackground();

                            }


                        }

                    }

                }
            });




        }

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                updateLocation(location);
            }
        }, 5000);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        locationManager.requestLocationUpdates(provider, 400, 1, this);
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

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(Location location) {

        mMap.clear();

        updateLocation(location);


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

    @Override
    public Location setUpLocation() {
return null;
    }

    @Override
    public Location setUpLocation(Location a) {
    a=new LocationUtility(getApplicationContext()).setUpLocation();
    return a;
    }
}



