package com.example.bookblog.Graphql.You;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.Callbacks.GetAllYourPost;

import org.jetbrains.annotations.NotNull;

import apolloSchema.AllYourPostQuery;

public class GetYourPosts {
    ApolloClient client;
    private static final String TAG = "GetYourPosts";
    public GetYourPosts(){
        client = ApolloInstance.getInstance();
    }



    public void getAllPosts(GetAllYourPost getAllYourPost){
        client.query(AllYourPostQuery.builder().build()).enqueue(new ApolloCall.Callback<AllYourPostQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<AllYourPostQuery.Data> response) {
                if(!response.hasErrors())
                    getAllYourPost.setAllYourPost(response.getData().getYourPosts());
                else
                    Log.d(TAG, "onResponse: "+response.getErrors().get(0));
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onFailure: ",e);
            }
        });
    }
}
