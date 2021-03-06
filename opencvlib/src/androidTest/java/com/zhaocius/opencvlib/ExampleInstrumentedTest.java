package com.zhaocius.opencvlib;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "OpenCVTest";
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    String sdcardFolder = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);


    @Test
    public void useAppContext()throws Exception {
        // Context of the app under test.
        OpenCVManager openCVManager = new OpenCVManager();
        Log.d(TAG, "useAppContext: "+openCVManager.stringFromJNI());

        String jpgName = new File(sdcardFolder + "test.jpg").getAbsolutePath();
        Bitmap bitmap = BitmapFactory.decodeFile(jpgName);
        Log.d(TAG, "jpgName: " + jpgName+", w = "+bitmap.getWidth()+", h = "+bitmap.getHeight());
        Log.d(TAG, "colorSpace: " + bitmap.getColorSpace());

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] piexls = new int[w*h];
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(buffer);
        byte[] gray= openCVManager.Bitmap2Grey(buffer.array(),w,h);

        File rawFile = new File(sdcardFolder + "test.raw");
        OutputStream os = new FileOutputStream(rawFile);
        os.write(gray);
        os.close();
    }
}