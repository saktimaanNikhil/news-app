package com.example.newsapp.modles;

import java.util.ArrayList;

public class mainNews {
    private String status;
    private String totalResults;
    private ArrayList<ApiModel> articles;

    public mainNews(String status, String totalResults, ArrayList<ApiModel> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<ApiModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ApiModel> articles) {
        this.articles = articles;
    }
}
