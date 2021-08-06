package com.afam.backendapistest.postModel;

public class PostMessage {
    private String postMessage;

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    @Override
    public String toString() {
        return "PostMessage{" +
                "postMessage='" + postMessage + '\'' +
                '}';
    }
}
