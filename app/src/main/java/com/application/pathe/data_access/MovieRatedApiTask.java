package com.application.pathe.data_access;

import android.os.AsyncTask;
import android.util.Log;

import com.application.pathe.domain.Movie;
import com.application.pathe.domain.Review;
import com.application.pathe.utilities.JSONParseUtils;
import com.application.pathe.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieRatedApiTask extends AsyncTask<String, Void, List<Movie>> {
    private static final String TAG = MovieApiTask.class.getSimpleName();

    private static final String JSON_MOVIE_ID = "id";
    private static final String JSON_MOVIE_TITLE = "title";
    private static final String JSON_MOVIE_IMAGE_URL = "poster_path";
    private static final String JSON_MOVIE_DESCRIPTION = "overview";
    private static final String JSON_MOVIE_CAST = "name";
    private static final String JSON_MOVIE_CAST_KEY = "cast";
    private static final String JSON_MOVIE_AUTHOR_KEY = "author";
    private static final String JSON_REVIEW_CONTENT = "content";
    private static final String JSON_REVIEW_CREATED_AT = "created_at";
    private static final String JSON_REVIEW_RATING = "rating";
    private static final String JSON_MOVIE_AUTHOR_DETAIL = "author_details";
    private static final String JSON_MOVIE_TRAILER_URL = "";
    private static final String JSON_MOVIE_GENRE = "genre_ids";
    private static final String JSON_MOVIE_LANGUAGE = "original_language";
    private static final String JSON_MOVIE_RELEASE_DATE = "release_date";
    private static final String JSON_MOVIE_GET_RESULTS = "results";
    private static final String JSON_MOVIE_RATING = "vote_average";

    private MovieApiListener listener;

    public MovieRatedApiTask(MovieApiListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        Log.i(TAG, "doInBackground aangeroepen");

        URL buildUrl1;
        String movieSearchResults;
        List<Movie> movies = null;
        JSONParseUtils jsonParseUtils = new JSONParseUtils();

        try {

            buildUrl1 = NetworkUtils.buildRatedListUrl();
            movieSearchResults = NetworkUtils.getResponseFromHttpUrl(buildUrl1);

            Log.i(TAG, "Crash check");
            movies = jsonParseUtils.doParseJsonToArrayList(movieSearchResults);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "doInBackground afgerond");
        return movies;

    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        Log.i(TAG, "onPostExecute aangeroepen");
        listener.onMoviesAvailable(movieList);
    }

    public interface MovieApiListener {
        public void handleMovieResult(String result);
        public void onMoviesAvailable(List<Movie> movieList);
    }
}
