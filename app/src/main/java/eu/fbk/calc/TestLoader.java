package eu.fbk.calc;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

import dalvik.system.DexClassLoader;

public class TestLoader {
    private Context context;

   public TestLoader(Context ctx)
    {
       this.context = ctx;
    }
    final String testClassesName = "TestClasses.dex";

    public String loadEspressoTestsDynamically(Context context)
    {
        copyAssets(context);

        try {
            // dir or archive where to find all the code to be dynamically called
            String fileName = "TestFiles.dex";
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
                continue;
            }
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(context.getExternalFilesDir(null), filename);
                Log.i("TestLoader","copy file:  " + outFile.getAbsolutePath());
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

    public void downloadTests()
    {
       // String url = "https://www.dropbox.com/s/4v7gvtjiys6zc30/testFiles.dex?dl=0";
        String url ="https://www.dropbox.com/s/0pto0fy8hnkojgx/test.txt?dl=0";

        new DownloadFileFromURL().execute(url);

       // InstrmentationHelper.runTests(context, loadEspressoTestsDynamically(context));
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Output stream
                File outFile = new File(context.getExternalFilesDir(null), testClassesName);

                OutputStream output = new FileOutputStream(outFile);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("TestLoader: ", e.getMessage());
            }

            return f_url[0];
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            Log.i("TestLoader","File: " + testClassesName + " downloaded!");

            File file = new File(context.getExternalFilesDir(null), testClassesName);


            Log.i("TestLoader","File: " + file.getAbsolutePath() + " exists: " + file.length());
            if(file.exists())
            {
               // InstrmentationHelper.runTests(context,file.getAbsolutePath());
                Log.i("TestLoader","File content: " + readTextFile(file));
            }
        }

    }

    private String readTextFile(File file) {
        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        return text.toString();
    }
}
