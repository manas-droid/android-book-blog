package com.example.bookblog.Graphql.Books;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.Callbacks.GetBookResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Future;

import apolloSchema.GetAllPostResultsQuery;

public class GetBooks {
    private static final String TAG = "GetBooks";
    ApolloClient client;

    public GetBooks(){
        client = ApolloInstance.getInstance();
    }

    public void getAllPosts(GetBookResponse getBookResponse){
      ApolloQueryCall<GetAllPostResultsQuery.Data> allPosts = client.query(GetAllPostResultsQuery.builder().build());
      allPosts.enqueue(new ApolloCall.Callback<GetAllPostResultsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetAllPostResultsQuery.Data> response) {
               if(!response.hasErrors()) {
                   getBookResponse.setResponse(response.getData().getAllPosts());
               }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onFailure: ",e);
            }
      });

    }

}
