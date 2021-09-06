package com.afam.backendapistest.model;

import java.util.List;

public class CommentResponse {
    private String responseCode;
    private String responseMessage;
    public List<CommentResponseModel> responseModel;

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

    public List<CommentResponseModel> getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(List<CommentResponseModel> responseModel) {
        this.responseModel = responseModel;
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseModel=" + responseModel +
                '}';
    }
}
