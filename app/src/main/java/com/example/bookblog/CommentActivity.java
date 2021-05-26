package com.example.bookblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bookblog.UI.Adapter.CommentAdapter;
import com.example.bookblog.UI.ViewModel.CommentActivityViewModel;
import com.example.bookblog.UtilUser.User;
import com.example.bookblog.UtilUser.UserProfile;


public class CommentActivity extends AppCompatActivity {
    private static final String TAG = "CommentActivity";
    private ImageView imageView;
    private RecyclerView recyclerView;
    private CommentActivityViewModel commentActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        imageView = findViewById(R.id.user_image);
        int postId= intent.getIntExtra("postId", 0);
        recyclerView = findViewById(R.id.commentRecyclerview);
        User user = UserProfile.getUserInfo(this);
        Glide.with(this).load(user.getPictureURL()).into(imageView);
        commentActivityViewModel = new ViewModelProvider(this.getViewModelStore(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getApplication()))
                .get(CommentActivityViewModel.class);

        commentActivityViewModel.callGetComments(postId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        commentActivityViewModel.getGetCommentLiveData().observe(this, getCommentList -> {
            if(getCommentList!=null) {
                CommentAdapter commentAdapter = new CommentAdapter(getCommentList);
                recyclerView.setAdapter(commentAdapter);
            }
        });
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy:");
        super.onDestroy();
    }
}