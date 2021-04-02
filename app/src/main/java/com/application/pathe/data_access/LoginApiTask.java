package com.application.pathe.data_access;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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

public class LoginApiTask extends AsyncTask<String, Void, String> {

    private static final String TAG = LoginApiTask.class.getSimpleName();


    public LoginApiTask() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        Log.i(TAG, "doInBackground aangeroepen");

        URL buildUrl1;
        String sessionId = null;
        String movieSearchResults = null;
        JSONParseUtils jsonParseUtils = new JSONParseUtils();

        try {

            String rawToken = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildGetAuthenticationUrl());
            String token = jsonParseUtils.doParseJsonRequestToken(rawToken);

            buildUrl1 = NetworkUtils.buildLoginUrl();
            movieSearchResults = NetworkUtils.postResponseFromHttpUrl(buildUrl1, token);

            Log.i(TAG, "Crash check");
            sessionId = jsonParseUtils.doParseJsonRequestToken(movieSearchResults);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "doInBackground afgerond");
        return sessionId;

    }

}

