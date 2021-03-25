package com.application.pathe.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.application.pathe.R;
import com.application.pathe.data_access.MovieApiTask;
import com.application.pathe.data_access.MoviePlayingApiTask;
import com.application.pathe.data_access.MoviePopularApiTask;
import com.application.pathe.domain.Movie;
import com.application.pathe.utilities.SpinnerUtils;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
// JW test1

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String MOVIE_NAME = "Movie";
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter mAdapter;
    private Spinner mSortList;
    private SearchView mSearchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSortList = findViewById(R.id.sp_sort_list);
        mSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortList.setAdapter(adapter);
        mSearchbar = findViewById(R.id.sv_search_bar);

        mRecyclerView = findViewById(R.id.rv_movie_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieRecyclerViewAdapter(mMovieList, new MovieClickListener());
        mRecyclerView.setAdapter(mAdapter);
        getPopularMoviesFromApi();
    }

    private void getPopularMoviesFromApi() {
        Log.i(TAG, "getPopularMoviesFromApi aangeroepen");

        MoviePopularApiTask moviePopularApiTask = new MoviePopularApiTask(new MainActivity.MoviePopularApiListener());
        moviePopularApiTask.execute();
    }

    private void getNewestMoviesFromApi() {
        Log.i(TAG, "getNewestMoviesFromApi aangeroepen");

        MoviePlayingApiTask moviePlayingApiTask = new MoviePlayingApiTask(new MainActivity.MoviePlayingApiListener());
        moviePlayingApiTask.execute("");
    }

    private void getMoviesFromApi(String fight_club) {
        Log.i(TAG, "getMoviesFromApi aangeroepen");

        MovieApiTask movieApiTask = new MovieApiTask(new MovieApiListener());
        movieApiTask.execute(fight_club);

    }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SpinnerUtils spinnerUtils = new SpinnerUtils();
        // switch case:
            //voor elke case 1 methode.
            switch (position) {
                case 0:
                    ArrayList<Movie> newMovieList = spinnerUtils.sortNameAToZ(mMovieList);

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    class MoviePlayingApiListener implements MoviePlayingApiTask.MoviePlayingApiListener {

            @Override
            public void handleMovieResult(String result) {
                Log.d(TAG, "handleMoviesResult aangeroepen - " + result);
        }

            @Override
            public void onMoviesAvailable(List<Movie> movieList) {
                Log.d(TAG, "onMoviesAvailable aangeroepen - " + movieList.size());

                mMovieList.clear();
                mMovieList.addAll(movieList);
                mAdapter.notifyDataSetChanged();
            }
    }

    class MovieApiListener implements MovieApiTask.MovieApiListener {

        @Override
        public void handleMovieResult(String result) {
            Log.d(TAG, "handleMoviesResult aangeroepen - " + result);
        }

        @Override
        public void onMoviesAvailable(List<Movie> movieList) {
            Log.d(TAG, "onMoviesAvailable aangeroepen - " + movieList.size());

            mMovieList.clear();
            mMovieList.addAll(movieList);
            mAdapter.notifyDataSetChanged();
        }
    }

    class MovieClickListener implements MovieRecyclerViewAdapter.MovieClickListener {

        @Override
        public void onItemClick(int position) {
            Log.i(TAG, "onItemClick is aangeroepen");
            Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);

            Movie movie = mMovieList.get(position);
            intent.putExtra(MOVIE_NAME, movie);

            startActivity(intent);
        }
    }

    class MoviePopularApiListener implements MoviePopularApiTask.MoviePopularApiListener {

        @Override
        public void handleMovieResult(String result) {
            Log.d(TAG, "handleMoviesResult aangeroepen - " + result);
        }

        @Override
        public void onMoviesAvailable(List<Movie> movieList) {
            Log.d(TAG, "onMoviesAvailable aangeroepen - " + movieList.size());

            mMovieList.clear();
            mMovieList.addAll(movieList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
