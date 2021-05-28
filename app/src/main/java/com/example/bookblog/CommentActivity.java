package com.example.bookblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
    private Button submitPost ;
    private EditText addCommentToPost;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        imageView = findViewById(R.id.user_image);
        int postId= intent.getIntExtra("postId", 0);
        recyclerView = findViewById(R.id.commentRecyclerview);
        progressBar = findViewById(R.id.progressBar);
        User user = UserProfile.getUserInfo(this);

        Glide.with(this).load(user.getPictureURL()).into(imageView);
        commentActivityViewModel = new ViewModelProvider(this.getViewModelStore(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getApplication()))
                .get(CommentActivityViewModel.class);


        commentActivityViewModel.callGetComments(postId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        commentActivityViewModel.getGetCommentLiveData().observe(this, getCommentList -> {
            if(getCommentList!=null) {
                progressBar.setVisibility(View.GONE);
                CommentAdapter commentAdapter = new CommentAdapter(getCommentList);
                recyclerView.setAdapter(commentAdapter);
            }else{
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        addCommentToPost = findViewById(R.id.addPostComment);
        submitPost = findViewById(R.id.postComment);
        addCommentToPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()>0 && !submitPost.isEnabled()){
                    submitPost.setEnabled(true);
                }else if(s.toString().trim().length() == 0 && submitPost.isEnabled()){
                    submitPost.setEnabled(false);
                }
            }
        });

        submitPost.setOnClickListener(v -> {
            Log.d(TAG, "onClick: "+addCommentToPost.getText().toString());
            commentActivityViewModel.callPostComments(postId,addCommentToPost.getText().toString(),this);
            addCommentToPost.setText("");
            Log.d(TAG, "onCreate: submitted");
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