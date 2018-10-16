package com.parse.starter.mUtil;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;

public class mMapUtil {
    Location location;
    LocationManager locationManager;
    String provider;

    public void mMarkers(GoogleMap mGooglemap, Location location) {
        Log.i("Markers", "markers");
        mGooglemap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10.0f));
        mGooglemap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location"));
    }

    public static ParseGeoPoint mLocator(GoogleMap mGooglemap, Location location) {
        return new ParseGeoPoint(location.getLatitude(), location.getLongitude());
    }
}
