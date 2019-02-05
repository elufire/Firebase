package com.example.firebase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;
import java.net.URL;

public class FirebaseInputActivity extends AppCompatActivity implements FirebaseInputContract{
    FirebaseAuth firebaseAuth;
    EditText etEmail;
    EditText etName;
    EditText etPhoto;
    FirebaseInputPresenter firebaseInputPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_input);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPhoto = findViewById(R.id.etPhoto);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseInputPresenter = new FirebaseInputPresenter(this);
    }



    public void onClick(View view) {
            Intent intent = getIntent();
            FirebaseUser user = intent.getParcelableExtra("firebase_user");
            firebaseInputPresenter.getUpdatedUser(user, etEmail.getText().toString(), etName.getText().toString(),
                    etPhoto.getText().toString());


    }

    @Override
    public void updatedFirebaseUser(FirebaseUser user) {
        Intent intent = new Intent(this, FirebaseDisplayActivity.class);
        intent.putExtra("firebase_display", user);
        intent.putExtra("activity", "act_input");
        startActivity(intent);
    }
}
