package eu.fbk.calc;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class InstrmentationHelper {

    public static void runTests(Context context,String testClassesPath) {
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
        arguments.putString("class", "eu.fbk.calc.GaussCalcTest2");
        //arguments.putString("class", "eu.fbk.calc.GaussCalcTest#gaussCalcTest");


        //Start the instrumentation. THE RESULT of the test will be available at TestListener class
        if (!context.startInstrumentation(componentName, null, arguments)) {
            Toast.makeText(context, "Cannot run instrumentation for " + packageName,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
