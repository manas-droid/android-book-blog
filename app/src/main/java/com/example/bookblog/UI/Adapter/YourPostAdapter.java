package com.example.bookblog.UI.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bookblog.CommentActivity;
import com.example.bookblog.R;
import com.example.bookblog.SinglePostActivity;

import java.util.List;

import apolloSchema.AllYourPostQuery;

public class YourPostAdapter extends RecyclerView.Adapter<YourPostAdapter.HomeViewHolder> {
    List<AllYourPostQuery.GetYourPost> list;
    private static final String TAG = "YourPostAdapter";
    private final Context activity;


    public YourPostAdapter(List<AllYourPostQuery.GetYourPost> list , Context activity){
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_your_post, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        AllYourPostQuery.GetYourPost post = list.get(position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(this.activity, SinglePostActivity.class);

            intent.putExtra("image", post.imageUrl());
            intent.putExtra("bookName", post.bookname());
            intent.putExtra("description", post.description());
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



    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        public ImageView bookImage;
        public LinearLayout linearLayout;


        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bookImage = itemView.findViewById(R.id.imageUrl);
            this.linearLayout = itemView.findViewById(R.id.content);
        }
    }
}
