#include <jni.h>

JNIEXPORT jstring

JNICALL Java_com_holenstudio_doctorpassword_util_PasswordUtil_nativeEncryptString
        (JNIEnv *env, jobject obj, jstring str) {
    char *password = (*env)->GetStringUTFChars(env, str, 0);
    jint version = (*env)->GetVersion(env);
    return (*env)->NewStringUTF(env, "This just a test for Android Studio NDK JNI developer!");
}