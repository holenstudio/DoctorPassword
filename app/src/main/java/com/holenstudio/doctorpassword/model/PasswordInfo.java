package com.holenstudio.doctorpassword.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Holen on 2016/5/23.
 */
public class PasswordInfo extends RealmObject implements Serializable{

    @Required
    private String mId;
    private String mSite;
    private String mTitle;
    private String mUsername;
    private String mPassword;
    private String mNote;
    private int mLevel;

    public PasswordInfo() {
        this("");
    }

    public PasswordInfo(String id) {
        this(id, "", "");
    }

    public PasswordInfo(String id, String username, String password) {
        this(id,"", "",username, password, "");
    }

    public PasswordInfo(String id, String site, String title, String username, String password, String note) {
        this(id, site, title, username, password, note, 0);
    }

    public PasswordInfo(String id, String site, String title, String username, String password, String note, int level) {
        this.mId = id;
        this.mSite = site;
        this.mTitle = title;
        this.mUsername = username;
        this.mPassword = password;
        this.mNote = note;
        this.mLevel = level;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String mSite) {
        this.mSite = mSite;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        this.mLevel = level;
    }

    @Override
    public String toString() {
        return "PasswordInfo{" +
                "mId='" + mId + '\'' +
                ", mSite='" + mSite + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mNote='" + mNote + '\'' +
                ", mLevel=" + mLevel +
                '}';
    }
}
