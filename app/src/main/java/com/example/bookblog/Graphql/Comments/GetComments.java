package com.example.bookblog.Graphql.Comments;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.Callbacks.GetCommentsResponse;

import org.jetbrains.annotations.NotNull;

import apolloSchema.GetAllCommentResultQuery;

public class GetComments {
    ApolloClient client;
    private static final String TAG = "GetComments";
    private int postId;
    public  GetComments(int postId){
        client = ApolloInstance.getInstance();
        this.postId  = postId;
    }

    public void getCommentForAPost(GetCommentsResponse getCommentsResponse){
         ApolloQueryCall<GetAllCommentResultQuery.Data> comments = client.query(GetAllCommentResultQuery.builder().postId(postId).build());
         comments.enqueue(new ApolloCall.Callback<GetAllCommentResultQuery.Data>() {
             @Override
             public void onResponse(@NotNull Response<GetAllCommentResultQuery.Data> response) {
                 if(!response.hasErrors())
                     getCommentsResponse.setCommentList(response.getData().getComments());
                 else
                     Log.d(TAG, "onResponse: "+response.getErrors());
             }
             @Override
             public void onFailure(@NotNull ApolloException e) {
                 Log.e(TAG, "onFailure: ", e);
             }
         });

    }

}


