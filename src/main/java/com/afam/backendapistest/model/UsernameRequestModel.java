package com.afam.backendapistest.model;

public class UsernameRequestModel {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UsernameRequestModel{" +
                "username='" + username + '\'' +
                '}';
    }
}
