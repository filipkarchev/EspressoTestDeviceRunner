package com.example.testslibrary;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import dalvik.system.DexClassLoader;

public class TestLoader {
    private Context context;

   public TestLoader(Context ctx)
    {
       this.context = ctx;
       Utils.openAppContext = ctx;
    }
    final String testClassesName = "TestClasses_hash1.dex";


    public void startTesting()
    {
        new DownloadFileFromURL().execute(Constants.fileUrl);
        new DownloadJsonFromURL().execute(Constants.jsonUrl);
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
                Log.i("TestLoader","url: " + url.toString());
                URLConnection conection = url.openConnection();
                conection.connect();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Output stream
                File outFile = new File(context.getFilesDir(), testClassesName);

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
                e.printStackTrace();
               // Log.e("TestLoader: ", e.getMessage());
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

            File file = new File(context.getFilesDir(), testClassesName);


            Log.i("TestLoader","File: " + file.getAbsolutePath() + " exists: " + file.length());
            if(file.exists())
            {
                prepareRunFile(file.getAbsolutePath());
            }
        }

    }

    JSONObject json;
    String filePath="";

    private void prepareRunFile(String filePath) {
        if(json!=null)
        {
            InstrmentationHelper.runTests(context,filePath,json);
            this.json = null;
            this.filePath = "";
        }else
        {
            this.filePath = filePath;
        }

    }

    private void prepareRunJson(JSONObject json) {
        Log.i("TestLoader","json is: " + json.toString());

        if(!filePath.equals(""))
        {
            InstrmentationHelper.runTests(context,filePath,json);
            this.json = null;
            this.filePath = "";
        }else
        {
            this.json = json;
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

    public class DownloadJsonFromURL extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String resultString = null;
            resultString = getJSON(params[0]);

            return resultString;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            Log.i("TestLoader","response: " + strings);

            try {
                prepareRunJson(new JSONObject(strings));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public String getJSON(String url) {
            HttpURLConnection c = null;
            try {
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.connect();
                int status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (Exception ex) {
                return ex.toString();
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        //disconnect error
                    }
                }
            }
            return null;
        }
    }




}
