package com.example.bookblog.Graphql.BookMarks;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloMutationCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;

import org.jetbrains.annotations.NotNull;

import apolloSchema.AddBookMarkPostResultMutation;
import apolloSchema.AddPostLikeMutation;

public class PostBookMark {
    ApolloClient client;
    private static final String TAG = "PostBookMark";
    public  PostBookMark(){
        client = ApolloInstance.getInstance();
    }

    public void postBookMarkForAPost(int postId){
        ApolloMutationCall<AddBookMarkPostResultMutation.Data> apolloMutationCall = client
                .mutate(AddBookMarkPostResultMutation.builder().postId(postId).build());
        apolloMutationCall.enqueue(new ApolloCall.Callback<AddBookMarkPostResultMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<AddBookMarkPostResultMutation.Data> response) {
                if(!response.hasErrors())
                    Log.d(TAG, "onResponse: "+response.getData());
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
