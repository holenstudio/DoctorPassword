#include <jni.h>
#include "stdio.h"
#include "string.h"
//#include "base64.h"

char *encryptString(char *str) {
    char *result = str;
    int length = strlen(str);
    int index = 0;
    for (; index < length; index ++) {
        result[index]++;
    }
    return result;
}

char *decryptString(char *str) {
    char *result = str;
    int length = strlen(str);
    int index = 0;
    for (; index < length; index ++) {
        result[index]--;
    }
    return result;
}

JNIEXPORT jstring
JNICALL Java_com_holenstudio_doctorpassword_util_PasswordUtil_nativeDecryptString
        (JNIEnv *env, jobject obj, jstring str, jstring keystr) {
    char *password = (*env)->GetStringUTFChars(env, str, 0);
    char *result = decryptString(password);
    jint version = (*env)->GetVersion(env);
    return (*env)->NewStringUTF(env, result);
}

JNIEXPORT jstring
JNICALL Java_com_holenstudio_doctorpassword_util_PasswordUtil_nativeEncryptString
        (JNIEnv *env, jobject obj, jstring str, jstring keystr) {
    char *password = (*env)->GetStringUTFChars(env, str, 0);
    char *result = encryptString(password);
    jint version = (*env)->GetVersion(env);
    return (*env)->NewStringUTF(env, result);
}

