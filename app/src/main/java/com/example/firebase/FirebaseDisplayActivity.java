package com.example.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FirebaseDisplayActivity extends AppCompatActivity implements DisplayContract{
    FirebaseAuth firebaseAuth;
    TextView tvName;
    TextView tvEmail;
    TextView tvFacebookUser;
    ImageView ivView;
    DisplayPresenter displayPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_display);
        ivView = findViewById(R.id.ivProfilePic);
        tvEmail = findViewById(R.id.tvEmail);
        tvName = findViewById(R.id.tvName);
        tvFacebookUser = findViewById(R.id.tvFacebookUser);
        tvFacebookUser.setVisibility(TextView.GONE);
        tvEmail.setVisibility(TextView.GONE);
        tvName.setVisibility(TextView.GONE);
        tvFacebookUser.setVisibility(TextView.GONE);
        ivView.setVisibility(ImageView.GONE);
        Intent intent = getIntent();
        FirebaseUser user = intent.getParcelableExtra("firebase_display");
        AccessToken accessToken = intent.getParcelableExtra("facebook");
        displayPresenter = new DisplayPresenter(this);
        displayPresenter.checkUsers(user, accessToken);

//        Intent intent = getIntent();
//        String activity = intent.getStringExtra("activity");
//        if(activity.equals("facebook")){
//
//        }

    }



        @Override
    public void statusFirebaseUser(FirebaseUser user) {
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            ivView.setVisibility(ImageView.VISIBLE);
            tvName.setVisibility(TextView.VISIBLE);
            tvEmail.setVisibility(TextView.VISIBLE);
            GlideApp.with(this).load(user.getPhotoUrl()).into(ivView);
            tvEmail.setText(user.getEmail());
            tvName.setText(user.getDisplayName());

    }

    @Override
    public void statusFacebookUser(AccessToken accessToken) {
        tvFacebookUser.setVisibility(TextView.VISIBLE);
        tvFacebookUser.setText("FaceBook info: "+ accessToken.getUserId() + " " + accessToken.getApplicationId());
    }

}
