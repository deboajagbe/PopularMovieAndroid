package com.unicornheight.popularmovieandroid;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.net.URL;

/**
 * Created by deboajagbe on 3/28/17.
 */

public class MovieNetwork extends MainActivity{

    public static class GetMovies extends AsyncTask<URL, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(URL... params) {
            String[] movieJsonData;
            URL FETCH_MOVIES = params[0];
            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(FETCH_MOVIES);
                movieJsonData = MovieJsonHandler
                        .getMovieDataFromJson(this, jsonResponse);
                return movieJsonData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String[] s) {
            progressBar.setVisibility(View.INVISIBLE);
            if(s != null && !s.equals("")){
                showData();
                movieAdapter.setAdapter(s);
            }
            else{
                showError();
            }
        }
    }

}
