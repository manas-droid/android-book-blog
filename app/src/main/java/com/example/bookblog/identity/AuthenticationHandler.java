package com.example.bookblog.identity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.Callback;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.example.bookblog.HomeActivity;
import com.example.bookblog.MainActivity;
import com.example.bookblog.UtilUser.User;
import com.example.bookblog.UtilUser.UserProfile;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class AuthenticationHandler {
    private final SecureCredentialsManager credentialsManager;
    public Credentials credentials;
    private final AppCompatActivity activity;
    private final Auth0 auth0;
    private final AuthenticationAPIClient authenticationAPIClient;
    private static final String TAG = "AuthenticationHandler";
    public AuthenticationHandler(AppCompatActivity activity){
         this.activity = activity;
        this.auth0 = new Auth0(this.activity);
        authenticationAPIClient = new AuthenticationAPIClient(this.auth0);
        credentialsManager = new SecureCredentialsManager(activity, authenticationAPIClient, new SharedPreferencesStorage(activity));
    }


    public void startAuthenticationProcess() {
        WebAuthProvider.login(this.auth0).withScheme("book-blog")
                .withScope("openid profile email offline_access")
                .withAudience("https://book-blog-api")
                .start(this.activity, new Callback<Credentials, AuthenticationException>() {
                    @Override
                    public void onSuccess(Credentials credential) {
                        credentialsManager.saveCredentials(credential);
                        credentials = credential;
                        Log.d(TAG, "onSuccess: here");
                        JWT jwt = new JWT(credential.getIdToken());
                        Map<String,Claim> map  = jwt.getClaims();
                        Log.d(TAG, "onSuccess: "+map);
                        Log.d(TAG, "onSuccess: "+credential.getScope());
                        User user = new User(
                                jwt.getClaim("email").asString(),
                                jwt.getClaim("nickname").asString(),
                                jwt.getClaim("picture").asString(),
                                 credential.getScope(),
                                jwt.getClaim("sub").asString()
                                );
                        UserProfile.saveUserInfo(activity, user);

                        activity.startActivity(new Intent(activity, HomeActivity.class));
                    }
                    @Override
                    public void onFailure(@NotNull AuthenticationException e) {
                        new AlertDialog.Builder(activity)
                                .setTitle("Authentication Error")
                                .setMessage(e.getMessage())
                                .show();
                    }
                });
    }

    public void logout(){
        credentialsManager.clearCredentials();
        UserProfile.deleteUserInfo(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String message = "See you soon!";
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, MainActivity.class));
            }
        });
    }

    void refreshCredentials() {
        credentialsManager.getCredentials(new Callback<Credentials, CredentialsManagerException>() {
            @Override
            public void onSuccess(Credentials credentials) {
                credentialsManager.saveCredentials(credentials);
                AuthenticationHandler.this.credentials = credentials;
            }

            @Override
            public void onFailure(@NotNull CredentialsManagerException e) {
                AuthenticationHandler.this.startAuthenticationProcess();
            }
        });
    }

    public String getAccessToken(){
        return credentials.getAccessToken();
    }

    public boolean hasValidCredentials() {
        boolean hasValidCredentials = this.credentialsManager.hasValidCredentials();
        if (hasValidCredentials && credentials == null) {
            refreshCredentials();
        }

        return hasValidCredentials;
    }
}
