package com.example.bookblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.example.bookblog.Graphql.Books.GetBooks;
import com.example.bookblog.Graphql.Callbacks.GetBookResponse;
import com.example.bookblog.Graphql.Likes.GetLikes;
import com.example.bookblog.identity.AuthenticationHandler;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import apolloSchema.AddPostLikeMutation;
import apolloSchema.GetAllPostResultsQuery;

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