package com.example.testslibrary;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SendTestsReport extends AsyncTask<String, String, Void> {

    @Override
    protected Void doInBackground(String... params) {
        String url = params[0];
        String report = params[1];

        sendReport(url,report);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.i("SendTestsReport","report sent");
        super.onPostExecute(aVoid);
    }

    public void sendReport(String url, String report) {
        HttpURLConnection c = null;
        String urlParameters  = null;
        try {
            urlParameters = "report=" + URLEncoder.encode(report, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("SendTestsReport","urlParameters: " + urlParameters);
        byte[] postData  = urlParameters.getBytes();
        int    postDataLength = postData.length;

        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);

            c.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            //c.setRequestProperty( "charset", "utf-8");
            c.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
           // c.setUseCaches( false );

            c.getOutputStream().write(postData);

            //c.connect();
            Reader in = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = in.read()) >= 0;)
                sb.append((char)ch);
            String response = sb.toString();
            Log.i("SendTestsReport","Response: " + response);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}