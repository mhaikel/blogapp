package com.afam.backendapistest.model;

public class CommentRequestModel {

    private long postId;
    private String commentMessage;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    @Override
    public String toString() {
        return "CommentRequestModel{" +
                "postId=" + postId +
                ", commentMessage='" + commentMessage + '\'' +
                '}';
    }
}
