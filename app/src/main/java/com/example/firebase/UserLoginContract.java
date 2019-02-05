package com.example.firebase;

import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseUser;

public interface UserLoginContract {
    void updateUI(FirebaseUser user);

    void updateFBUI(LoginResult loginResult);

    void badUser();
}
