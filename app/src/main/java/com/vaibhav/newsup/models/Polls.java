package com.vaibhav.newsup.models;

import android.util.Log;

public class Polls {

    private String title,content;
    private Long id;

    public Polls(){

    }

    public Polls(String title, String content, Long id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
