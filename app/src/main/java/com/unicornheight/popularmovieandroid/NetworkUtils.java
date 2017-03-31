package com.unicornheight.popularmovieandroid;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by deboajagbe on 3/28/17.
 */

class NetworkUtils extends Activity{

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info == null;

    }

    public static String getResponseFromHttpUrl(URL movieUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) movieUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}
