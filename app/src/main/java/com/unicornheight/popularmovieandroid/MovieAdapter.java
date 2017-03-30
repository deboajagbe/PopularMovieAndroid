package com.unicornheight.popularmovieandroid;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by deboajagbe on 3/28/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {


    Context mContext;
    private String[] movieList;

    private final MovieAdapterOnClickHandler mClickHandler;


    public MovieAdapter(@NonNull Context context, MovieAdapterOnClickHandler clickHandler) {

        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForListItem = R.layout.movielist;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
}
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movies_posters;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            movies_posters = (ImageView) itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String clickData = movieList[adapterPosition];
            mClickHandler.onClick(clickData);
        }
    }
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String moviePoster = movieList[position];
        String[] moviesPath = moviePoster.split("###");
        String posterPath = moviesPath[1];
        if(posterPath != null) {
            String posterUrl = "http://image.tmdb.org/t/p/w500/" + posterPath;
            Picasso.with(mContext).load(posterUrl)
                    .into(holder.movies_posters);
        } else {

            holder.movies_posters.setImageResource(R.drawable.empty);
        }
    }
    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.length;
    }

    public void setAdapter(String[] s) {
        this.movieList = s;
        notifyDataSetChanged();
    }
    public interface MovieAdapterOnClickHandler {
        void onClick(String movie);
    }
}