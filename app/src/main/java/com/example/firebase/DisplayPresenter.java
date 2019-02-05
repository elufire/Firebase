package com.example.firebase;

import android.content.Intent;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public class DisplayPresenter {
    DisplayContract displayContract;

    public DisplayPresenter(DisplayContract displayContract) {
        this.displayContract = displayContract;
    }

    public void checkUsers(FirebaseUser user, AccessToken accessToken){
        if(user != null){
            displayContract.statusFirebaseUser(user);
        }
        if(accessToken != null){
            displayContract.statusFacebookUser(accessToken);
        }
    }
}
