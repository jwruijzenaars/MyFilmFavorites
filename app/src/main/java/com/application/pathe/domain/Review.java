package com.application.pathe.domain;

import java.io.Serializable;

public class Review implements Serializable {

    private String author;
    private String content;
    private String createdAt;
    private String rating;

    public Review (String author, String content, String createdAt, String rating) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.rating = rating;

    }

    @Override
    public String toString() {
        return  "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt;
    }

    public String getAuthor() { return author; }

    public String getContent() { return content; }

    public String getCreatedAt() { return createdAt; }

    public String getRating() {
        return rating;
    }
}
