#include <jni.h>
#include <string>
#include<opencv2/opencv.hpp>
#include <opencv2/imgproc/types_c.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_zhaocius_opencvlib_OpenCVManager_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jbyteArray

JNICALL
Java_com_zhaocius_opencvlib_OpenCVManager_Bitmap2Grey(
        JNIEnv *env,
        jobject /* this */,jbyteArray jbufArray,jint w,jint h) {
    jbyte *jbuf;
    jsize length;
    jboolean ptfalse = false;
    jbuf = env->GetByteArrayElements(jbufArray, &ptfalse);
    length = env->GetArrayLength(jbufArray);
    if(jbuf == NULL){
        return 0;
    }

    char* cbuf = (char*)malloc(sizeof(char)*length);
    memcpy(cbuf,jbuf,length);
    env->ReleaseByteArrayElements(jbufArray, jbuf, 0);


    cv::Mat imgData(h, w, CV_8UC4, cbuf);
    cv::Mat dstimgData;

    // 注意，Android的Bitmap是ARGB四通道,而不是RGB三通道
    cvtColor(imgData,dstimgData,CV_RGB2GRAY);

    int size=w * h;
    jbyteArray result = env->NewByteArray(size);
    env->SetByteArrayRegion(result, 0, size, (jbyte*)imgData.data);
    return result;
}
