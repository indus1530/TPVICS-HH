//
// Created by ali.azaz on 3/18/2021.
//

#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL
Java_edu_aku_hassannaqvi_tpvics_1hh_utils_shared_Keys_apiKey(JNIEnv *env, jobject thiz) {
    std::string api_key = "asSa%s|n'$ crEed";
    return env->NewStringUTF(api_key.c_str());
}