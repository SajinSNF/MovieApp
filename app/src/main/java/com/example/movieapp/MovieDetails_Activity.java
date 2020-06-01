package com.example.movieapp;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Database.Favorite;
import com.example.movieapp.Database.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails_Activity extends AppCompatActivity {

    @BindView(R.id.title)  TextView titleTextView;
    @BindView(R.id.rating)  TextView ratingTextView;
    @BindView(R.id.overview)  TextView overviewTextView;
    @BindView(R.id.release_date)  TextView releaseDateTextView;
    @BindView(R.id.image)  ImageView imageView;
    @BindView(R.id.recycler_view2) RecyclerView recyclerView2;
    private String posterUrl;
    @BindView(R.id.togglebutton) ToggleButton toggle;

    private int movieId;
    private Movie movie;
    private String posterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setHasFixedSize(true);
        String title;
        String rating;
        String overview;
        String releaseDate;
        Bundle extras = getIntent().getExtras();
        movie = extras.getParcelable("movie");
        if(extras == null) {
            posterUrl= null;
            title= null;
            rating= null;
            overview= null;
            releaseDate = null;
            movieId = 0;
        }
        else {
            posterPath = movie.getPosterPath();
            posterUrl = getString(R.string.poster_base_url) + posterPath;
            title = movie.getName();
            rating = movie.getVoteAverage().toString();
            overview = movie.getOverview();
            releaseDate = movie.getReleaseDate();
            movieId = movie.getId();
        }
        titleTextView.setText(title);
        ratingTextView.setText(rating);
        overviewTextView.setText(overview);
        releaseDateTextView.setText(releaseDate);

        Glide.with(this).load(posterUrl).into(imageView);
        String apiKey = "6043714b75ca9810332af52e4998b9fc";
        String url2 = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=" + apiKey;

        isFavorite();
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                addFavorite();
            } else {
                deleteFavorite();
            }
        });
        new FetchReviewTask().execute(url2);
    }

    public void addFavorite(){
        Favorite favorite = new Favorite();
        favorite.setMovieId(movieId);
        favorite.setMovieName(titleTextView.getText().toString());
        favorite.setOverview(overviewTextView.getText().toString());
        favorite.setPosterUrl(posterPath);
        favorite.setReleaseDate(releaseDateTextView.getText().toString());
        favorite.setVoteAverage(movie.getVoteAverage());
        MainActivity.myAppDatabase.myDao().addFavorite(favorite);
    }

    public void isFavorite(){
        MovieDetails_ViewModel_Factory movieDetailsViewModelFactory = new MovieDetails_ViewModel_Factory(movieId);
        final MovieDetails_ViewModel movieDetailsViewModel = ViewModelProviders.of(this, movieDetailsViewModelFactory).get(MovieDetails_ViewModel.class);
        movieDetailsViewModel.getFavorite().observe(this, new Observer<Favorite>() {
            @Override
            public void onChanged(@Nullable Favorite favorite) {
                movieDetailsViewModel.getFavorite().removeObserver(this);
                if (favorite == null){
                    toggle.setChecked(false);
                }
                else if ((favorite.getMovieId() == movieId) && !toggle.isChecked()) {
                    toggle.setChecked(true);
                }
            }
        });
    }

    public void deleteFavorite(){
        MainActivity.myAppDatabase.myDao().deleteFavorite(movieId);
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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



    public class FetchReviewTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String newJsonResponseVariable = "";
            URL url = null;
            try {
                Uri uri = Uri.parse(strings[0]);
                url = new URL(uri.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                assert url != null;
                newJsonResponseVariable = getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newJsonResponseVariable;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}