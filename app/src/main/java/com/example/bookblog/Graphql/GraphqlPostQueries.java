package com.example.bookblog.Graphql;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bookblog.Graphql.BookMarks.PostBookMark;
import com.example.bookblog.Graphql.Comments.PostComments;
import com.example.bookblog.Graphql.Likes.PostLikes;


public class GraphqlPostQueries {
    public GraphqlPostQueries(AppCompatActivity activity){
        new ApolloInstance(activity);
    }

    public void postBookMarkForAPost(int postId){
        PostBookMark postBookMark = new PostBookMark();
        postBookMark.postBookMarkForAPost(postId);
    }

    public void postLikesForAPost(int postId){
        PostLikes postLikes = new PostLikes();
        postLikes.addLikeToPost(postId);
    }
    public void postCommentForAPost(Integer postId, String comment){
        PostComments postComments = new PostComments();
        postComments.postCommentForAPost(comment, postId);
    }


}
