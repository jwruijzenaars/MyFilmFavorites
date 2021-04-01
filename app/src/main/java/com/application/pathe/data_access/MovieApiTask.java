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

public class MovieApiTask extends AsyncTask<String, Void, List<Movie>> {

    private static final String TAG = MovieApiTask.class.getSimpleName();


    private MovieApiListener listener;

    public MovieApiTask(MovieApiListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        Log.i(TAG, "doInBackground aangeroepen");
        String searchQuery = strings[0];

        URL buildUrl1;
        String movieSearchResults = null;
        List<Movie> movies = new ArrayList<>();
        JSONParseUtils jsonParseUtils = new JSONParseUtils();

        try {

            buildUrl1 = NetworkUtils.buildSearchMovieTitleUrl(searchQuery);
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

