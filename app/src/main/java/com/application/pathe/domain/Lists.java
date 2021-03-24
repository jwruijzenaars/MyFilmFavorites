package com.application.pathe.domain;

import java.util.List;

public class Lists {

    private String mListName;
    private List<Movie> mMovieList;
    private boolean mPreDefined;


    public Lists(String listName, List<Movie> movieList, boolean isPreDefined) {
        mListName = listName;
        mMovieList = movieList;
        mPreDefined = isPreDefined;
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
}
