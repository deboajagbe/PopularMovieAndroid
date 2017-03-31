package com.unicornheight.popularmovieandroid;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    static RecyclerView mRecycler;
    public static ProgressBar progressBar;
    public static MovieAdapter movieAdapter;
    static final String API_PARAM = "api_key";
    static final String TOKEN_API_KEY = BuildConfig.TOKEN_API_KEY;
    static String POPULAR_MOVIES = "http://api.themoviedb.org/3/movie/popular?";
    static String MOVIES_HIGHEST_RATED = "http://api.themoviedb.org/3/movie/top_rated?";
    static TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mError = (TextView) findViewById(R.id.tv_error_message);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_loading);
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this, this);
        mRecycler.setAdapter(movieAdapter);
        loadMovies();
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 800;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }


    //Default Load Most_Popular
    private void loadMovies(){
        Uri builtUri = Uri.parse(POPULAR_MOVIES).buildUpon()
                .appendQueryParameter(API_PARAM, TOKEN_API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(NetworkUtils.isNetworkAvailable(MainActivity.this)){
            showError();
            mError.setText(getString(R.string.internet_fail));
        } else {
            new MovieNetwork.GetMovies().execute(url);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            //Load Highest rated on click
            case R.id.sort_by_highest_rated_action:
                Uri builtUri = Uri.parse(MOVIES_HIGHEST_RATED).buildUpon()
                        .appendQueryParameter(API_PARAM, TOKEN_API_KEY)
                        .build();
                URL url = null;
                try {
                    url = new URL(builtUri.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if(NetworkUtils.isNetworkAvailable(MainActivity.this)){
                    showError();
                    mError.setText(getString(R.string.internet_fail));
                } else {
                    new MovieNetwork.GetMovies().execute(url);
                }
                break;
            case R.id.sort_by_popular_action:
                    loadMovies();
                break;
            default:
                loadMovies();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void showError(){
        progressBar.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.INVISIBLE);
    }
    public static void showData(){
        progressBar.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String movie) {
        Intent intent = new Intent(getApplicationContext(), DetailPage.class);
        intent.putExtra(Intent.EXTRA_TEXT, movie);
        startActivity(intent);
    }
}

