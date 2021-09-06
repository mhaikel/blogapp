package com.afam.backendapistest.model;

public class CommentResponseModel {

    private long commentId;
    private long postId;
    private String commentMessage;
    private String timeCreated;

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

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return "CommentResponseModel{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", commentMessage='" + commentMessage + '\'' +
                ", timeCreated='" + timeCreated + '\'' +
                '}';
    }
}
