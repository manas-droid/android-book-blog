package com.example.bookblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookblog.identity.AuthenticationHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button login;
    AuthenticationHandler authenticationHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        authenticationHandler = new AuthenticationHandler(this);
        if(authenticationHandler.hasValidCredentials()){
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        authenticationHandler.startAuthenticationProcess();
    }
}