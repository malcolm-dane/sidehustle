package com.parse.starter.models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.cast.TextTrackStyle;
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
import com.parse.starter.Activities.TabbedMain;
import com.parse.starter.CustomList;
import com.parse.starter.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class YourLocation extends FragmentActivity implements LocationListener {
    ImageButton aButton;
    ImageButton aButton1;
    ImageButton aButton2;
    ImageButton aButton3;
    ImageButton aButton4;
    String aEmail;
    Button aSendButton;
    Button cancelIt;
    EditText describe;
    ParseGeoPoint driverLocation = new ParseGeoPoint(0.0d, 0.0d);
    String driverUsername = "";
    String eMyproblem;
    EditText erequestDetails;
    Handler handler = new Handler();
    private String hintText;
    Integer[] imageId = new Integer[]{Integer.valueOf(R.drawable.chores), Integer.valueOf(R.drawable.work), Integer.valueOf(R.drawable.nanny), Integer.valueOf(R.drawable.other), Integer.valueOf(R.drawable.coder), Integer.valueOf(R.drawable.needjump), Integer.valueOf(R.drawable.gymbuddy)};
    TextView infoTextView;
    ListView jobs;
    Location location;
    LocationManager locationManager;
    private AdView mAdView;
    ArrayAdapter mAdapter;
    ArrayAdapter mJobAdapter;
    private GoogleMap mMap;
    String provider;
    Button refresh;
    Boolean requestActive = Boolean.valueOf(false);
    Button requestButton;
    String requestType;
    Button sendButton;
    Button sendItButton;
    ArrayList<ParseFile> uRequests;
    ParseGeoPoint userLocation;
    ArrayList<String> usersRequests;
    final String[] web = new String[]{"chores: Random tasks you don't want to do", "worker: for jobs that require skill", "nanny:for a gig that requires watching or caring for another", "other: some other request", "coder:some type of computer work", "Jump Start:You're car battery is dead, bummer.Get someone to jump it", "Gym Buddy: It's time to lose the freshmen 15. But you don't know how. Somone does. Find them"};

    public void Cancelit(View view) {
        ParseQuery<ParseObject> query = new ParseQuery("Requests");
        query.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        object.deleteInBackground(new DeleteCallback() {
                            public void done(ParseException e) {
                                Log.i("Delete", "Canceled");
                            }
                        });
                    }
                }
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_location);
        setUpMapIfNeeded();
        this.usersRequests = new ArrayList();
        this.requestButton = (Button) findViewById(R.id.request);
        this.infoTextView = (TextView) findViewById(R.id.infoTextView);
        this.requestButton = (Button) findViewById(R.id.request);
        this.uRequests = new ArrayList();
        this.sendButton = (Button) findViewById(R.id.SendButton);
        this.refresh = (Button) findViewById(R.id.Refresh);
        this.locationManager = (LocationManager) getSystemService("location");
        this.provider = this.locationManager.getBestProvider(new Criteria(), false);
        this.locationManager.requestLocationUpdates(this.provider, 400, TextTrackStyle.DEFAULT_FONT_SCALE, this);
        this.location = this.locationManager.getLastKnownLocation(this.provider);
        if (this.location != null) {
            updateLocation(this.location);
        }
        this.requestButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                YourLocation.this.RequestBuilder();
            }
        });
    }

    public void updateIt(View view) {
        this.usersRequests.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
        query.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        if (object.getString(aRequest.USER_ID_KEY).equals(String.valueOf(ParseUser.getCurrentUser().getUsername()))) {
                            YourLocation.this.usersRequests.add(object.getObjectId());
                        }
                    }
                }
            }
        });
    }

    public void RequestBuilder() {
        setContentView(R.layout.simplelistq);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6405485161020407~7683183944");
        this.mAdView = (AdView) findViewById(R.id.adView);
        this.mAdView.loadAd(new Builder().build());
        CustomList adapter = new CustomList(this, this.web, this.imageId);
        ListView list = (ListView) findViewById(R.id.aLV);
        findViewById(R.id.attach).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                YourLocation.this.startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 0);
            }
        });
        this.describe = (EditText) findViewById(R.id.AET);
        this.describe.setVisibility(4);
        this.aSendButton = (Button) findViewById(R.id.AB);
        this.aSendButton.setVisibility(4);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                YourLocation.this.aSendButton.setVisibility(0);
                YourLocation.this.describe.setVisibility(0);
                YourLocation.this.describe.setHint("You Clicked at " + YourLocation.this.web[position] + " explain what you need done!");
                YourLocation.this.requestType = YourLocation.this.web[position];
                YourLocation.this.aSendButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        String aEmail = YourLocation.this.describe.getText().toString();
                        ParseObject newRequest = ParseObject.create(aRequest.class);
                        newRequest.put("RequestOpen", "Open_REquest");
                        newRequest.put(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
                        newRequest.put("type", YourLocation.this.requestType);
                        newRequest.put(aRequest.requesterLocation, new ParseGeoPoint(YourLocation.this.location.getLatitude() + 0.0102d, YourLocation.this.location.getLongitude() + 0.0102d));
                        newRequest.put("aEmail", aEmail);
                        if (YourLocation.this.uRequests.isEmpty()) {
                            newRequest.put("hasPhoto", Boolean.valueOf(false));
                        } else {
                            Log.i("NOT EMPTY", "NOT EMPTY");
                            newRequest.put("aImage", YourLocation.this.uRequests.get(0));
                            newRequest.put("hasPhoto", Boolean.valueOf(true));
                        }
                        ParseACL acl = new ParseACL();
                        acl.setPublicReadAccess(true);
                        acl.setPublicWriteAccess(true);
                        newRequest.setACL(acl);
                        newRequest.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("null alert", "null");
                                    YourLocation.this.startActivity(new Intent(YourLocation.this, TabbedMain.class));
                                    return;
                                }
                                Log.i("k", e.getMessage());
                            }
                        });
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null) {
            try {
                Bitmap bitmap = Media.getBitmap(getContentResolver(), data.getData());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                this.uRequests.clear();
                this.uRequests.add(new ParseFile("image.png", byteArray));
                RequestBuilder();
            } catch (Exception e) {
            }
        }
    }

    public void updateLocation(Location location) {
        if (this.driverUsername.equals("")) {
            this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10.0f));
            this.mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location"));
        }
        if (!this.driverUsername.equals("")) {
            ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
            userQuery.whereEqualTo("username", this.driverUsername);
            userQuery.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null && objects.size() > 0) {
                        for (ParseUser driver : objects) {
                            YourLocation.this.driverLocation = driver.getParseGeoPoint("location");
                        }
                    }
                }
            });
            if (!(this.driverLocation.getLatitude() == 0.0d || this.driverLocation.getLongitude() == 0.0d)) {
                Log.i("AppInfo", this.driverLocation.toString());
                this.infoTextView.setText("Your driver is " + Double.valueOf(((double) Math.round(Double.valueOf(this.driverLocation.distanceInMilesTo(new ParseGeoPoint(location.getLatitude(), location.getLongitude()))).doubleValue() * 10.0d)) / 10.0d).toString() + " miles away ");
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                ArrayList<Marker> markers = new ArrayList();
                markers.add(this.mMap.addMarker(new MarkerOptions().position(new LatLng(this.driverLocation.getLatitude(), this.driverLocation.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Driver Location")));
                markers.add(this.mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location")));
                Iterator it = markers.iterator();
                while (it.hasNext()) {
                    builder.include(((Marker) it.next()).getPosition());
                }
                this.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
            }
            this.userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
            ParseQuery<ParseObject> query = new ParseQuery("aRequest");
            query.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null && objects.size() > 0) {
                        for (ParseObject object : objects) {
                            object.put(aRequest.requesterLocation, YourLocation.this.userLocation);
                            object.saveInBackground();
                        }
                    }
                }
            });
        }
        final Location location2 = location;
        this.handler.postDelayed(new Runnable() {
            public void run() {
                YourLocation.this.updateLocation(location2);
            }
        }, 5000);
    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        this.locationManager.requestLocationUpdates(this.provider, 400, TextTrackStyle.DEFAULT_FONT_SCALE, this);
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

    protected void onPause() {
        super.onPause();
        this.locationManager.removeUpdates(this);
    }

    public void onLocationChanged(Location location) {
        this.mMap.clear();
        updateLocation(location);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
