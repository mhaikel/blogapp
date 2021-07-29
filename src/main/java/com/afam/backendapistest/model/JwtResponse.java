package com.afam.backendapistest.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken;
    private User userToken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public JwtResponse(User userToken) {
        this.jwttoken = jwttoken;
        this.userToken = userToken;
    }

    public String getToken() {
        return this.jwttoken;
    }



}