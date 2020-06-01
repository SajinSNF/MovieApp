package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetails_ViewModel_Factory extends ViewModelProvider.NewInstanceFactory {

    final private int mFavoriteId;

    MovieDetails_ViewModel_Factory(int favoriteId) {
        mFavoriteId = favoriteId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetails_ViewModel(mFavoriteId);
    }
}
