package com.holenstudio.doctorpassword.model;

import java.io.Serializable;

/**
 * Created by Holen on 2016/5/23.
 */
public class PasswordInfo implements Serializable{

    private String mSite;
    private String mTitle;
    private String mUsername;
    private String mPassword;
    private String mNote;
    private int mLevel;

    public PasswordInfo(String username, String password) {
        this("", "", username, password, "", 0);
    }

    public PasswordInfo(String site, String title, String username, String password, String note) {
        this(site, title, username, password, note, 0);
    }

    public PasswordInfo(String site, String title, String username, String password, String note, int level) {
        this.mSite = site;
        this.mTitle = title;
        this.mUsername = username;
        this.mPassword = password;
        this.mNote = note;
        this.mLevel = level;
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
                "mSite='" + mSite + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mNote='" + mNote + '\'' +
                ", mLevel=" + mLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordInfo that = (PasswordInfo) o;

        if (mLevel != that.mLevel) return false;
        if (mSite != null ? !mSite.equals(that.mSite) : that.mSite != null) return false;
        if (mTitle != null ? !mTitle.equals(that.mTitle) : that.mTitle != null) return false;
        if (mUsername != null ? !mUsername.equals(that.mUsername) : that.mUsername != null)
            return false;
        if (mPassword != null ? !mPassword.equals(that.mPassword) : that.mPassword != null)
            return false;
        return mNote != null ? mNote.equals(that.mNote) : that.mNote == null;

    }

    @Override
    public int hashCode() {
        int result = mSite != null ? mSite.hashCode() : 0;
        result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
        result = 31 * result + (mUsername != null ? mUsername.hashCode() : 0);
        result = 31 * result + (mPassword != null ? mPassword.hashCode() : 0);
        result = 31 * result + (mNote != null ? mNote.hashCode() : 0);
        result = 31 * result + mLevel;
        return result;
    }
}
