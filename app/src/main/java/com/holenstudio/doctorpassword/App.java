package com.holenstudio.doctorpassword;

import android.app.Application;

import com.holenstudio.doctorpassword.model.AppInfo;

/**
 * Created by Holen on 2016/6/2.
 */
public class App extends Application{
    AppInfo mInfo;

    @Override
    public void onCreate() {
        super.onCreate();

        mInfo = new AppInfo();
    }

    public AppInfo getInfo() {
        return mInfo;
    }

}
