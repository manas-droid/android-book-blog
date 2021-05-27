package com.example.bookblog.UI.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookblog.Graphql.Callbacks.GetAllYourPost;
import com.example.bookblog.Graphql.You.GetYourPosts;

import java.util.List;

import apolloSchema.AllYourPostQuery;

public class YouFragmentViewModel extends AndroidViewModel implements GetAllYourPost {
    private MutableLiveData<List<AllYourPostQuery.GetYourPost>> mutableLiveData;
    public YouFragmentViewModel(@NonNull Application application) {
        super(application);
        mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);
        GetYourPosts getYourPosts = new GetYourPosts();
        getYourPosts.getAllPosts(this);
    }

    public MutableLiveData<List<AllYourPostQuery.GetYourPost>> getMutableLiveData() {
        return mutableLiveData;
    }

    @Override
    public void setAllYourPost(List<AllYourPostQuery.GetYourPost> allYourPost) {
        mutableLiveData.postValue(allYourPost);
    }
}
