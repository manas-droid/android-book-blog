package com.example.bookblog.Graphql.Likes;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.Callbacks.GetLikeResponse;

import org.jetbrains.annotations.NotNull;

import apolloSchema.GetPostLikeResultQuery;

public class GetLikes {
    private ApolloClient client = null;
    private static final String TAG = "GET_LIKES";

    public GetLikes(AppCompatActivity activity){
        new ApolloInstance(activity);
        client = ApolloInstance.getInstance();
    }

    public void getLikesPerPost(GetLikeResponse getLikeResponse, int postId){
       ApolloQueryCall<GetPostLikeResultQuery.Data> like = client.query(GetPostLikeResultQuery.builder().postId(postId).build());

       like.enqueue(new ApolloCall.Callback<GetPostLikeResultQuery.Data>() {
           @Override
           public void onResponse(@NotNull Response<GetPostLikeResultQuery.Data> response) {
               if(!response.hasErrors())
                   getLikeResponse.setLikeResponse(response.getData().getLikes());
               else
                   Log.d(TAG, "onResponse: "+response.getErrors());
           }

           @Override
           public void onFailure(@NotNull ApolloException e) {
               Log.e(TAG, "onFailure: ",e);
           }
       });
    }




}
