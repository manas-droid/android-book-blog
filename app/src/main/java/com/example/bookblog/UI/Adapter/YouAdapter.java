package com.example.bookblog.UI.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.bumptech.glide.Glide;
import com.example.bookblog.CommentActivity;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.Callbacks.GetAllYourPost;
import com.example.bookblog.Graphql.Callbacks.GetLikeResponse;
import com.example.bookblog.Graphql.GraphqlPostQueries;
import com.example.bookblog.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import apolloSchema.AllYourPostQuery;
import apolloSchema.GetAllPostResultsQuery;
import apolloSchema.GetPostBookMarkResultQuery;
import apolloSchema.GetPostLikeResultQuery;

public class YouAdapter extends RecyclerView.Adapter<YouAdapter.HomeViewHolder> implements GetLikeResponse {
    List<AllYourPostQuery.GetYourPost> list;
    private static final String TAG = "HomeAdapter";
    private final Context activity;
    private final ApolloClient client;
    private GraphqlPostQueries graphqlQueries;
    public YouAdapter(List<AllYourPostQuery.GetYourPost> list , Context activity){
        this.list = list;
        this.activity = activity;
        client = ApolloInstance.getInstance();
        graphqlQueries = new GraphqlPostQueries((AppCompatActivity)activity);
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_posts, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        AllYourPostQuery.GetYourPost post = list.get(position);
        ApolloQueryCall<GetPostLikeResultQuery.Data> like =  this.client.query(GetPostLikeResultQuery.builder().postId(post.id()).build());

        like.enqueue(new ApolloCall.Callback<GetPostLikeResultQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetPostLikeResultQuery.Data> response) {
                if(!response.hasErrors()){
                    if (response.getData().getLikes()) {
                        holder.likes.setTag("liked");
                        holder.likes.setImageResource(R.drawable.ic_like);
                    }
                }
                else {
                    for(Error error : response.getErrors()){
                        Log.d(TAG, "onResponse: "+error.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onFailure: ",e );
            }
        });


        client.query(GetPostBookMarkResultQuery.builder().postId(post.id()).build())
                .enqueue(new ApolloCall.Callback<GetPostBookMarkResultQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetPostBookMarkResultQuery.Data> response) {
                        if(!response.hasErrors()){
                            if(response.getData().getBookMarks()) {
                                holder.bookMark.setImageResource(R.drawable.ic_bookmarked);
                                holder.bookMark.setTag("bookmarked");
                            }
                        }
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e(TAG, "onFailure: ",e );
                    }
                });


        holder.likes.setOnClickListener(v -> {
            if(holder.likes.getTag().equals("not liked")){
                Log.d(TAG, "onBindViewHolder: to like");
                holder.likes.setImageResource(R.drawable.ic_like);
                holder.likes.setTag("liked");
            }else{
                Log.d(TAG, "onBindViewHolder: to unlike");
                holder.likes.setImageResource(R.drawable.ic_notliked);
                holder.likes.setTag("not liked");
            }

            graphqlQueries.postLikesForAPost(post.id());
        });



        holder.bookMark.setOnClickListener(v -> {
            if(holder.bookMark.getTag().equals("not bookmarked")){
                Log.d(TAG, "onBindViewHolder: to bookmark");
                holder.bookMark.setImageResource(R.drawable.ic_bookmarked);
                holder.bookMark.setTag("bookmarked");
            }else{
                Log.d(TAG, "onBindViewHolder: to un bookmark");
                holder.bookMark.setImageResource(R.drawable.ic_notbookmarked);
                holder.bookMark.setTag("not bookmarked");
            }
            graphqlQueries.postBookMarkForAPost(post.id());
        });


        holder.description.setText(post.description());
        holder.bookName.setText(post.bookname());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(this.activity, CommentActivity.class);
            intent.putExtra("postId", post.id());
            this.activity.startActivity(intent);
        });


        Glide.with(holder.itemView).load(post.imageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.bookImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void setLikeResponse(boolean isLiked) {
        Log.d(TAG, "setLikeResponse: "+isLiked);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        public TextView bookName , description;
        public ImageView bookImage , likes , bookMark;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bookName = itemView.findViewById(R.id.bookname);
            this.description = itemView.findViewById(R.id.description);
            this.bookImage = itemView.findViewById(R.id.imageUrl);
            this.likes = itemView.findViewById(R.id.like);
            this.bookMark = itemView.findViewById(R.id.bookMark);
        }
    }
}
