package com.example.bookblog.UI.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookblog.R;

import java.util.List;

import apolloSchema.GetAllCommentResultQuery;

public class CommentAdapter  extends  RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private static final String TAG = "CommentAdapter";
    private final List<GetAllCommentResultQuery.GetComment> getCommentList;

    public CommentAdapter(List<GetAllCommentResultQuery.GetComment> getCommentList){
        this.getCommentList = getCommentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        holder.nickname.setText(this.getCommentList.get(position).nickname());
        holder.commentBody.setText(this.getCommentList.get(position).comment());
        Glide.with(holder.itemView)
                .load(this.getCommentList.get(position).picture())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.getCommentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        private TextView nickname , commentBody;
        private ImageView image;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.comment_username);
            commentBody = itemView.findViewById(R.id.comment_content);
            image = itemView.findViewById(R.id.comment_user_img);
        }
    }
}
