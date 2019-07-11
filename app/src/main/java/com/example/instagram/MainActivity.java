package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button signinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            usernameInput = findViewById(R.id.etUsername);
            passwordInput = findViewById(R.id.etPassword);
            loginBtn = findViewById(R.id.btnLogin);
            signinBtn = findViewById(R.id.btnSignup);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();
                    login(username, password);
                }
            });
            signinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();
                    signin(username, password);
                }
            });
        }

    }

    private void login(String username, String password) {
        //TODO
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "login successful");
                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "login failure");
                    e.printStackTrace();
                }
            }
        });
    }
    private void signin(String username, String password) {
        //TODO
       ParseUser user = new ParseUser();
       user.setUsername(username);
       user.setPassword(password);
       user.signUpInBackground(new SignUpCallback() {
           @Override
           public void done(ParseException e) {
               if(e==null) {
                   Log.d("SigninActivity", "signin successful");
                   final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                   startActivity(intent);
                   finish();
               } else {
                   Log.e("SigninActivity", "signin successful");
                   e.printStackTrace();
               }

           }
       });
    }
}
