package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.adapters.requestAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.katso.livebutton.LiveButton;

public class ViewRequests extends AppCompatActivity implements LocationListener {
    static final String TAG =ViewRequests.class.getSimpleName();
    String MessageFromUser;
    ListView listView;
    ArrayList<String> listViewContent123;
    ArrayList<String> usernames;
    ArrayList<String> message;
    ArrayList<Double> latitudes;
    ArrayList<Double> longitudes;
    ArrayList<String> objurl;
    ArrayList<String> objID;
    requestAdapter arrayAdapter;
    ArrayList<String>usersRequests;
    ArrayList<String>type;
    ArrayList<String>mytype;
    ArrayList<Bitmap> fromParse;
    Bitmap aBit;
    Location location;
    private boolean flagOff;
    LocationManager locationManager;
    String provider;
    int c;
boolean ViewingOwnREquest;
    @Override
  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);
        flagOff=false;
        ViewingOwnREquest=false;
        LiveButton mRefreshB= (LiveButton) findViewById(R.id.Refresh);
        LiveButton mMyReqests= (LiveButton) findViewById(R.id.MyRequests);

        usersRequests=new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView123);
        listViewContent123 = new ArrayList<String>();
        objID=new ArrayList<>();
        objurl=new ArrayList<String>();
        usernames = new ArrayList<String>();
        latitudes = new ArrayList<Double>();
        longitudes = new ArrayList<Double>();
        message=new ArrayList<String>();
        type=new ArrayList<String>();
       mytype=new ArrayList<String>();
  fromParse=new ArrayList<Bitmap>();
        listViewContent123.add("Finding nearby requesters");

//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewContent123);
 arrayAdapter=new requestAdapter(this,listViewContent123,fromParse,objurl);
        listView.setAdapter(arrayAdapter);
        //usernames.clear();
       // updateLocation();
        //
        mRefreshB.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           updateLocation(); arrayAdapter.notifyDataSetChanged();
        }
    });
        mMyReqests.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ViewingOwnREquest=true;
            listViewContent123.clear();
            arrayAdapter.notifyDataSetChanged();
            UpdateIt();
        }
    });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
if(!ViewingOwnREquest){
                Intent i = new Intent(ViewRequests.this, ViewRiderLocation.class);
                i.putExtra("username", usernames.get(position));
                i.putExtra("MessageFromUser",message.get(position));
                i.putExtra("latitude", latitudes.get(position));
                i.putExtra("longitude", longitudes.get(position));
                i.putExtra("userLatitude", location.getLatitude());
                i.putExtra("userLongitude", location.getLongitude());
                i.putExtra("objid",objID.get(position));
                i.putExtra("type",type.get(position));
                startActivity(i);

            }
                if(ViewingOwnREquest){
                    Intent i = new Intent(ViewRequests.this,finalizeIt.class);
                    i.putExtra("username", usernames.get(position));
                    i.putExtra("myobjId",mytype.get(position));
                    i.putExtra("type",type.get(position));
                    i.putExtra("bid",message.get(position));
                    i.putExtra("theirOBJ",objID.get(position));
                    startActivity(i);
                }
        }});

        updateReq();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        locationManager.requestLocationUpdates(provider, 400, 1, this);

        location = locationManager.getLastKnownLocation(provider);

        if (location != null) {


            updateLocation();
arrayAdapter.notifyDataSetChanged();
        }




    }

    public void updateReq() {
        // aEmail=eMyproblem.getText().toString();
        //this method updates the mEmail that is a users initial message to anyone who is using the application.
       // listViewContent123.clear();
        usersRequests.clear();
        mytype.clear();
        // setContentView(R.layout.simplelist);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    flagOff = true;

                    Log.i("notnull", "Updateit");
                    for (ParseObject object : objects) {
                        /*
                        usersRequests.add(object.getObjectId());//

                        ParseQuery<ParseObject> mQuery=ParseUser.getQuery("Requests");
                            listViewContent123.add(object.getObjectId()+"You");*/
                        usersRequests.add(object.getObjectId());//}}}
                        mytype.add(object.getString("type"));
                        String a = object.getObjectId();
                    }
                }
            }
        });
    }
        public void UpdateIt(){
            message.clear();
            objID.clear();
            type.clear();
            mytype.clear();
        for(int i=0;i<usersRequests.size();i++){  c=i;


            ParseQuery<ParseObject> OQuery=ParseQuery.getQuery("Bids");

            OQuery.whereEqualTo("receiver",ParseUser.getCurrentUser().getUsername());
            OQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null){
                        Log.i("nullAlert","1done");
                        for(ParseObject object:objects){

                                Log.i("if","done");
                          type.add(object.getString("type"));
                            message.add(object.getString("Bid"));
                            usernames.add(object.getString("sender"));
                            objID.add(object.getObjectId());
                            mytype.add(object.getString("objId"));
                            listViewContent123.add("Your Request: "+object.getString("type")+"You have Bids: "+object.get("Bid")+" from user "+object.getString("sender"));
                                arrayAdapter.notifyDataSetChanged();
                        }

                              }else{
                    Log.i("elseAlert","done");
                   // listViewContent123.add("Your Request"+type.get(c)+"no One has ansered");
                   // arrayAdapter.notifyDataSetChanged();
                    }}});



        }
            ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
            query.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null)
                    {
                        flagOff = true;
                        Log.i("notnull", "Updateit");

                        for (ParseObject object : objects)
                        {
                          if(object.getParseFile("aImage")!=null){

                              //   Log.i("fromUser",file.getUrl().);
                            //   byteArray = new byte[0];


                            if(object.getString("hasBid")==null)
                        {
                            listViewContent123.add("Your Request: "+object.getString("type")+" has no Bids");
                            arrayAdapter.notifyDataSetChanged();
                        }
                        }
                    }
                }
            }
        });}

                    /*
                        ParseQuery<ParseObject> mQuery=ParseQuery.getQuery("Requests");
                        mQuery.whereEqualTo("objectId",object.getObjectId());
                        mQuery.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if(e==null){
                                    Log.i("notnull","Finding Object");
                                    for (ParseObject object:objects){
                                        ParseQuery<ParseObject> OQuery=ParseQuery.getQuery("Bids");
                                        OQuery.whereEqualTo("objID",object.getObjectId());
                                        OQuery.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if(e==null){
                                                    for(ParseObject object:objects){
                                                        listViewContent123.add("Your Request"+object.getString("type")+"You have Bids"+object.get("Bid")+"from user "+ object.getString("sender"));
                                                        arrayAdapter.notifyDataSetChanged();}
                                                }else{
                                                    for(ParseObject object:objects){listViewContent123.add("Your Request"+object.getString("type")+"You have Bids"+object.get("Bid")+"from user "+ object.getString("sender"))}
                                                    Toast.makeText(getApplicationContext(),"No Replies yet",Toast.LENGTH_SHORT).show();}
                                            }
                                        });
                                    }arrayAdapter.notifyDataSetChanged();
                                } else{Log.i("null",e.getMessage());}
                            }



                        });arrayAdapter.notifyDataSetChanged();}}
                else{Log.i("message",e.getMessage());}
            }
        });

    }
*/




    public void updateLocation() {

        final ParseGeoPoint userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        ParseUser.getCurrentUser().put("location", userLocation);
        ParseUser.getCurrentUser().saveInBackground();
        //MessageFromUser=ParseUser.getCurrentUser().get("here").toString();//Message the user entered from the landing screen
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");

        query.whereEqualTo("RequestOpen","Open_REquest");
       // query.setLimit(100);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    Log.i("MyApp", objects.toString());

                    if (objects.size() > 0) {

                        clear();
                        for (ParseObject object : objects) {
                            if (object.get("driverUsername") == null)  {
                                if(object.get("requesterUsername")!=null&&!object.get("requesterUsername").toString().equals(ParseUser.getCurrentUser().getUsername())) {
                                  // if(object.getJSONArray("Bids").toString().contains(ParseUser.getCurrentUser().getUsername())||object.getJSONArray("Bids").toString().contains(ParseUser.getCurrentUser().getUsername().toLowerCase())){
                                     //  listViewContent123.add("You answered");

                                   // }  arrayAdapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), "There are unAnswered requests", Toast.LENGTH_SHORT).show();
                                  //  Log.i("MyApp", object.getParseFile("aImage").getUrl());

                                    Double distanceInMiles = userLocation.distanceInMilesTo((ParseGeoPoint) object.get("requesterLocation"));
                                    Double distanceOneDP = (double) Math.round(distanceInMiles * 10) / 10;
                                  //  MessageFromUser = object.getString("aEmail");
                                    aMessageFromUser(MessageFromUser,object);
                                    message.add(MessageFromUser);
                                   // ParseFile file;

                                   // Log.i(objurl.toString(),"here");

                                   // arrayAdapter.notifyDataSetChanged();
                                   // testing.grabAndConvert(file,fromParse);}
                                    listViewContent123.add(distanceOneDP.toString() + " miles " + "Username is:" + object.getString("requesterUsername")+"Type of Request is"+ object.getString("type")+"Request Details are"+object.getString("aEmail")+ "Status:You haven't answered");
                                   type.add(object.getString("type")); //fromParse.add(aBit);
                                    objID.add(object.getObjectId());
                                     // fromParse.add(aBit);
                                    usernames.add(object.getString("requesterUsername"));
                                   // usernames.add(object.getString("here"));
                                    latitudes.add(object.getParseGeoPoint("requesterLocation").getLatitude());
                                    longitudes.add(object.getParseGeoPoint("requesterLocation").getLongitude());
                                }}
                          //  arrayAdapter.notifyDataSetChanged();
                            if ((object.get("driverUsername")!=null)&&object.get("driverUsername").equals(ParseUser.getCurrentUser().getUsername())) {
                                if(object.getParseFile("aImage")==null){
                                    objurl.add("http://mdane-devportfolio.com/images1/PicNoteScreencap.png");}
                                else{objurl.add(object.getParseFile("aImage").getUrl());}       //arrayAdapter.notifyDataSetChanged();
                                Log.i("MyApp", object.toString());
                                type.add(object.getString("requestType"));
                                Double distanceInMiles = userLocation.distanceInMilesTo((ParseGeoPoint) object.get("requesterLocation"));
                                Double distanceOneDP = (double) Math.round(distanceInMiles * 10) / 10;
                                MessageFromUser=object.getString("aEmail");
                                message.add(MessageFromUser);
                                listViewContent123.add(distanceOneDP.toString() + " miles "+"Username is:"+object.getString("requesterUsername")+object.getString("aEmail")+"Status: You answered");
                                usernames.add(object.getString("requesterUsername"));
                                //usernames.add(object.getString("username"));

                                   // objurl.add(file.getUrl());
                                longitudes.add(object.getParseGeoPoint("requesterLocation").getLongitude());

                            }
                           //
                        }




                    }

                    else{
                        Toast.makeText(getApplicationContext(),"Something Happened. Is your connection spooty? Hit Refresh",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        arrayAdapter.notifyDataSetChanged();
    }
public String aMessageFromUser(String a,ParseObject object){
    a=object.getString("aEmail");
    return a;
}
public void clear(){
    listViewContent123.clear();
    usernames.clear();
    latitudes.clear();
    longitudes.clear();
    message.clear();
    objID.clear();
    objurl.clear();
    type.clear();
    fromParse.clear();

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_requests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // locationManager.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(Location location) {


        updateLocation();


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
}