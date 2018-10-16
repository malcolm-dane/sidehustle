package com.parse.starter.mUtil;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import com.parse.starter.LocationHelper;

public class LocationUtility implements LocationHelper {
    LocationManager locationManager;
    Context mcontext;

    public LocationUtility(Context context) {
        this.mcontext = context;
    }

    public Location setUpLocation() {
        this.locationManager = (LocationManager) this.mcontext.getSystemService("location");
        return this.locationManager.getLastKnownLocation(this.locationManager.getBestProvider(new Criteria(), true));
    }

    public Location setUpLocation(Location a) {
        return null;
    }

    public LocationManager theManager(LocationManager a) {
        a = (LocationManager) this.mcontext.getSystemService("location");
        Location location = a.getLastKnownLocation(a.getBestProvider(new Criteria(), true));
        return a;
    }
}
