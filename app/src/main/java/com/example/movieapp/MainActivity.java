package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.movieapp.APIholder.JsonPlaceholderApi;
import com.example.movieapp.Adapters.MovieAdapter;
import com.example.movieapp.Database.Favorite;
import com.example.movieapp.Database.JsonResponse;
import com.example.movieapp.Database.Movie;
import com.example.movieapp.Database.MyAppDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private String apiKey;
    private String baseUrl;
    private String url;
    private boolean popular = true;
    private boolean toprated = true;
    private MovieAdapter.MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    public static MyAppDatabase myAppDatabase;
    private RequestManager requestManager;
    private Retrofit retrofit;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private int grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        grid = getResources().getInteger(R.integer.movie_list_grid_size);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, grid);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapterOnClickHandler = this;
        apiKey = "6043714b75ca9810332af52e4998b9fc";
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "favoritedb").allowMainThreadQueries().build();
        if (!popular){
            baseUrl = getString(R.string.top_rated_base_url);
        }
        else {
            baseUrl = getString(R.string.popular_base_url);
        }

        requestManager = Glide.with(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        popular = true;
        retrofitFunction(popular);
    }

    public void retrofitFunction(Boolean popular){
        Call<JsonResponse> call;
        if (popular){
            call = jsonPlaceholderApi.getPopular(apiKey);
        }
        else {
            call = jsonPlaceholderApi.getTopRated(apiKey);
        }

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful()){
                    JsonResponse jsonResponse = response.body();
                    ArrayList<Movie> movies = jsonResponse.getResults();
                    MovieAdapter movieAdapter = new MovieAdapter(movies, movieAdapterOnClickHandler, requestManager, getString(R.string.poster_base_url));
                    recyclerView.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MovieAdapter movieAdapter = (MovieAdapter) recyclerView.getAdapter();
        assert movieAdapter != null;
    }

    public void ObserveMethod(){
        Main_ViewModel mainViewModel;
        mainViewModel = ViewModelProviders.of(this).get(Main_ViewModel.class);
        mainViewModel.getFavorites().observe(this, favorites -> {
            ArrayList<Movie> favoriteArrayList = new ArrayList<Movie>();
            assert favorites != null;
            for (Favorite fvt : favorites) {
                Movie movie = new Movie(fvt.getMovieId(), fvt.getMovieName(), fvt.getPosterUrl(), fvt.getVoteAverage(), fvt.getOverview(), fvt.getReleaseDate());
                favoriteArrayList.add(movie);
            }
            MovieAdapter movieAdapter = new MovieAdapter(favoriteArrayList, movieAdapterOnClickHandler, requestManager, getString(R.string.poster_base_url));
            recyclerView.setAdapter(movieAdapter);
        });
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetails_Activity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                popular = true;
                toprated = false;
                baseUrl = getString(R.string.popular_base_url);
                url = baseUrl + apiKey;
                retrofitFunction(popular);
                return true;
            case R.id.top_rated:
                popular = false;
                toprated = true;
                baseUrl = getString(R.string.top_rated_base_url);
                url = baseUrl + apiKey;
                retrofitFunction(popular);
                return true;
            case R.id.favorites:
                ObserveMethod();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
}