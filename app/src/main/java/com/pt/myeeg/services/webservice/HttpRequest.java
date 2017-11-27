package com.pt.myeeg.services.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jorge Zepeda Tinoco on 13/08/17.
 */

public class HttpRequest {

    private static final int CONNECT_TIMEOUT = 5000; // 5 seconds
    private static final int READ_TIMEOUT = 15000; // 15 seconds

    public static boolean isConnected(final Context context) {
        if (context == null)
            return false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null
                && networkInfo.isConnectedOrConnecting();
    }



    public static String sendGetRequest(String url)  {
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {

        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setUseCaches(true);

            InputStream is = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder total = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                total.append(line);
            }

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            return total.toString();
        } catch (IOException e) {
            //Log.e("IOException",e.getMessage());
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return null;
        } finally {
            Log.i("Connection","Get request");
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }


}