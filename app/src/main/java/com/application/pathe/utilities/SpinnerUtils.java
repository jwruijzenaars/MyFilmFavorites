package com.application.pathe.utilities;


import com.application.pathe.data_access.MoviePopularApiTask;
import com.application.pathe.domain.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SpinnerUtils implements Comparator<Movie>{

    private MovieApiListener listener;

    public SpinnerUtils(MovieApiListener listener) {
        this.listener = listener;
    }

    public SpinnerUtils() {

    }

    public void sortAToZ(ArrayList<Movie> movies) {
        ArrayList<Movie> sortedMovie;
        sortedMovie = movies;

        Collections.sort(sortedMovie, new SpinnerUtils());

        listener.onMoviesAvailable(sortedMovie);
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }

    public interface MovieApiListener {
        public void handleMovieResult(String result);
        public void onMoviesAvailable(List<Movie> movieList);
    }

}
