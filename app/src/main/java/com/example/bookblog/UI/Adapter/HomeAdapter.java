package com.example.bookblog.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.bumptech.glide.Glide;
import com.example.bookblog.CommentActivity;
import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.BookMarks.GetBookMark;
import com.example.bookblog.Graphql.GraphqlPostQueries;
import com.example.bookblog.Graphql.Likes.GetLikes;
import com.example.bookblog.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import apolloSchema.GetAllPostResultsQuery;
import apolloSchema.GetPostBookMarkResultQuery;
import apolloSchema.GetPostLikeResultQuery;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>  {
    List<GetAllPostResultsQuery.GetAllPost> list;
    private static final String TAG = "HomeAdapter";
    private final Context activity;
    private GraphqlPostQueries graphqlQueries;
    public HomeAdapter(List<GetAllPostResultsQuery.GetAllPost> list , Context activity){
        this.list = list;
        this.activity = activity;
        graphqlQueries = new GraphqlPostQueries();
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

        GetAllPostResultsQuery.GetAllPost post = list.get(position);
        GetLikes getLikes = new GetLikes(post.id());
        getLikes.getLikesOfAPost(holder.likes);
        GetBookMark getBookMark = new GetBookMark(post.id());
        getBookMark.getBookMarkForAPost(holder.bookMark);



        holder.likes.setOnClickListener(v -> {
          if(holder.likes.getTag().equals("not liked")){
              holder.likes.setImageResource(R.drawable.ic_like);
              holder.likes.setTag("liked");
          }else{
              holder.likes.setImageResource(R.drawable.ic_notliked);
              holder.likes.setTag("not liked");
          }
          graphqlQueries.postLikesForAPost(post.id());
        });



        holder.bookMark.setOnClickListener(v -> {
            if(holder.bookMark.getTag().equals("not bookmarked")){
                holder.bookMark.setImageResource(R.drawable.ic_bookmarked);
                holder.bookMark.setTag("bookmarked");
            }else{
                holder.bookMark.setImageResource(R.drawable.ic_notbookmarked);
                holder.bookMark.setTag("not bookmarked");
            }
            graphqlQueries.postBookMarkForAPost(post.id());
        });


        String author = "article written by : "+post.nickname();

        holder.author.setText(author);
        holder.description.setText(post.description());
        holder.bookname.setText(post.bookname());

        holder.comment.setOnClickListener(v -> {
            Intent intent = new Intent(this.activity, CommentActivity.class);
            intent.putExtra("postId", post.id());
            this.activity.startActivity(intent);
        });


        holder.expand.setOnClickListener(v ->{
            Log.d(TAG, "onBindViewHolder: here in expand");
            TransitionManager.beginDelayedTransition(holder.linearLayout, new AutoTransition());

            if(holder.description.getMaxLines() == 5){
                holder.description.setMaxLines(Integer.MAX_VALUE);
                holder.expand.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
            }else{
                holder.description.setMaxLines(5);
                holder.expand.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
            }
        });

        Glide.with(holder.itemView).load(post.imageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class  HomeViewHolder extends RecyclerView.ViewHolder{
       public TextView bookname , description , author;
       public ImageView bookImage , likes , bookMark , comment;
       public Button expand;
       public LinearLayout linearLayout;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bookname = itemView.findViewById(R.id.bookname);
            this.description = itemView.findViewById(R.id.description);
            this.author = itemView.findViewById(R.id.nickname);
            this.bookImage = itemView.findViewById(R.id.imageUrl);
            this.likes = itemView.findViewById(R.id.like);
            this.bookMark = itemView.findViewById(R.id.bookMark);
            this.expand = itemView.findViewById(R.id.expandableClick);
            this.comment = itemView.findViewById(R.id.comment);
            this.linearLayout = itemView.findViewById(R.id.content);
        }

    }

}
