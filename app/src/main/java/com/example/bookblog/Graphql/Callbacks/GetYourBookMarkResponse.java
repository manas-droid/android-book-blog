package com.example.bookblog.Graphql.Callbacks;

import java.util.List;

import apolloSchema.GetYourBookMarksQuery;

public interface GetYourBookMarkResponse {
    void setYourBookMarksResponse(List<GetYourBookMarksQuery.GetYourBookMark> getYourBookMarkList);
}
