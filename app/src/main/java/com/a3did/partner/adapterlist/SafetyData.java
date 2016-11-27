package com.a3did.partner.adapterlist;

import android.graphics.drawable.Drawable;

/**
 * Created by edward on 11/26/2016.
 */
public class SafetyData {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String dateStr ;

    public void setItem(Drawable icon, String title, String desc){
        iconDrawable = icon;
        titleStr = title;
        dateStr = desc ;
    }
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDate(String desc) {
        dateStr = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDate() {
        return this.dateStr ;
    }
}
