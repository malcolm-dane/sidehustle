package com.parse.starter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.MVC.staticUtils;
import com.parse.starter.SingleItemView;
import com.parse.starter.adapters.ListViewAdapter;
import com.parse.starter.models.aRequest;
import com.parse.starter.models.vrData;
import java.util.ArrayList;
import java.util.List;
import com.parse.starter.R;

import ru.katso.livebutton.LiveButton;

public class vw2 extends ViewRequests {
    ListViewAdapter adapter;
    private List<vrData> arequestlist = null;
    List<vrData> arequestlist1;
    boolean isme = false;
    ListView listview;
    private AdView mAdView;
    LiveButton mMyReqests;
    ProgressDialog mProgressDialog;
    LiveButton mRefreshB;
    List<ParseObject> ob;
    private List<vrData> ulist = null;
    vrData umap;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            vw2.this.arequestlist = new ArrayList();
            vw2.this.ulist = new ArrayList();
            final ParseGeoPoint userLocation = new ParseGeoPoint(vw2.this.location.getLatitude(), vw2.this.location.getLongitude());
            ParseUser.getCurrentUser().put(aRequest.driverLocation, userLocation);
            ParseQuery<ParseObject> query = new ParseQuery("aRequest");
            query.whereNotEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    for (ParseObject object : objects) {
                        ParseFile image = (ParseFile) object.get("aImage");
                        if (object.get("RequestOpen").equals("Open_REquest")) {
                            vrData map = new vrData();
                            map.setusername(object.get(aRequest.USER_ID_KEY).toString());
                            map.setMessage((String) object.get("aEmail"));
                            map.setOther(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLatitude()));
                            map.setOtherLong(Double.valueOf(object.getParseGeoPoint(aRequest.requesterLocation).getLongitude()));
                            map.setmyLat(Double.valueOf(vw2.this.location.getLatitude()));
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
                            vw2.this.arequestlist.add(map);
                            vw2.this.adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
            ParseQuery<ParseObject> aquery = new ParseQuery("aRequest");
            aquery.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
            aquery.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject object : objects) {
                            if (object.get("RequestOpen").equals("Open_REquest")) {
                                vw2.this.umap = new vrData();
                                vw2.this.umap.setusername(object.get(aRequest.USER_ID_KEY).toString());
                                ParseFile image = (ParseFile) object.get("aImage");
                                if (object.getParseFile("aImage") != null) {
                                    vw2.this.umap.setImageUrl(object.getParseFile("aImage").getUrl());
                                }
                                vw2.this.umap.setType(object.getString("type"));
                                vw2.this.umap.setobjID(object.getObjectId());
                                if (object.get("hasbid1") != null) {
                                    vw2.this.umap.setBid(object.getString("hasbid1"));
                                } else {
                                    vw2.this.umap.setBid("noBid");
                                }
                                vw2.this.ulist.add(vw2.this.umap);
                            }
                        }
                    }
                }
            });
            return null;
        }

        public void redirect(int position) {
            Intent intent = new Intent(vw2.this, SingleItemView.class);
            intent.putExtra("rank", vw2.this.ulist.get(position).getusername());
            intent.putExtra("country", vw2.this.ulist.get(position).getMessage());
            intent.putExtra("timestamp", vw2.this.ulist.get(position).getobjID());
            intent.putExtra("flag", vw2.this.ulist.get(position).getImageUrl());
            intent.putExtra("longitude", vw2.this.ulist.get(position).getOtherLong());
            intent.putExtra("latitude", vw2.this.ulist.get(position).getOtherLat());
            intent.putExtra("userLongitude", vw2.this.ulist.get(position).getMyLong());
            intent.putExtra("userLatitude", vw2.this.ulist.get(position).getMyLat());
            intent.putExtra("objID", vw2.this.ulist.get(position).getobjID());
            intent.putExtra("username", vw2.this.ulist.get(position).getusername());
            intent.putExtra("bid", vw2.this.ulist.get(position).getBid());
            intent.putExtra("type", vw2.this.ulist.get(position).getType());
            vw2.this.startActivity(intent);
        }

        public void otherRedirect(int position) {
            Intent intent = new Intent(vw2.this, ViewRiderLocation.class);
            intent.putExtra("objID", vw2.this.arequestlist.get(position).getobjID());
            Log.i(vw2.this.arequestlist.get(position).getobjID(), vw2.this.arequestlist.get(position).getobjID());
            intent.putExtra("rank", vw2.this.arequestlist.get(position).getusername());
            intent.putExtra("country", vw2.this.arequestlist.get(position).getMessage());
            intent.putExtra("timestamp", vw2.this.arequestlist.get(position).getTimeStamp());
            intent.putExtra("flag", vw2.this.arequestlist.get(position).getImageUrl());
            intent.putExtra("longitude", vw2.this.arequestlist.get(position).getOtherLong());
            intent.putExtra("latitude", vw2.this.arequestlist.get(position).getOtherLat());
            intent.putExtra("userLongitude", vw2.this.arequestlist.get(position).getMyLong());
            intent.putExtra("userLatitude", vw2.this.arequestlist.get(position).getMyLat());
            intent.putExtra("username", vw2.this.arequestlist.get(position).getusername());
            intent.putExtra("type", vw2.this.arequestlist.get(position).getType());
            vw2.this.startActivity(intent);
        }

        protected void onPostExecute(Void result) {
            vw2.this.mAdView = (AdView) vw2.this.findViewById(R.id.adView);
            vw2.this.mAdView.loadAd(new Builder().build());
            vw2.this.listview = (ListView) vw2.this.findViewById(R.id.vrListView);
            vw2.this.adapter = new ListViewAdapter(vw2.this, vw2.this.arequestlist);
            vw2.this.listview.setAdapter(vw2.this.adapter);
            vw2.this.adapter.notifyDataSetChanged();
            vw2.this.listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if (vw2.this.isme) {
                        RemoteDataTask.this.redirect(position);
                    } else {
                        RemoteDataTask.this.otherRedirect(position);
                    }
                }
            });
            vw2.this.mRefreshB = (LiveButton) vw2.this.findViewById(R.id.Refresh);
            vw2.this.mMyReqests = (LiveButton) vw2.this.findViewById(R.id.MyRequests);
            vw2.this.mRefreshB.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (vw2.this.isme) {
                        vw2.this.isme = false;
                    }
                    List<vrData> arequestlist1 = new ArrayList();
                    arequestlist1 = staticUtils.getRequests(vw2.this.location);
                    vw2.this.listview = (ListView) vw2.this.findViewById(R.id.vrListView);
                    vw2.this.adapter = new ListViewAdapter(vw2.this, vw2.this.arequestlist);
                    vw2.this.listview.setAdapter(vw2.this.adapter);
                }
            });
            vw2.this.mMyReqests.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    vw2.this.isme = true;
                    vw2.this.adapter = new ListViewAdapter(vw2.this, vw2.this.ulist);
                    vw2.this.listview.setAdapter(vw2.this.adapter);
                    vw2.this.adapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vrlist);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6405485161020407~7683183944");
        new RemoteDataTask().execute();
    }

    public void onLocationChanged(Location location) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
