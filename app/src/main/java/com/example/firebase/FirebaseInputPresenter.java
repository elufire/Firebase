package com.example.firebase;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;

public class FirebaseInputPresenter {
    FirebaseInputContract firebaseInputContract;

    public FirebaseInputPresenter(FirebaseInputContract firebaseInputContract) {
        this.firebaseInputContract = firebaseInputContract;
    }

    public void getUpdatedUser(FirebaseUser user,String email, String name, String photo){
        if(user!= null){
            if(!email.isEmpty()){
                user.updateEmail(email);
            }
            if(!photo.isEmpty()){
                Log.e("TAG", "GetuserUpdated photo url: " + photo);
                Uri uri = Uri.parse(photo);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri).build();
                user.updateProfile(profileUpdates);
                Log.e("TAG", "After Update: " + user.getPhotoUrl());
            }
            if(!name.isEmpty()){
                System.out.println("Input Presenter:" + name);
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();
                user.updateProfile(profileChangeRequest);
            }
        }
        firebaseInputContract.updatedFirebaseUser(user);

    }
}
