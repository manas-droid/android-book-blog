package com.example.bookblog.Graphql;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloClient;
import com.example.bookblog.identity.AuthenticationHandler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApolloInstance {
    public static final  String BASE_URL ="http://13.59.62.47/graphql";
    private static ApolloClient apolloClient = null;
    private AppCompatActivity activity = null;

    private  static String token = null;
    private static final String TAG = "ApolloInstance";

    public static void setToken(AppCompatActivity activity){
        if(token == null) {
            AuthenticationHandler authenticationHandler = new AuthenticationHandler(activity);
            token = (authenticationHandler.hasValidCredentials()) ? authenticationHandler.getAccessToken() : "";
        }
    }

    public static ApolloClient getInstance(){
    if(apolloClient == null) {
        apolloClient = ApolloClient
                .builder()
                .okHttpClient(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    }
                }).build())
                .serverUrl(BASE_URL)
                .build();
    }
    return apolloClient;
    }

}
