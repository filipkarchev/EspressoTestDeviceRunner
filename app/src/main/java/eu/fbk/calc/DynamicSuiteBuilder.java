package eu.fbk.calc;

import android.util.Log;

import org.junit.runner.RunWith;

import dalvik.system.DexClassLoader;

@RunWith(DynamicSuite.class)
public class DynamicSuiteBuilder {
    public static Class[] suite() {
        Class arr[] = new Class[1];
        Log.i("DynamicSuiteBuilder","SUITE");
        //Generate class array here.
        String loaderPath = "/storage/emulated/0/Android/data/eu.fbk.calc/files/classes5.dex";
        Log.i("DynamicSuiteBuilder","parent loader path: " + loaderPath.getClass().getClassLoader());
        DexClassLoader loader = new DexClassLoader(loaderPath,"",null, loaderPath.getClass().getClassLoader());
        try {
            Class myClass = loader.loadClass("eu.fbk.calc.GaussCalcTest4");
            arr[0] = myClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return arr;
    }
}