package com.example.bookblog.Graphql.Callbacks;

import java.util.List;

import apolloSchema.AllYourPostQuery;

public interface GetAllYourPost {
    void setAllYourPost(List<AllYourPostQuery.GetYourPost> allYourPost);
}
