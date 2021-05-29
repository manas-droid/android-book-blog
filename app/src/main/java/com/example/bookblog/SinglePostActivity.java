package com.example.bookblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookblog.Graphql.BookMarks.GetBookMark;
import com.example.bookblog.Graphql.GraphqlPostQueries;
import com.example.bookblog.Graphql.Likes.GetLikes;



public class SinglePostActivity extends AppCompatActivity {

    private String bookName, description,imageUrl;
    private int postId;
    private Button expand;
    private TextView tv_bookName , tv_description;
    private ImageView iv_Image , iv_Comment , iv_likes, iv_bookMark;
    private LinearLayout linearLayout;
    private static final String TAG = "SinglePostActivity";
    private GraphqlPostQueries graphqlPostQueries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        graphqlPostQueries = new GraphqlPostQueries();

        Intent intent = getIntent();
        bookName = intent.getStringExtra("bookName");
        postId  = intent.getIntExtra("postId" , 0);
        description = intent.getStringExtra("description");
        imageUrl = intent.getStringExtra("image");

        tv_description = findViewById(R.id.description);
        tv_bookName = findViewById(R.id.bookname);
        iv_Image = findViewById(R.id.imageUrl);
        expand = findViewById(R.id.expandableClick);
        linearLayout = findViewById(R.id.content);
        iv_Comment = findViewById(R.id.comment);
        iv_likes = findViewById(R.id.like);
        iv_bookMark = findViewById(R.id.bookMark);


        init();
        clickExpand();
        clickComment();

        clickLikes();
        clickBookMark();
    }

    private void clickExpand(){
        expand.setOnClickListener(v ->{
            Log.d(TAG, "clickExpand: here");
            TransitionManager.beginDelayedTransition(linearLayout, new AutoTransition());
            if(tv_description.getMaxLines() == 5){
                tv_description.setMaxLines(Integer.MAX_VALUE);
                expand.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
            }else{
                tv_description.setMaxLines(5);
                expand.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
            }
        });
    }

    private void clickLikes(){
        iv_likes.setOnClickListener(v -> {
            if(iv_likes.getTag().equals("not liked")){
                iv_likes.setImageResource(R.drawable.ic_like);
                iv_likes.setTag("liked");
            }else{
                iv_likes.setImageResource(R.drawable.ic_notliked);
                iv_likes.setTag("not liked");
            }
            graphqlPostQueries.postLikesForAPost(postId);
        });
    }

    private void clickBookMark(){

        iv_bookMark.setOnClickListener(v -> {
            if(iv_bookMark.getTag().equals("not bookmarked")){
                iv_bookMark.setImageResource(R.drawable.ic_bookmarked);
                iv_bookMark.setTag("bookmarked");
            }else{
                iv_bookMark.setImageResource(R.drawable.ic_notbookmarked);
                iv_bookMark.setTag("not bookmarked");
            }
            graphqlPostQueries.postBookMarkForAPost(postId);
        });
    }

    private void clickComment(){
        iv_Comment.setOnClickListener(v -> {
            Log.d(TAG, "clickComment: here");
            Intent intent = new Intent(this, CommentActivity.class);
            intent.putExtra("postId", postId);
            startActivity(intent);
        });
    }



    private void init(){
        tv_bookName.setText(bookName);
        tv_description.setText(description);
        Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_placeholder).into(iv_Image);

        GetLikes likes = new GetLikes(postId);
        likes.getLikesOfAPost(iv_likes);

        GetBookMark getBookMark = new GetBookMark(postId);
        getBookMark.getBookMarkForAPost(iv_bookMark);
    }

}