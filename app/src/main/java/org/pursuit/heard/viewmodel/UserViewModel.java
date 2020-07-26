package org.pursuit.heard.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private MutableLiveData<String> currentUser;

    public MutableLiveData<String> getCurrentUser() {
        return currentUser;
    }
}
