package com.example.maroy.services;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MaRoy on 4/18/2015.
 */
public class AsyncServiceTask extends AsyncTask<String, Void, String> {

    private AsyncServiceResponse delegate = null;//Call back interface
    private ProgressDialog progress;

    public AsyncServiceTask(AsyncServiceResponse asyncResponse) {
        delegate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected String doInBackground(String... urls) {

        InputStream is = null;
        // params comes from the execute() call: params[0] is the url.
        try {

            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();

            // Convert the InputStream into a string
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        delegate.onProcessComplete(result);
    }
}
