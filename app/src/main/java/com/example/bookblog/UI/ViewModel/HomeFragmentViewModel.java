package com.example.bookblog.UI.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookblog.Graphql.Books.GetBooks;
import com.example.bookblog.Graphql.Callbacks.GetBookResponse;

import java.util.List;

import apolloSchema.GetAllPostResultsQuery;

public class HomeFragmentViewModel extends AndroidViewModel implements GetBookResponse {

    private MutableLiveData<List<GetAllPostResultsQuery.GetAllPost>> getAllPostMutableLiveData;
    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        GetBooks getBooks = new GetBooks();
        getBooks.getAllPosts(this);
        getAllPostMutableLiveData = new MutableLiveData<>();
        getAllPostMutableLiveData.setValue(null);
    }


    public MutableLiveData<List<GetAllPostResultsQuery.GetAllPost>> getGetAllPostMutableLiveData() {
        return getAllPostMutableLiveData;
    }

    @Override
    public void setResponse(List<GetAllPostResultsQuery.GetAllPost> getAllPost) {
            getAllPostMutableLiveData.postValue(getAllPost);
    }
}
