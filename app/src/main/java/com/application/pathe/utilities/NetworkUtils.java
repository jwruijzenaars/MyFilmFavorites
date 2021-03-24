package com.application.pathe.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    final static String MOVIE_API_V3 = "88eb80ac323dd890b4e6eda54cbfbc0e";
    final static String MOVIE_API_CREDITS = "credits";
    final static String MOVIE_API_POPULAR = "popular";
    final static String MOVIE_API_V4 = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OGViODBhYzMyM2RkODkwYjRlNmVkYTU0Y2JmYmMwZSIsInN1YiI6IjVlNzIxNDRlYjFmNjhkMDAxMmRjZTQ0NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0addbQ6PmtMG0upm5C-Qt8dLIfaq0bru2F2XzakNbf4";
    final static String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/";
    final static String MOVIE_API_URL_SEARCH = "https://api.themoviedb.org/3/search/movie";
    final static String MOVIE_API_FORMAT = "api_key=";
    final static String MOVIE_API_QUERY = "query=";
    final static String MOVIE_API_PAGE = "page=1";
    final static String MOVIE_API_PG = "include_adult=true";
    final static String MOVIE_API_LANGUAGE = "language=en-US";


//https://api.themoviedb.org/3/movie/550?api_key=5c135f148aad677f96fa566df2585042

    public static URL buildSearchMovieTitleUrl(String searchTerm) {
        String builtUri = MOVIE_API_URL_SEARCH+"?"+MOVIE_API_FORMAT+MOVIE_API_V3+"&"+
                MOVIE_API_QUERY+searchTerm+"&"+MOVIE_API_PAGE+"&"+MOVIE_API_PG;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url: " + url);
        return url;
    }

    public static URL buildSearchMovieUrl(String movieId) {
        String builtUri = MOVIE_API_URL+movieId+"?"+MOVIE_API_FORMAT+MOVIE_API_V3;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url: " + url);
        return url;
    }

    public static URL buildCastListUrl (String movieId) {
        String builtUri = MOVIE_API_URL+movieId+"/"+MOVIE_API_CREDITS+"?"+MOVIE_API_FORMAT+MOVIE_API_V3;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url: " + url);
        return url;
    }

    public static URL buildPopularListUrl () {
        String builtUri = MOVIE_API_URL+MOVIE_API_POPULAR+"?"+MOVIE_API_FORMAT+MOVIE_API_V3+"&"+MOVIE_API_LANGUAGE+"&"+MOVIE_API_PAGE;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
