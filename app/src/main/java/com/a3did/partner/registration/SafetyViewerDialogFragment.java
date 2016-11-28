package com.a3did.partner.registration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a3did.partner.partner.MainActivity;
import com.a3did.partner.partner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Joonhyun on 2016-11-27.
 */

public class SafetyViewerDialogFragment extends DialogFragment implements OnMapReadyCallback {
    public MainActivity mActivity = null;
    public LatLng geoInfo = null;
    private GoogleMap mMap;
    public SafetyViewerDialogFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public MapView mapView = null;
    public SupportMapFragment mapFragment = null;

    View root = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            root = inflater.inflate(R.layout.activity_maps, container, false);
            mapFragment = (SupportMapFragment)mActivity.getSupportFragmentManager()
                    .findFragmentById(R.id.mapf);
            mapFragment.getMapAsync(this);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return root;
    }

    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapFragment != null)
            mapFragment.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mapFragment != null)
            mapFragment.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mapFragment != null)
            mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapFragment != null){
            mapFragment.onDestroy();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(geoInfo).title("Dangerous Area"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(geoInfo));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }
}
