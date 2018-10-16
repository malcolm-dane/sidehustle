package com.parse.starter;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.adapters.ListViewAdapter;
import com.parse.starter.mUtil.LocationUtility;
import com.parse.starter.models.vrData;

import ru.katso.livebutton.LiveButton;

public class vw2 extends ViewRequests {
	// Declare Variables
	ListView listview;
	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	ListViewAdapter adapter;
	private List<vrData> arequestlist = null;
	private List<vrData> ulist = null;
	vrData umap;LiveButton mMyReqests;
	boolean isme=false;
	Location aLocation;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml


		setContentView(R.layout.vrlist);

			// Execute RemoteDataTask AsyncTask
		new RemoteDataTask().execute();
	}

	@Override
	public void onLocationChanged(Location location) {

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



	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> implements LocationHelper{
		Location aLocation;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
aLocation=setUpLocation();
		}
		@Override
		public Location setUpLocation() {
			aLocation=new LocationUtility(getApplicationContext()).setUpLocation();
			return aLocation;
		}

		@Override
		public Location setUpLocation(Location a) {
			return null;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			arequestlist = new ArrayList<vrData>();
			ulist = new ArrayList<vrData>();
			Location aLocation=setUpLocation();
			final ParseGeoPoint userLocation= new ParseGeoPoint(aLocation.getLatitude(),aLocation.getLongitude());
			ParseUser.getCurrentUser().put("driverLocation",userLocation);
			// Locate the class table named "object" in Parse.com
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"aRequest");
			// Locate the column named "ranknum" in Parse.com and order list
			// by ascending
			query.whereNotEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					for (ParseObject object : objects) {
						// Locate images in flag column
						ParseFile image = (ParseFile) object.get("aImage");

						vrData map = new vrData();
						map.setusername(object.get("requesterUsername").toString());
						map.setusername((String) object.get("requesterUsername"));
						map.setMessage((String) object.get("aEmail"));
						map.setOther(object.getParseGeoPoint("requesterLocation").getLatitude());
						map.setOtherLong(object.getParseGeoPoint("requesterLocation").getLongitude());
						map.setmyLat(location.getLatitude());
						map.setMyLong(object.getParseGeoPoint("requesterLocation").getLongitude());
						map.setType(object.getString("type"));
						Double distanceInMiles = userLocation.distanceInMilesTo((ParseGeoPoint) object.get("requesterLocation"));
						Double ParseGeo = (double) Math.round(distanceInMiles * 10) / 10;
						Double.toString(ParseGeo);
						map.setParseGeo(Double.toString(ParseGeo));
						if(object.getParseFile("aImage")!=null){
						map.setImageUrl((String) object.getParseFile("aImage").getUrl());}
						map.setTimeStamp((String) object.get("_id"));//object ID
						map.setobjID(object.getObjectId().toString());
						Log.i("i","here");
							arequestlist.add(map);
						adapter.notifyDataSetChanged();

				}
			}

		});
			ParseQuery<ParseObject> aquery = new ParseQuery<ParseObject>(
					"aRequest");
			aquery.whereEqualTo("requesterUsername", ParseUser.getCurrentUser().getUsername());
			aquery.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					for (ParseObject object : objects) {
						if(object.get("RequestOpen").equals("Open_REquest"))
							{

								umap = new vrData();
							umap.setusername(object.get("requesterUsername").toString());
								ParseFile image = (ParseFile) object.get("aImage");
								if(object.getParseFile("aImage")!=null){
								umap.setImageUrl((String) object.getParseFile("aImage").getUrl());}
							umap.setobjID(object.getObjectId());
								ulist.add(umap);
						}}
				//adapter.notifyDataSetChanged();
			}

			});return null;}
		public void redirect(final int position){
			Intent intent=new Intent(vw2.this,SingleItemView.class);
			intent.putExtra("rank",
					(ulist.get(position).getusername()));
			// Pass all data country
			intent.putExtra("country",
					(ulist.get(position).getMessage()));
			// Pass all data population
			intent.putExtra("timestamp",
					(ulist.get(position).getobjID()));
			// Pass all data flag
			intent.putExtra("flag",
					(ulist.get(position).getImageUrl()));
			// Start SingleItemView Class
			intent.putExtra("longitude",  ulist.get(position).getOtherLong());
			intent.putExtra("latitude", ulist.get(position).getOtherLat());
			intent.putExtra("userLongitude",  ulist.get(position).getMyLong());
			intent.putExtra("userLatitude",  ulist.get(position).getMyLat());
			intent.putExtra("objid",  ulist.get(position).getobjID());
			intent.putExtra("username",
					(ulist.get(position).getusername()));
			intent.putExtra("type",
					(ulist.get(position).getType()));
		startActivity(intent);

		}
		public void otherRedirect(final int position){
			Intent intent=new Intent(vw2.this,ViewRiderLocation.class);
			intent.putExtra("objid",arequestlist.get(position).getobjID());
			Log.i(arequestlist.get(position).getobjID(),arequestlist.get(position).getobjID());
			intent.putExtra("rank",
					(arequestlist.get(position).getusername()));
			// Pass all data country
			intent.putExtra("country",
					(arequestlist.get(position).getMessage()));
			// Pass all data population
			intent.putExtra("timestamp",
					(arequestlist.get(position).getTimeStamp()));
			// Pass all data flag
			intent.putExtra("flag",
					(arequestlist.get(position).getImageUrl()));
			// Start SingleItemView Class
			intent.putExtra("longitude",  arequestlist.get(position).getOtherLong());
			intent.putExtra("latitude", arequestlist.get(position).getOtherLat());
			intent.putExtra("userLongitude",  arequestlist.get(position).getMyLong());
			intent.putExtra("userLatitude",  arequestlist.get(position).getMyLat());
			intent.putExtra("username",
					(arequestlist.get(position).getusername()));
			intent.putExtra("type",
					(arequestlist.get(position).getType()));
			startActivity(intent);
		}

		@Override
		protected void onPostExecute(Void result) {


			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.vrListView);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(vw2.this,
					arequestlist);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);

			adapter.notifyDataSetChanged();
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//String me=ParseUser.getCurrentUser().getUsername();
					//String them=arequestlist.get(position).getusername();
if(isme){ redirect(position);}else{otherRedirect(position);}
				}
			});
			LiveButton mRefreshB= (LiveButton) findViewById(R.id.Refresh);
			mMyReqests	= (LiveButton) findViewById(R.id.MyRequests);
			mRefreshB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(isme){isme=false;}
					listview = (ListView) findViewById(R.id.vrListView);
					// Pass the results into ListViewAdapter.java
					adapter = new ListViewAdapter(vw2.this,
							arequestlist);
					// Binds the Adapter to the ListView
					listview.setAdapter(adapter);
				}
			});
			mMyReqests.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					isme=true;
					adapter = new ListViewAdapter(vw2.this,
							ulist);
					// Binds the Adapter to the ListView
					listview.setAdapter(adapter);

					adapter.notifyDataSetChanged();

				}
			});
			// Close the progressdialog
//			mProgressDialog.dismiss();
		}
	}
}



