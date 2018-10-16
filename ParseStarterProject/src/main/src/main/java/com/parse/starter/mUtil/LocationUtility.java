package com.parse.starter.mUtil;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.app.Activity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;
import com.parse.starter.LocationHelper;

import java.util.List;

/**
 * Created by - on 6/26/2017.
 */

public class LocationUtility implements LocationHelper {
Context mcontext;
    LocationManager locationManager;
    public LocationUtility(Context context){
this.mcontext=context;
}
    @Override
    public Location setUpLocation() {

        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)mcontext.getSystemService(context);
        String provider =  locationManager.getBestProvider(new Criteria(),true);
        Location location = locationManager.getLastKnownLocation(provider);

        return location;
    }

    @Override
    public Location setUpLocation(Location a) {
        return null;
    }
public LocationManager theManager(LocationManager a){
    String context = Context.LOCATION_SERVICE;
    a=(LocationManager)mcontext.getSystemService(context);
    String provider =  a.getBestProvider(new Criteria(),true);
    Location location =a.getLastKnownLocation(provider);
    return a;
}

}

