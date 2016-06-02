package com.holenstudio.doctorpassword;

import android.app.Application;

import com.holenstudio.doctorpassword.model.AppInfo;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Holen on 2016/6/2.
 */
public class App extends Application {
    AppInfo mInfo;

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);

        mInfo = new AppInfo();
    }

    public AppInfo getInfo() {
        return mInfo;
    }

}
