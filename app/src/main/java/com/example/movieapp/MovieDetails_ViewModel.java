package com.example.movieapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.Database.Favorite;

public class MovieDetails_ViewModel extends ViewModel {

    final private int mFavoriteId;

    MovieDetails_ViewModel(int favoriteId) {
        mFavoriteId = favoriteId;
    }

    public LiveData<Favorite> getFavorite(){
        return MainActivity.myAppDatabase.myDao().getFavorite(mFavoriteId);
    }
}
