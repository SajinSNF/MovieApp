package com.example.movieapp;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.movieapp.Database.Favorite;
import com.example.movieapp.Database.Movie;

import java.util.List;

public class Main_ViewModel extends AndroidViewModel {
    private LiveData<List<Favorite>> mFavoritesList;


    public Main_ViewModel(Application application) {
        super(application);
        mFavoritesList = MainActivity.myAppDatabase.myDao().getFavorites();

    }

    LiveData<List<Favorite>> getFavorites(){

        return mFavoritesList;
    }
}
