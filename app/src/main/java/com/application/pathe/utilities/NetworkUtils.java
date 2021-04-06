package com.application.pathe.utilities;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    final static String MOVIE_API_V3 = "88eb80ac323dd890b4e6eda54cbfbc0e";
    final static String MOVIE_API_CREDITS = "credits";
    final static String MOVIE_API_POPULAR = "popular";
    final static String MOVIE_API_RATING = "top_rated";
    final static String MOVIE_API_UPCOMING = "upcoming";
    final static String MOVIE_API_REVIEW = "reviews";
    final static String MOVIE_API_V4 = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OGViODBhYzMyM2RkODkwYjRlNmVkYTU0Y2JmYmMwZSIsInN1YiI6IjVlNzIxNDRlYjFmNjhkMDAxMmRjZTQ0NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0addbQ6PmtMG0upm5C-Qt8dLIfaq0bru2F2XzakNbf4";
    final static String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/";
    final static String MOVIE_API_URL_SEARCH = "https://api.themoviedb.org/3/search/movie";
    final static String MOVIE_API_FORMAT = "api_key=";
    final static String MOVIE_API_QUERY = "query=";
    final static String MOVIE_API_PAGE = "page=1";
    final static String MOVIE_API_PG = "include_adult=true";
    final static String MOVIE_API_LANGUAGE = "language=en-US";
    final static String MOVIE_API_GET_TOKEN_URL = "https://api.themoviedb.org/3/authentication/token/new?";
    final static String MOVIE_API_LOGIN_URL = "https://api.themoviedb.org/3/authentication/token/validate_with_login?";
    final static String MOVIE_API_GET_SESSION = "https://api.themoviedb.org/3/authentication/session/new?";
    final static String MOVIE_REQUEST_TOKEN = "request_token=";
    final static String MOVIE_LISTS_FROM_ACCOUNT_URL = "https://api.themoviedb.org/3/account/9142470/lists?";
    final static String MOVIE_SESSION_ID_FORMAT = "session_id=";
    final static String MOVIE_GET_LIST_URL = "https://api.themoviedb.org/3/list/";


//https://api.themoviedb.org/3/movie/550?api_key=5c135f148aad677f96fa566df2585042

    public static URL buildSearchMovieTitleUrl(String searchTerm) {
        String builtUri = MOVIE_API_URL_SEARCH +"?"+ MOVIE_API_FORMAT + MOVIE_API_V3 +"&"+ MOVIE_API_LANGUAGE +"&"+
                MOVIE_API_QUERY + searchTerm +"&"+ MOVIE_API_PAGE +"&"+ MOVIE_API_PG;

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

        return url;
    }

    public static URL buildPopularListUrl () {
        String builtUri = MOVIE_API_URL + MOVIE_API_POPULAR +"?"+ MOVIE_API_FORMAT + MOVIE_API_V3 +"&"+ MOVIE_API_LANGUAGE +"&"+ MOVIE_API_PAGE;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildRatedListUrl () {
        String builtUri = MOVIE_API_URL + MOVIE_API_RATING +"?"+ MOVIE_API_FORMAT + MOVIE_API_V3 + "&" + MOVIE_API_LANGUAGE +"&"+ MOVIE_API_PAGE;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUpcomingListUrl () {
        String builtUri = MOVIE_API_URL + MOVIE_API_UPCOMING +"?"+ MOVIE_API_FORMAT + MOVIE_API_V3 + "&" + MOVIE_API_LANGUAGE +"&"+ MOVIE_API_PAGE;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildReviewListUrl (String movieId) {
        String builtUri = MOVIE_API_URL + movieId +"/"+ MOVIE_API_REVIEW +"?"+ MOVIE_API_FORMAT + MOVIE_API_V3 + "&" + MOVIE_API_LANGUAGE +"&"+ MOVIE_API_PAGE;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildGetAuthenticationUrl () {
        String builtUri = MOVIE_API_GET_TOKEN_URL + MOVIE_API_FORMAT + MOVIE_API_V3;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildLoginUrl () {
        String builtUri = MOVIE_API_LOGIN_URL + MOVIE_API_FORMAT + MOVIE_API_V3;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL createSession (String requestToken) {
        String builtUri = MOVIE_API_GET_SESSION + MOVIE_API_FORMAT + MOVIE_API_V3 + "&" + MOVIE_REQUEST_TOKEN + requestToken;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildGetLists (String sessionID) {
        String builtUri = MOVIE_LISTS_FROM_ACCOUNT_URL + MOVIE_API_FORMAT + MOVIE_API_V3 + "&" + MOVIE_API_LANGUAGE + "&" + MOVIE_SESSION_ID_FORMAT + sessionID;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildGetListByID (String id) {
        String builtUri = MOVIE_GET_LIST_URL + id + "?" + MOVIE_API_FORMAT + MOVIE_API_V3 + "&" + MOVIE_API_LANGUAGE;

        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

//    public static URL buildSearchMovieUrl(String movieId) {
//        String builtUri = MOVIE_API_URL+movieId+"?"+MOVIE_API_FORMAT+MOVIE_API_V3;
//
//        URL url = null;
//        try {
//            url = new URL(builtUri);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        Log.d(TAG, "Built url: " + url);
//        return url;
//    }

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String postResponseFromHttpUrl(URL url, String tokenString) throws IOException {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        JSONObject input = new JSONObject();
        try {
            input.put("username", "jw.ruijzenaars");
            input.put("password", "JJWV123");
            input.put("request_token", tokenString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try (OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());) {
            os.write(input.toString());
            os.flush();
            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
                return sb.toString();
            } else {
                System.out.println(con.getResponseMessage());
                return "fail";
            }
//
//            try(BufferedReader br = new BufferedReader(
//                    new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                return response.toString();
//            }
//
        }
        finally {
            con.disconnect();
        }
    }

}
