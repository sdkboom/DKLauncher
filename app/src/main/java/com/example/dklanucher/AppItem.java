package com.example.dklanucher;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by SDK on 2018-9-18.
 */

public class AppItem {

    private String appName ;
    private Drawable appIcon ;
    private Intent intent ;
    private String pkgName ;
    private int Id ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

}
