#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_holenstudio_doctorpassword_util_PasswordUtil_nativeEncryptString
  (JNIEnv *env, jobject obj){
     return (*env)->NewStringUTF(env,"This just a test for Android Studio NDK JNI developer!");
  }