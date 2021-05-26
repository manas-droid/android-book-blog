package com.example.bookblog.Graphql.Likes;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloMutationCall;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;

import org.jetbrains.annotations.NotNull;

import apolloSchema.AddPostLikeMutation;

public class PostLikes {
    private ApolloClient client;
    private static final String TAG = "PostLikes";
    public  PostLikes(AppCompatActivity activity){
        new ApolloInstance(activity);
        client = ApolloInstance.getInstance();
    }


    public void addLikeToPost(int postId){
        ApolloMutationCall<AddPostLikeMutation.Data> addLike = client.mutate(AddPostLikeMutation.builder().postId(postId).build());

        Log.d(TAG, "addLikeToPost: here");
        addLike.enqueue(new ApolloCall.Callback<AddPostLikeMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response response) {
                Log.d(TAG, "onResponse: here");
                if(!response.hasErrors()){
                    Log.d(TAG, "onResponse: "+response.getData());
                }
                else{
                    Log.d(TAG, "onResponse: "+response.getErrors().get(0));
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onFailure: ",e);
            }
        });
    }
}
