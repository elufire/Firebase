package com.example.firebase;

import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginPresenter {
    UserLoginContract userLoginContract;


    public UserLoginPresenter(UserLoginContract userLoginContract) {
        this.userLoginContract = userLoginContract;
    }

    public void upDateFirebaseUser(FirebaseUser user){
        if(user != null){
            userLoginContract.updateUI(user);
        }
        else{
            userLoginContract.badUser();
        }

    }

    public void updateFacebook(LoginResult loginResult){

        if(loginResult != null){
            userLoginContract.updateFBUI(loginResult);
        }

    }
}
