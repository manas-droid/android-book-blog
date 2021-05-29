package com.example.bookblog.Graphql.Likes;

import android.util.Log;
import android.widget.ImageView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.R;

import org.jetbrains.annotations.NotNull;

import apolloSchema.GetPostLikeResultQuery;

public class GetLikes {
    ApolloClient client;
    private static final String TAG = "GetLikes";
    private int postId;
    public GetLikes(int postId){
        client = ApolloInstance.getInstance();
        this.postId = postId;
    }

    public void getLikesOfAPost(ImageView likeButton){

        this.client.query(GetPostLikeResultQuery.builder().postId(this.postId).build()).enqueue(new ApolloCall.Callback<GetPostLikeResultQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetPostLikeResultQuery.Data> response) {
                if(!response.hasErrors()){
                    if(response.getData().getLikes()){
                        likeButton.setTag("liked");
                        likeButton.setImageResource(R.drawable.ic_like);
                    }
                }else {
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
