package com.a3did.partner.adapterlist;

import android.graphics.drawable.Drawable;

/**
 * Created by Joonhyun on 2016-11-19.
 */

public class AchievementListData {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String fromStr ;
    private String toStr ;
    private int starNumber;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setFromStr(String from) {
        fromStr = from ;
    }
    public void setToStr(String to) {
        toStr = to ;
    }
    public void setStarNumber(int num) {
        starNumber = num ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getFromStr() {
        return this.fromStr ;
    }
    public String getToStr() {
        return this.toStr ;
    }
    public int getStarNumber() {
        return this.starNumber;
    }
}
