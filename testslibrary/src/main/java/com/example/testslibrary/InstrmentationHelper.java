package com.example.testslibrary;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class InstrmentationHelper {

    public static void runTests(Context context, String testClassesPath, JSONObject json) {
        //Load the dynamic code here
        //TestLoader testLoader = new TestLoader(context);
        //String  espressoClass = testLoader.loadEspressoTestsDynamically(context);

        // Log.i("GaussCalc","path is: " + espressoClass);
        //Get all available instrumentations
        final String packageName = context.getPackageName();
        final List<InstrumentationInfo> list =
                context.getPackageManager().queryInstrumentation(packageName, PackageManager.GET_META_DATA);

        Log.i("GaussCalc","Instrumentations size: " + list.size());
        if (list.isEmpty()) {
            Toast.makeText(context, "Cannot find instrumentation for " + packageName,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //We assume there is only one instrumentation
        final InstrumentationInfo instrumentationInfo = list.get(0);

        Log.i("GaussCalc", "data pack: " + instrumentationInfo.packageName + " name: " + instrumentationInfo.name);
        final ComponentName componentName =
                new ComponentName(instrumentationInfo.packageName,
                        instrumentationInfo.name);


        //In the bundle send what is your loader and send which test class#method you want to start
        Bundle arguments = new Bundle();
        arguments.putString("loaderPath", testClassesPath);
        JSONArray testArr=null;
        try {
            testArr = json.getJSONArray("testClasses");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Add all tests classes for that configuration to be executed
        String testsStr = "";
        if(testArr!=null && testArr.length()>0)
        {
            for(int i=0;i<testArr.length();i++)
            {
                try {
                    Log.i("TestHelper","class added: " + testArr.getString(i));
                    testsStr+= "," + testArr.getString(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!testsStr.equals("")) {
            arguments.putString("class", testsStr.substring(1));
        }

        //arguments.putString("class", "eu.fbk.calc.GaussCalcTest#gaussCalcTest");


        //Start the instrumentation. THE RESULT of the test will be available at TestListener class
        if (!context.startInstrumentation(componentName, null, arguments)) {
            Toast.makeText(context, "Cannot run instrumentation for " + packageName,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
