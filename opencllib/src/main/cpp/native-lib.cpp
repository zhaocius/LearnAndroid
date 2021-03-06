#include <jni.h>
#include <android/log.h>
#include "local/tool.h"
#include "CL/cl.h"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "zhaocius", __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_zhaocius_opencllib_OpenCLManager_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zhaocius_opencllib_OpenCLManager_openCLTest(JNIEnv *env, jobject thiz) {
    cl_int status;
    /**Step 1: Getting platforms and choose an available one(first).*/
    cl_platform_id platform;
    getPlatform(platform);
    /**Step 2:Query the platform and choose the first GPU device if has one.*/
    cl_device_id *devices = getCl_device_id(platform);
    /**Step 3: Create context.*/
    cl_context context = clCreateContext(NULL, 1, devices, NULL, NULL, NULL);
    /**Step 4: Creating command queue associate with the context.*/
    cl_command_queue commandQueue =
            clCreateCommandQueue(context, devices[0], 0, &status);
    /**Step 5: Create program object */
    const char *filename = "/sdcard/_TestCases/HelloWorld_Kernel.cl";
    string sourceStr;
    status = convertToString(filename, sourceStr);
    const char *source = sourceStr.c_str();
    size_t sourceSize[] = {strlen(source)};
    cl_program program =
            clCreateProgramWithSource(context, 1, &source, sourceSize, NULL);
    status = clBuildProgram(program, 0, 0, 0, 0, 0);
    /**Step 7: Initial input,output for the host and create memory objects for the
     * kernel*/
    const int NUM = 100000;
    float *input = new float[NUM];
    for (int i = 0; i < NUM; i++)
        input[i] = i;
    float *output = new float[NUM];
    cl_mem inputBuffer =
            clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                           (NUM) * sizeof(float), (void *)input, NULL);
    cl_mem outputBuffer = clCreateBuffer(context, CL_MEM_WRITE_ONLY,
                                         NUM * sizeof(float), NULL, NULL);
    /**Step 8: Create kernel object */
    cl_kernel kernel = clCreateKernel(program, "helloworld", NULL);
    auto time0 = time();
    /**Step 9: Sets Kernel arguments.*/
    status = clSetKernelArg(kernel, 0, sizeof(cl_mem), &inputBuffer);
    status = clSetKernelArg(kernel, 1, sizeof(cl_mem), &outputBuffer);
    /**Step 10: Running the kernel.*/
    size_t global_work_size[1] = {NUM};
    cl_event enentPoint;
    status = clEnqueueNDRangeKernel(commandQueue, kernel, 1, NULL,
                                    global_work_size, NULL, 0, NULL, &enentPoint);
    SAMPLE_CHECK_ERRORS(status);
    clWaitForEvents(1, &enentPoint); /// wait
    clReleaseEvent(enentPoint);
    /**Step 11: Read the cout put back to host memory.*/
    status = clEnqueueReadBuffer(commandQueue, outputBuffer, CL_TRUE, 0,
                                 NUM * sizeof(float), output, 0, NULL, NULL);
    auto time1 = time();
    auto time_use = time_diff(time0, time1);
    LOGD(" time cost: %lf",time_use);
    for (int i = 0; i < NUM; i++)
        LOGD("output %f",output[i]);

    /**Step 12: Clean the resources.*/
    status = clReleaseKernel(kernel);         //*Release kernel.
    status = clReleaseProgram(program);       // Release the program object.
    status = clReleaseMemObject(inputBuffer); // Release mem object.
    status = clReleaseMemObject(outputBuffer);
    status = clReleaseCommandQueue(commandQueue); // Release  Command queue.
    status = clReleaseContext(context);           // Release context.
    SAMPLE_CHECK_ERRORS(status);

    if (output != NULL) {
        free(output);
        output = NULL;
    }

    if (devices != NULL) {
        free(devices);
        devices = NULL;
    }
    return 0;
}