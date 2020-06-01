package com.example.movieapp.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.example.movieapp.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewAdapterViewHolder> {
    private ArrayList<String> mPosterUrls;
    private ArrayList<String> mTitles;
    private ArrayList<String> mVoteAverages;
    private ArrayList<String> mOverviews;
    private ArrayList<String> mReleaseDates;
    private ArrayList<String> mMovieIds;
    private final RecyclerViewAdapterOnClickHandler mClickHandler;
    private RequestManager glide;

    public interface RecyclerViewAdapterOnClickHandler {
        void onClick(String posterUrl, String title, String voteAverage, String overview, String releaseDate, String movieId);
    }

    RecyclerViewAdapter(RequestManager glide, ArrayList<String> posterUrls, ArrayList<String> titles, ArrayList<String> voteAverages, ArrayList<String> overviews, ArrayList<String> releaseDates, ArrayList<String> movieIds, RecyclerViewAdapterOnClickHandler clickHandler) {
        mPosterUrls = posterUrls;
        mTitles = titles;
        mVoteAverages = voteAverages;
        mOverviews = overviews;
        mReleaseDates = releaseDates;
        mMovieIds = movieIds;
        mClickHandler = clickHandler;
        this.glide = glide;
    }

    public class RecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;

        RecyclerViewAdapterViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String posterUrl = mPosterUrls.get(adapterPosition);
            String title = mTitles.get(adapterPosition);
            String voteAverage = mVoteAverages.get(adapterPosition);
            String overview = mOverviews.get(adapterPosition);
            String releaseDate = mReleaseDates.get(adapterPosition);
            String movieId = mMovieIds.get(adapterPosition);
            mClickHandler.onClick(posterUrl, title, voteAverage, overview, releaseDate, movieId);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        return new RecyclerViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterViewHolder holder, int position) {

        glide.load(mPosterUrls.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPosterUrls.size();
    }
}
