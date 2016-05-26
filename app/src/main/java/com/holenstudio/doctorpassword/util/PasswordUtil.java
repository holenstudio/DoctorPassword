package com.holenstudio.doctorpassword.util;

/**
 * Created by Holen on 2016/5/26.
 */
public class PasswordUtil {
    static {
        System.loadLibrary("passwordEncryption");   //defaultConfig.ndk.moduleName
    }

    public static String getEncryptString(String targetStr) {
        return nativeEncryptString(targetStr);
    }

    private static native String nativeEncryptString(String targetStr);
}