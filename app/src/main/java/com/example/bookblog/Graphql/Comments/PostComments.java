package com.example.bookblog.Graphql.Comments;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;

import org.jetbrains.annotations.NotNull;

import apolloSchema.AddPostCommentResultMutation;

public class PostComments {
    private static final String TAG = "PostComments";
    ApolloClient client;
    public PostComments(){
        client = ApolloInstance.getInstance();
    }

    public void postCommentForAPost(String comment , int postId){

        client.mutate(AddPostCommentResultMutation.builder().comment(comment)
                .parentId(null).postId(postId).build())
                .enqueue(new ApolloCall.Callback<AddPostCommentResultMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AddPostCommentResultMutation.Data> response) {
                        if(!response.hasErrors()){
                            Log.d(TAG, "onResponse: "+response.getData().addPostComment().ok());
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
