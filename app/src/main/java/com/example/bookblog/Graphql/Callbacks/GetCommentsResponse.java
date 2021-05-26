package com.example.bookblog.Graphql.Callbacks;

import java.util.List;

import apolloSchema.GetAllCommentResultQuery;

public interface GetCommentsResponse {
    void setCommentList(List<GetAllCommentResultQuery.GetComment> getCommentList);
}
