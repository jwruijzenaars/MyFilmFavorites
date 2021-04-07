package com.application.pathe.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import com.application.pathe.R;
import com.application.pathe.data_access.LoginApiTask;
import com.application.pathe.data_access.MoviePopularApiTask;
import com.application.pathe.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class LoadingScreen extends AppCompatActivity {
    private static final String SESSION_ID = "Session_Id";
    private static final String MOVIES = "Movies";
    private static final String TAG = LoadingScreen.class.getSimpleName();

    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private String mSessionID;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.loading_screen);

        loginAccount();
        try {
            getPopularMoviesFromApi();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            goToMainActivity();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goToMainActivity() throws InterruptedException {
        Thread.sleep(5000);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MOVIES, mMovieList);
        intent.putExtra(SESSION_ID, mSessionID);
        startActivity(intent);
    }

    private void loginAccount() {
        Log.i(TAG, "loginAccount aangeroepen");

        LoginApiTask loginApiTask = new LoginApiTask(new LoadingScreen.LoginListener());
        loginApiTask.execute();
    }

    private void getPopularMoviesFromApi() throws InterruptedException {
        Log.i(TAG, "getPopularMoviesFromApi aangeroepen");

        MoviePopularApiTask moviePopularApiTask = new MoviePopularApiTask(new LoadingScreen.MovieApiListener());
        Thread.sleep(1000);
        moviePopularApiTask.execute();
    }

    class LoginListener implements LoginApiTask.LoginListener {

        @Override
        public void handleLoginResult(String result) {

        }

        @Override
        public void onSessionCreated(String sessionId) {
            mSessionID = sessionId;
        }
    }

    class MovieApiListener implements MoviePopularApiTask.MovieApiListener {

        @Override
        public void handleMovieResult(String result) {
            Log.d(TAG, "handleMoviesResult aangeroepen - " + result);
        }

        @Override
        public void onMoviesAvailable(List<Movie> movieList) {
            Log.d(TAG, "onMoviesAvailable aangeroepen - " + movieList.size());

            mMovieList.clear();
            mMovieList.addAll(movieList);
        }

    }
}
