package com.unicornheight.popularmovieandroid;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by deboajagbe on 3/28/17.
 */

class NetworkUtils extends Activity{

    private static final String API_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
    static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    static final String SORT_PARAM = "sort_by";
    static final String API_PARAM = "api_key";
    static final String TOKEN_API_KEY = BuildConfig.TOKEN_API_KEY;
    public static final String HIGHEST_RATED = "vote_average.desc"; // Sort By Highest Rated.....this is implement in the onOptionMenuItemSelected
    public static final String MOST_POPULAR = "popularity.desc"; //Sort By Popular.....


    //Append the url
    public static URL buildMovieUrl(String sort_order) {
        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, sort_order)
                .appendQueryParameter(API_PARAM, TOKEN_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

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
