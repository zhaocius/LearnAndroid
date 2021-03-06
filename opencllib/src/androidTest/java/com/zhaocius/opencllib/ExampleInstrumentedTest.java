package com.zhaocius.opencllib;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "OpenCLLTest";
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    String sdcardFolder = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);


    @Test
    public void useAppContext()throws Exception {
        // Context of the app under test.
        Log.d(TAG, "useAppContext: start");
        OpenCLManager openCLManager = new OpenCLManager();
        openCLManager.openCLTest();
    }
}