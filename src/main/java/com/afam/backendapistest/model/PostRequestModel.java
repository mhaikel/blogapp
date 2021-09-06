package com.afam.backendapistest.model;

public class PostRequestModel {
    private String username;
    private String postMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    @Override
    public String toString() {
        return "PostRequestModel{" +
                "username='" + username + '\'' +
                ", postMessage='" + postMessage + '\'' +
                '}';
    }


}
