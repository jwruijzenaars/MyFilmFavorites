package com.application.pathe.data_access;

import android.os.AsyncTask;
import android.util.Log;

import com.application.pathe.domain.Movie;
import com.application.pathe.domain.Review;
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

        try {

            buildUrl1 = NetworkUtils.buildSearchMovieTitleUrl(searchQuery);
            movieSearchResults = NetworkUtils.getResponseFromHttpUrl(buildUrl1);

            Log.i(TAG, "Crash check");
            movies = doParseJsonToArrayList(movieSearchResults);

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

    private List<Movie> doParseJsonToArrayList(String jsonString1) {
        List<Movie> movieResult = new ArrayList<>();

        try {
            JSONObject results = new JSONObject (jsonString1);

            JSONArray movieList = results.getJSONArray(JSON_MOVIE_GET_RESULTS);
            for (int j = 0; j < movieList.length(); j++) {
                JSONObject currentMovie = movieList.getJSONObject(j);
                String title = currentMovie.getString(JSON_MOVIE_TITLE);
                String description = currentMovie.getString(JSON_MOVIE_DESCRIPTION);
                String rating = currentMovie.getString(JSON_MOVIE_RATING);
                String language = currentMovie.getString(JSON_MOVIE_LANGUAGE);
                String releaseDate = currentMovie.getString(JSON_MOVIE_RELEASE_DATE);
                String imageUrl = "https://image.tmdb.org/t/p/original" + currentMovie.getString(JSON_MOVIE_IMAGE_URL);

                String genre = null;
                JSONArray genreList = currentMovie.getJSONArray(JSON_MOVIE_GENRE);
                for (int i = 0; i < genreList.length(); i++) {
                    String genreType =  genreList.getString(i);
                    genre += "," + genreType;
                }
                String id = currentMovie.getString(JSON_MOVIE_ID);

                URL buildUrl2;
                String CastSearchResults = null;
                buildUrl2 = NetworkUtils.buildCastListUrl(id);

                try {
                    CastSearchResults = NetworkUtils.getResponseFromHttpUrl(buildUrl2);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                JSONObject resultsCast = new JSONObject(CastSearchResults);
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

                URL buildUrl3;
                String reviewSearchResults = null;
                buildUrl3 = NetworkUtils.buildReviewListUrl(id);

                try {
                    reviewSearchResults = NetworkUtils.getResponseFromHttpUrl(buildUrl3);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject resultsReview = new JSONObject(reviewSearchResults);
                ArrayList<Review> review = new ArrayList<>();
                JSONArray reviewList = resultsReview.getJSONArray(JSON_MOVIE_GET_RESULTS);
                for (int y = 0; y < 8 && y < reviewList.length(); y++) {
                    JSONObject reviewObject = (JSONObject) reviewList.get(y);
                    if (reviewObject != null) {
                        JSONObject authorDetail = reviewObject.getJSONObject(JSON_MOVIE_AUTHOR_DETAIL);

                        String substrCreatedAt = reviewObject.getString(JSON_REVIEW_CREATED_AT).substring(0,10);
                        review.add(new Review(reviewObject.getString(JSON_MOVIE_AUTHOR_KEY), reviewObject.getString(JSON_REVIEW_CONTENT), substrCreatedAt, authorDetail.getString(JSON_REVIEW_RATING)));
                    } else {
                        break;
                    }
                }

                Movie newMovie = new Movie(title, imageUrl, description, rating, cast , "placeholder", genre, language, releaseDate, "available", review);
                movieResult.add(newMovie);

            }
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

