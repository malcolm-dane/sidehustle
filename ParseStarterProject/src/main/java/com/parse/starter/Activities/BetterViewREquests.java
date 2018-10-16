package com.parse.starter.Activities;

import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import com.google.android.gms.cast.TextTrackStyle;
import com.parse.starter.MVC.staticUtils;
import com.parse.starter.R;
import com.parse.starter.adapters.ListViewAdapter;
import com.parse.starter.models.vrData;
import java.util.ArrayList;
import java.util.List;
import com.parse.starter.R;

public class BetterViewREquests extends AppCompatActivity {
    ListView aLv;
    ListViewAdapter adapter;
    Location location;
    LocationManager locationManager;
    private String provider;
    private List<vrData> vrdataList;

    private static class LocationExtender implements LocationListener {
        private LocationExtender() {
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

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.locationManager = (LocationManager) getSystemService("location");
        this.provider = this.locationManager.getBestProvider(new Criteria(), false);
        this.locationManager.requestLocationUpdates(this.provider, 400, TextTrackStyle.DEFAULT_FONT_SCALE, new LocationExtender());
        this.location = this.locationManager.getLastKnownLocation(this.provider);
        setContentView(R.layout.activity_better_view_requests);
        this.aLv = (ListView) findViewById(R.id.aLV);
        this.vrdataList = new ArrayList();
        this.adapter = new ListViewAdapter(this, this.vrdataList);
    }

    public void addAll(View View) {
        this.aLv.setAdapter(this.adapter);
        this.vrdataList = staticUtils.getRequests(this.location);
        this.adapter.notifyDataSetChanged();
    }
}
