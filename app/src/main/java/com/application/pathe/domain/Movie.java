package com.application.pathe.domain;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {

    private String title;
    private String imgUrl;
    private String description;
    private String rating;
    private ArrayList<String> castMember;
    private ArrayList<Review> reviews;
    private String trailerUrl;
    private String genre;
    private String language;
    private String releaseDate;
    private String availability;

    public Movie(String title, String imgUrl, String description, String rating, ArrayList<String> castMember,
                 String trailerUrl, String genre, String language, String releaseDate, String availability,
                 ArrayList<Review> reviews) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
        this.rating = rating;
        this.castMember = castMember;
        this.trailerUrl = trailerUrl;
        this.genre = genre;
        this.language = language;
        this.releaseDate = releaseDate;
        this.availability = availability;
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return  "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", description='" + description + '\'' +
                ", rating='" + rating + '\'' +
                ", castMember=" + castMember +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", genre='" + genre + '\'' +
                ", language='" + language + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", availability='" + availability;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public ArrayList<String> getCastMember() {
        return castMember;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getAvailability() {
        return availability;
    }
}
