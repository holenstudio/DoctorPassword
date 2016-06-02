package com.holenstudio.doctorpassword.model;

import java.io.Serializable;

/**
 * Created by Holen on 2016/6/2.
 */
public class AppInfo implements Serializable {
    private String mKey;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "mKey='" + mKey + '\'' +
                '}';
    }
}
