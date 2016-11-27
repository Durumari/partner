package com.a3did.partner.adapterlist;

import android.graphics.drawable.Drawable;

/**
 * Created by Joonhyun on 2016-11-19.
 */

public class AssistantListData {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String dateStr ;

    public void setItem(Drawable icon, String title, String date){
        iconDrawable = icon;
        titleStr = title;
        dateStr = date;
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
