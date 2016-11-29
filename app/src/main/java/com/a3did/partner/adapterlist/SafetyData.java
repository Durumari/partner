package com.a3did.partner.adapterlist;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by edward on 11/26/2016.
 */
public class SafetyData {
    private Drawable iconDrawable ;
    private String titleStr ;
    private double latData;
    private double lngData;
    private LatLng geoInfo;

    public void setItem(String title, double lat, double lng){
        //iconDrawable = ;
        titleStr = title;
        latData = lat;
        lngData = lng;
        geoInfo = new LatLng(latData, lngData);
    }

    public void setItem(String title, LatLng geo){
        //iconDrawable = ;
        titleStr = title;
        latData = geo.latitude;
        lngData = geo.longitude;
        geoInfo = geo;
    }
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setGeoInfo(double lat, double lng) {
        latData = lat;
        lngData = lng;
        geoInfo = new LatLng(latData, lngData);
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public LatLng getGeoInfo() {
        return this.geoInfo ;
    }
    public double getLatData(){
        return this.latData;
    }
    public double getLngData(){
        return this.lngData;
    }
}
