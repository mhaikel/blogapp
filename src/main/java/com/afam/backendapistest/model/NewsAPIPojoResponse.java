package com.afam.backendapistest.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class NewsAPIPojoResponse {
    private String status;
    private int totalResults;
    private Articles[] articles;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public Articles[] getArticles() {
        return articles;
    }

    public void setArticles(Articles[] articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}
