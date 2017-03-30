package com.unicornheight.popularmovieandroid;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

/**
 * Created by deboajagbe on 3/28/17.
 */

class MovieJsonHandler {

    static String[] movies;

    public static String[] getMovieDataFromJson(MovieNetwork.GetMovies context, String movieJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MOVIE_LIST = "results";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date"; //release_date
        final String MOVIE_VOTES = "vote_average";
        final String OWM_MESSAGE_CODE = "cod";

        String title;
        String overview;
        String movie_votes;
        String release_date;
        String poster;



        JSONObject movieJson = new JSONObject(movieJsonStr);

        if (movieJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);


        movies = new String[movieArray.length()];
        for(int i = 0; i < movieArray.length(); i++) {

            // Get the JSON object representing the movie
            JSONObject movieData = movieArray.getJSONObject(i);

            title = movieData.getString(MOVIE_TITLE);
            poster = movieData.getString(MOVIE_POSTER);
            overview = movieData.getString(MOVIE_OVERVIEW);
            release_date = movieData.getString(MOVIE_RELEASE_DATE);
            movie_votes = movieData.getString(MOVIE_VOTES);

            movies[i] = title +  "###" + poster + "###" + overview + "###" + release_date + "###" + movie_votes ;
            Log.d("Main", movies.toString());

        }
        return movies;
    }

}
