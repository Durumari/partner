package com.a3did.partner.adapterlist;

import android.graphics.drawable.Drawable;

/**
 * Created by Joonhyun on 2016-11-19.
 */

public class RewardListData {
    private Drawable iconDrawable ;
    private String titleStr ;
    private int starNum;

    public void setItem(Drawable icon, String title, int starnum){
        iconDrawable = icon;
        titleStr = title;
        starNum = starnum;
    }
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setStarNum(int starnum) {
        starNum = starnum ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public int getStarNum() {
        return this.starNum ;
    }

}
