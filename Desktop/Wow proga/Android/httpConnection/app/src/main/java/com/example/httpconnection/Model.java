package com.example.httpconnection;

public class Model {
    private String title, author, description, genre;
    private String url;


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }


    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String description) {
        this.author = description;
    }

    public void setImg(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
