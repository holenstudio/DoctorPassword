LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := passwordEncryption
LOCAL_SRC_FILES := passwordEncryption.c

include $(BUILD_SHARED_LIBRARY)
