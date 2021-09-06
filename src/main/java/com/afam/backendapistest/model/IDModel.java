package com.afam.backendapistest.model;

public class IDModel {

    private long id;
    private String username;

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

    @Override
    public String toString() {
        return "IDModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
