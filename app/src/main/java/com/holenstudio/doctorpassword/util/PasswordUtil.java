package com.holenstudio.doctorpassword.util;

import android.util.Base64;
import android.util.Log;

/**
 * Created by Holen on 2016/5/26.
 */
public class PasswordUtil {
    private final static String TAG = "PasswordUtil";

    static {
        System.loadLibrary("passwordEncryption");   //defaultConfig.ndk.moduleName
    }

    public static String getEncryptString(String srcStr, String keyStr) {
        int i = ((int)System.currentTimeMillis() & 1);
        if (i == 1) {
            return encryptString(srcStr, keyStr);
        } else {
            return nativeEncryptString(srcStr, keyStr);
        }
    }

    public static String getDecryptString(String srcStr, String keyStr) {
        if ((System.currentTimeMillis() | 1) == 1) {
            return decryptString(srcStr, keyStr);
        } else {
            return nativeDecryptString(srcStr, keyStr);
        }
    }

    private static String encryptString(String srcStr, String keyStr) {
        try {
            byte[] key = EncryptionUtil.initKey(keyStr);
            return Base64.encodeToString(EncryptionUtil.encrypt(srcStr.getBytes("utf-8"), key), Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return "";
    }

    private static String decryptString(String srcStr, String keyStr) {
        return keyStr;
    }


    private static native String nativeEncryptString(String srcStr, String keyStr);

    private static native String nativeDecryptString(String srcStr, String keyStr);


}
