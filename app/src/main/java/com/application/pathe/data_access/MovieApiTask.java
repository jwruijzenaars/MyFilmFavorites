package com.application.pathe.data_access;

import android.os.AsyncTask;
import android.util.Log;

import com.application.pathe.domain.Movie;
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

    private String JSON_MOVIE_TITLE = "title";
    private String JSON_MOVIE_IMAGE_URL = "poster_path";
    private String JSON_MOVIE_DESCRIPTION = "overview";
    private String JSON_MOVIE_CAST = "name";
    private String JSON_MOVIE_CAST_KEY = "cast";
    private String JSON_MOVIE_TRAILER_URL = "";
    private String JSON_MOVIE_GENRE = "genres";
    private String JSON_MOVIE_GENRE_TYPE = "name";
    private String JSON_MOVIE_LANGUAGE = "original_language";
    private String JSON_MOVIE_RELEASE_DATE = "release_date";
    private String JSON_MOVIE_AVAILABILITY = "status";
    private String JSON_MOVIE_RATING = "vote_average";

    private MovieApiListener listener;

    public MovieApiTask(MovieApiListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        Log.i(TAG, "doInBackground aangeroepen");
        String searchQuery = strings[0];

        URL buildUrl1;
        URL buildUrl2;
        String movieSearchResults1 = null;
        String movieSearchResults2 = null;
        List<Movie> movies = new ArrayList<>();

        try {

            buildUrl1 = NetworkUtils.buildSearchMovieUrl(searchQuery);
            buildUrl2 = NetworkUtils.buildCastListUrl(searchQuery);


            movieSearchResults1 = NetworkUtils.getResponseFromHttpUrl(buildUrl1);
            movieSearchResults2 = NetworkUtils.getResponseFromHttpUrl(buildUrl2);

            Log.i(TAG, "Crash check");
            movies = doParseJsonToArrayList(movieSearchResults1, movieSearchResults2);

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

    private List<Movie> doParseJsonToArrayList(String jsonString1, String jsonString2) {
        Log.i(TAG, "doParseJsonToArrayList aangeroepen");
        List<Movie> movieResult = new ArrayList<>();

        try {
            JSONObject results = new JSONObject (jsonString1);

            String title = results.getString(JSON_MOVIE_TITLE);
            String description = results.getString(JSON_MOVIE_DESCRIPTION);
            String rating = results.getString(JSON_MOVIE_RATING);
            String language = results.getString(JSON_MOVIE_LANGUAGE);
            String releaseDate = results.getString(JSON_MOVIE_RELEASE_DATE);
            String availability = results.getString(JSON_MOVIE_AVAILABILITY);
            String imageUrl = "https://image.tmdb.org/t/p/original" + results.getString(JSON_MOVIE_IMAGE_URL);

            String genre = null;
            JSONArray genreList = results.getJSONArray(JSON_MOVIE_GENRE);
                for (int i = 0; i < genreList.length(); i++) {
                    JSONObject genreType = (JSONObject) genreList.get(i);
                    genre = genreType.getString(JSON_MOVIE_GENRE_TYPE);
                }

                Log.i(TAG, "cast parsen");

            JSONObject resultsCast = new JSONObject(jsonString2);
            ArrayList<String> cast = new ArrayList<>();
            JSONArray castList = resultsCast.getJSONArray(JSON_MOVIE_CAST_KEY);
            for (int y = 0; y < 8 && y < castList.length(); y++) {
                JSONObject castMember = (JSONObject) castList.get(y);
                if (castMember != null) {
                    cast.add(castMember.getString(JSON_MOVIE_CAST));
                } else {
                    break;
                }
            }

            Movie newMovie = new Movie(title, imageUrl, description, rating, cast
                    , "placeholder", genre, language, releaseDate, availability);
            movieResult.add(newMovie);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieResult;
    }

    public interface MovieApiListener {
        public void handleMovieResult(String result);
        public void onMoviesAvailable(List<Movie> movieList);
    }
}

