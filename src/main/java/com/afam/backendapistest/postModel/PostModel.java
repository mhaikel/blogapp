package com.afam.backendapistest.postModel;

public class PostModel {
    private long id;
    private String username;
    private String postMessage;
    private String dateCreated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", postMessage='" + postMessage + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
