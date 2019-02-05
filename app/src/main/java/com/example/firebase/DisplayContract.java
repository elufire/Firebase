package com.example.firebase;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public interface DisplayContract {

    void statusFirebaseUser(FirebaseUser user);

    void statusFacebookUser(AccessToken accessToken);

}
