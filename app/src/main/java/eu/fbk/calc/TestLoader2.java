package eu.fbk.calc;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class TestLoader2 {

    public String loadEspressoTestsDynamically(Context context)
    {
        copyAssets(context);

        try {
            // dir or archive where to find all the code to be dynamically called
            String fileName = "classes4.dex";
            File outFile = new File(context.getExternalFilesDir(null), fileName);

            String optimizedDir = context.getApplicationInfo().dataDir;

            Log.i("GaussCalc--","Check if file exists: " + outFile.exists() + " dir: " + optimizedDir);
            return outFile.getAbsolutePath();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void loadTestDynamically(Context context)
    {
        copyAssets(context);

        try {
            // dir or archive where to find all the code to be dynamically called
            //String fileName = "testLibrary.jar";
            String fileName = "classes.dex";
            File outFile = new File(context.getExternalFilesDir(null), fileName);

            String optimizedDir = context.getApplicationInfo().dataDir;

            Log.i("GaussCalc--","Check if jar exists: " + outFile.exists() + " dir: " + optimizedDir);
            DexClassLoader loader = new DexClassLoader(outFile.getAbsolutePath(),optimizedDir,"", ClassLoader.getSystemClassLoader());

            // class/method name and method signature
            String className = "com.example.mylibrary.TestClass2";
            String methodName = "runTest1";


            Class<?>[] methodSignature = {Context.class};

            // actual parameters
            Object[] actualParameters = {context};

            // load and constructor call
            Class remoteClass = loader.loadClass(className);
            Object remoteObject = remoteClass.newInstance();

            //Set Context
            Method setContextMethod = remoteClass.getMethod("setContext",methodSignature);
            setContextMethod.invoke(remoteObject,actualParameters);

            //Run the test
            Method remoteMethod = remoteClass.getMethod(methodName);

            Object result = remoteMethod.invoke(remoteObject);
            Log.i("GaussCalc--","Tested passed: " + (boolean)result);
        }
        catch(Exception e){
           e.printStackTrace();
        }
    }


    public static void copyAssets(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            if(!filename.endsWith(".apk") && !filename.endsWith(".dex"))
            {
                return;
            }
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(context.getExternalFilesDir(null), filename);
                Log.i("TestLoader2","copy file:  " + outFile.getAbsolutePath());
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
