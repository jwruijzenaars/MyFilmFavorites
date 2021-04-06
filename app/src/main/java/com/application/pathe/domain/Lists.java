package com.application.pathe.domain;

import java.io.Serializable;
import java.util.List;

public class Lists implements Serializable {

    private String mListName;
    private List<Movie> mMovieList;
    private String mListID;


    public Lists(String listName, List<Movie> movieList, String listID) {
        mListName = listName;
        mMovieList = movieList;
        mListID = listID;
    }

    public Lists(String listName, String listID) {
        mListName = listName;
        mListID = listID;
    }

    public void setListName(String mListName) {
        this.mListName = mListName;
    }

    public String getListName() {
        return mListName;
    }

    public List<Movie> getmMovieList() {
        return mMovieList;
    }

    public void addMovie(Movie movie) {
        mMovieList.add(movie);
    }

    public void deleteMovie(int position) {
        mMovieList.remove(position);
    }

    public String getmListID() {
        return mListID;
    }
}
