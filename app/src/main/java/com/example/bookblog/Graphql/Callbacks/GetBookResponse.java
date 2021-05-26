package com.example.bookblog.Graphql.Callbacks;

import android.util.Log;

import java.util.List;

import apolloSchema.GetAllPostResultsQuery;

public interface GetBookResponse {
    void setResponse(List<GetAllPostResultsQuery.GetAllPost> getAllPost);
}