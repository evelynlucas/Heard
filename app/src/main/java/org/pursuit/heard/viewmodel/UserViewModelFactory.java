package org.pursuit.heard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.pursuit.heard.database.ProfileDatabase;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private ProfileDatabase database;
    private Application application;

    public UserViewModelFactory(ProfileDatabase database, Application application) {
        this.database = database;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(application, database);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
