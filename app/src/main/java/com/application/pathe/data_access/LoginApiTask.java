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

    private LoginListener listener;

    public LoginApiTask(LoginListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        Log.i(TAG, "doInBackground aangeroepen");

        URL buildUrl1;
        URL buildUrl2;
        String sessionId = null;
        String validatedToken = null;
        String rawSessionID = null;
        JSONParseUtils jsonParseUtils = new JSONParseUtils();

        try {

            String rawToken = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildGetAuthenticationUrl());
            String token = jsonParseUtils.doParseJsonRequestToken(rawToken);
            Log.d(TAG, "token: " + token);

            buildUrl1 = NetworkUtils.buildLoginUrl();
            validatedToken = NetworkUtils.postResponseFromHttpUrl(buildUrl1, token);

            buildUrl2 = NetworkUtils.createSession(token);
            rawSessionID = NetworkUtils.getResponseFromHttpUrl(buildUrl2);

            Log.i(TAG, "Crash check");
            sessionId = jsonParseUtils.doParseTokenToSessionId(rawSessionID);
            System.out.println("Created session with id: " + sessionId);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "doInBackground afgerond");
        return sessionId;

    }

    protected void onPostExecute(String sessionId) {
        Log.i(TAG, "onPostExecute aangeroepen");
        listener.onSessionCreated(sessionId);
    }
    public interface LoginListener {
        public void handleLoginResult(String result);
        public void onSessionCreated(String sessionId);
    }

}

