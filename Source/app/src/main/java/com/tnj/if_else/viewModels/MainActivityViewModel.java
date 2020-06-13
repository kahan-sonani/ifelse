package com.tnj.if_else.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.tnj.if_else.firebaseRepository.AuthRepository;

public class MainActivityViewModel extends AndroidViewModel {

    public LiveData<FirebaseUser> userLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        userLiveData = AuthRepository.getInstance().getFirebaseUser();
    }
}
