package com.example.bookblog.Graphql.You;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.Callbacks.GetYourBookMarkResponse;

import org.jetbrains.annotations.NotNull;

import apolloSchema.GetYourBookMarksQuery;

public class GetYourBookMarks {
    ApolloClient client;
    private static final String TAG = "GetYourBookMarks";
    public GetYourBookMarks(){
        client = ApolloInstance.getInstance();
    }

    public void getYourBookMarks(GetYourBookMarkResponse getYourBookMarkResponse){
        client.query(GetYourBookMarksQuery.builder().build()).enqueue(new ApolloCall.Callback<GetYourBookMarksQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetYourBookMarksQuery.Data> response) {
                if(!response.hasErrors()){
                    getYourBookMarkResponse.setYourBookMarksResponse(response.getData().getYourBookMarks());
                }
                else{
                    Log.d(TAG, "onResponse: "+response.getErrors().get(0));
                }

            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }

}
