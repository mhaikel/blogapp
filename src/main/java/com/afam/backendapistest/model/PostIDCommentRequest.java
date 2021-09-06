package com.afam.backendapistest.model;

public class PostIDCommentRequest {
    private long postId;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "PostIDCommentRequest{" +
                "postId=" + postId +
                '}';
    }
}
