package com.afam.backendapistest.postModel;

import java.util.List;

public class PostResponse {
    private String responseCode;
    private String responseMessage;
    public List<PostModel> postModel;

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

    public List<PostModel> getPostModel() {
        return postModel;
    }

    public void setPostModel(List<PostModel> postModel) {
        this.postModel = postModel;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", postModel=" + postModel +
                '}';
    }
}
