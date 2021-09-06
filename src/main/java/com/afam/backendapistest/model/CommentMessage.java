package com.afam.backendapistest.model;

public class CommentMessage {
    private String commentMessage;

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    @Override
    public String toString() {
        return "CommentMessage{" +
                "commentMessage='" + commentMessage + '\'' +
                '}';
    }
}
