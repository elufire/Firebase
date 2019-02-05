package com.example.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

public class UserLoginActivity extends AppCompatActivity implements UserLoginContract{
    FirebaseAuth firebaseAuth;
    EditText etEmail;
    EditText etPassword;
    TextView tvLoinResult;
    CallbackManager callbackManager;
    LoginButton loginButton;
    UserLoginPresenter userLoginPresenter;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvLoinResult = findViewById(R.id.tvLoginResult);

        userLoginPresenter = new UserLoginPresenter(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App
                        userLoginPresenter.updateFacebook(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateFBUI(LoginResult loginResult){
        Intent intent = new Intent(this, FirebaseDisplayActivity.class);
        intent.putExtra("facebook", loginResult.getAccessToken());
        intent.putExtra("activity", "act_one");
        startActivity(intent);


    }

    public void updateUI(FirebaseUser user){
        Intent intent;
            intent = new Intent(this, FirebaseInputActivity.class);
            intent.putExtra("firebase_user", user);
            startActivity(intent);
            //tvLoinResult.setText("User " + user.getEmail() + " is logged in.");

    }

    public void badUser(){
        tvLoinResult.setText("User Does not exist, Please ReEnter");
        Toast.makeText(this,"Please enter a correct username and password!", Toast.LENGTH_SHORT).show();
        etEmail.setText("");
        etPassword.setText("");
    }

    public void onClick(View view) {
        String email = etEmail.getText() != null ? etEmail.getText().toString() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString() : "";
        switch (view.getId()){
            case R.id.btnSignIn:
                if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        userLoginPresenter.upDateFirebaseUser(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.getException());

                                        userLoginPresenter.upDateFirebaseUser(null);
                                    }

                                    // ...
                                }
                            });
                }
                else {
                    tvLoinResult.setText("User Does not exist, Please ReEnter");
                    etEmail.setText("");
                    etPassword.setText("");
                }

                break;
            case R.id.btnSignUp:
                if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        userLoginPresenter.upDateFirebaseUser(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());

                                        userLoginPresenter.upDateFirebaseUser(null);
                                    }

                                    // ...
                                }
                            });
                }else{
                    tvLoinResult.setText("User Does not exist");
                    etEmail.setText("");
                    etPassword.setText("");
                }
                break;
        }
    }
}
