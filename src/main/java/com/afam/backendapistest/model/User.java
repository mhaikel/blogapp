package com.afam.backendapistest.model;

import com.afam.backendapistest.util.Token;


import javax.validation.constraints.Email;
import java.sql.ResultSet;

public class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @Email(message = "Email must be valid")
    private String email;

    private String verificationCode;
    private ResultSet enable;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ResultSet getEnable() {
        return enable;
    }

    public int setEnable(ResultSet enable) {
        this.enable = enable;
        return 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                //", enable=" + enable +
                '}';
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        Token token = null;
        this.verificationCode = token.generateRandomString();
    }
}
