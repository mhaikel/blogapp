package com.afam.backendapistest.model;

public class CommentIDRequestModel {

    private long commentId;
    private long postId;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CommentIDRequestModel{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                '}';
    }
}
