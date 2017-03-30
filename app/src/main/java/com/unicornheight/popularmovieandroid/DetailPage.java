package com.unicornheight.popularmovieandroid;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailPage extends AppCompatActivity {

    String Title, Poster, ReleaseDate, Votes, Overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        ActionBar actionBar = this.getSupportActionBar();

        TextView titleView = (TextView) findViewById(R.id.title);
        TextView dateView = (TextView) findViewById(R.id.releaseDate);
        TextView voteView = (TextView) findViewById(R.id.votes);
        ImageView posterView = (ImageView) findViewById(R.id.posterDetail);
        TextView overViewTextView = (TextView) findViewById(R.id.overviewText);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            Intent intentThatStartedThisActivity = getIntent();

            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

                String movies = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

                String[] movies_parts = movies.split("###");

                Title = movies_parts[0];
                Poster = movies_parts[1];
                Overview = movies_parts[2];
                ReleaseDate = movies_parts[3];
                Votes = movies_parts[4];

                if (Poster.equals("null") || Poster.equals(null) || Poster.equals("")) {
                    posterView.setImageResource(R.drawable.empty);
                } else {
                    Picasso.with(this)
                            .load("http://image.tmdb.org/t/p/w500/" + Poster)
                            .into(posterView);
                }

                titleView.setText(Title);
                dateView.setText(ReleaseDate);
                voteView.setText(Votes);
                overViewTextView.setText(Overview);
            }
        }
    }
}
