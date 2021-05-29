package com.example.bookblog.Graphql.BookMarks;

import android.util.Log;
import android.widget.ImageView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.R;

import org.jetbrains.annotations.NotNull;

import apolloSchema.GetPostBookMarkResultQuery;

public class GetBookMark {
    private ApolloClient client;
    private static final String TAG = "GetBookMark";
    private int postId;
    public GetBookMark(int postId){
        this.postId = postId;
        client = ApolloInstance.getInstance();
    }


    public void getBookMarkForAPost(ImageView bookMarkImage){

        client.query(GetPostBookMarkResultQuery.builder().postId(this.postId).build())
                .enqueue(new ApolloCall.Callback<GetPostBookMarkResultQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetPostBookMarkResultQuery.Data> response) {
                        if(!response.hasErrors()){
                            if(response.getData().getBookMarks()) {
                                bookMarkImage.setImageResource(R.drawable.ic_bookmarked);
                                bookMarkImage.setTag("bookmarked");
                            }
                        }
                        else{
                            Log.d(TAG, "onResponse: "+response.getErrors().get(0));
                        }
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });

    }
}
