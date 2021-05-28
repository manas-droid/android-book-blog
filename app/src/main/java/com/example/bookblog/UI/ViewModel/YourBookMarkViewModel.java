package com.example.bookblog.UI.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookblog.Graphql.Callbacks.GetYourBookMarkResponse;
import com.example.bookblog.Graphql.You.GetYourBookMarks;

import java.util.List;

import apolloSchema.GetYourBookMarksQuery;

public class YourBookMarkViewModel extends AndroidViewModel implements GetYourBookMarkResponse {

    private MutableLiveData<List<GetYourBookMarksQuery.GetYourBookMark>>mutableLiveData;
    public YourBookMarkViewModel(@NonNull Application application) {
        super(application);
        mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(null);
        GetYourBookMarks getYourBookMarks = new GetYourBookMarks();
        getYourBookMarks.getYourBookMarks(this);
    }

    public MutableLiveData<List<GetYourBookMarksQuery.GetYourBookMark>> getMutableLiveData() {
        return mutableLiveData;
    }

    @Override
    public void setYourBookMarksResponse(List<GetYourBookMarksQuery.GetYourBookMark> getYourBookMarkList) {
        mutableLiveData.postValue(getYourBookMarkList);
    }
}
