package com.unicornheight.popularmovieandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailPage extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.posterDetail)
    ImageView mPoster;
    @BindView(R.id.overviewText)
    TextView mOverview;
    @BindView(R.id.releaseDate)
    TextView mReleaseDate;
    @BindView(R.id.votes)
    TextView mVotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            Intent intentThatStartedThisActivity = getIntent();

            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

                String movies = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

                String[] movies_parts = movies.split("###");

                mTitle.setText(movies_parts[0].toString());
                mOverview.setText(movies_parts[2]);
                mReleaseDate.setText(movies_parts[3]);
                mVotes.setText(movies_parts[4]);

                if (movies_parts[1].equals("null") || movies_parts[1].equals(null) || movies_parts[1].equals("")) {
                    mPoster.setImageResource(R.drawable.empty);
                } else {
                    Picasso.with(this)
                            .load("http://image.tmdb.org/t/p/w500/" + movies_parts[1])
                            .into(mPoster);
                }

            }
        }
    }
}
