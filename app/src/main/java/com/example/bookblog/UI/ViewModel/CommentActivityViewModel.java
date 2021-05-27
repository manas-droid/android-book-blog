package com.example.bookblog.UI.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookblog.Graphql.Callbacks.GetCommentsResponse;
import com.example.bookblog.Graphql.Comments.GetComments;
import com.example.bookblog.Graphql.GraphqlPostQueries;

import java.util.List;

import apolloSchema.GetAllCommentResultQuery;

public class CommentActivityViewModel extends ViewModel implements GetCommentsResponse {


    private MutableLiveData<List<GetAllCommentResultQuery.GetComment>> getCommentLiveData;
    public CommentActivityViewModel() {
        getCommentLiveData = new MutableLiveData<>();
        getCommentLiveData.setValue(null);
    }
    public void callGetComments(int postId){
        GetComments getComments = new GetComments(postId);
        getComments.getCommentForAPost(this);
    }

    public void callPostComments(Integer postId , String comment , Context activity){
            GraphqlPostQueries graphqlPostQueries = new GraphqlPostQueries((AppCompatActivity)activity);
            graphqlPostQueries.postCommentForAPost(postId, comment);
    }

    public MutableLiveData<List<GetAllCommentResultQuery.GetComment>> getGetCommentLiveData() {
        return getCommentLiveData;
    }

    @Override
    public void setCommentList(List<GetAllCommentResultQuery.GetComment> getCommentList) {

        getCommentLiveData.postValue(getCommentList);
    }
}
