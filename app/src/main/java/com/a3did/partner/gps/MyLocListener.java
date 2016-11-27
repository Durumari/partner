package com.a3did.partner.gps;

/**
 * Created by edward on 11/26/2016.
 */
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.a3did.partner.partner.R;

/**
 * Created by Aylar-HP on 28/09/2015.
 */
public class MyLocListener implements LocationListener {

//    MyLocListener(){
//
//    }
    TextView t;
    @Override
    public void onLocationChanged(Location location)
    {

        t = (TextView) t.findViewById(R.id.textView_coordinate);

        if(location != null)
        {
            Log.d("TEST", "Latitude" + location.getLatitude());
            Log.d("TEST", "Longitude" + location.getLongitude());
        }else{
            Log.e("TEST", "ERROR: no gps location ");

        }
        t.append(" " + location.getLongitude()+ " " +location.getLatitude());
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}
