package com.afam.backendapistest.model;

import java.util.List;

public class UsernameResponse {
    private String responseCode;
    private String responseMessage;
    private List<UsernameDetailsModel> usernameDetailsModel;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<UsernameDetailsModel> getUsernameDetailsModel() {
        return usernameDetailsModel;
    }

    public void setUsernameDetailsModel(List<UsernameDetailsModel> usernameDetailsModel) {
        this.usernameDetailsModel = usernameDetailsModel;
    }

    @Override
    public String toString() {
        return "UsernameResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", usernameDetailsModel=" + usernameDetailsModel +
                '}';
    }
}
