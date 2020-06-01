package com.example.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.movieapp.Database.Movie;
import com.example.movieapp.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private ArrayList<Movie> mMovies;
    private final MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    private RequestManager glide;
    private String baseUrl;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(ArrayList<Movie> mMovies, MovieAdapterOnClickHandler movieAdapterOnClickHandler, RequestManager glide, String baseUrl) {
        this.mMovies = mMovies;
        this.movieAdapterOnClickHandler = movieAdapterOnClickHandler;
        this.glide = glide;
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        ImageView imageView = view.findViewById(R.id.image_view);
        return new MovieAdapter.MovieAdapterViewHolder(view, imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        glide.load(baseUrl + mMovies.get(position).getPosterPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;

        public MovieAdapterViewHolder(@NonNull View itemView, ImageView imageView) {
            super(itemView);
            this.imageView = imageView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);
            movieAdapterOnClickHandler.onClick(movie);
        }
    }
}
