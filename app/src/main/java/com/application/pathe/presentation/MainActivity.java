package com.application.pathe.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.application.pathe.R;
import com.application.pathe.data_access.LoginApiTask;
import com.application.pathe.data_access.MovieApiTask;
import com.application.pathe.data_access.MovieUpcomingApiTask;
import com.application.pathe.data_access.MoviePopularApiTask;
import com.application.pathe.data_access.MovieRatedApiTask;
import com.application.pathe.domain.Movie;
import com.application.pathe.utilities.SpinnerUtils;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String MOVIE_NAME = "Movie";
    private static final String SESSION_ID = "Session_Id";
    private static final String MOVIES = "Movies";
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter mAdapter;
    private Spinner mSortList;
    private SearchView mSearchbar;
    private int mSpinnerPos;
    private String mSessionID;
    private Button mListNavigate;
    private boolean mFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mMovieList = (ArrayList<Movie>) bundle.get(MOVIES);
        mSessionID = bundle.getString(SESSION_ID);
        mFirstTime = true;

        mSortList = findViewById(R.id.sp_sort_list);
        mSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortList.setAdapter(adapter);

        mSearchbar = findViewById(R.id.sv_search_bar);
        mSearchbar.setOnQueryTextListener(new MovieApiListener());

        mRecyclerView = findViewById(R.id.rv_list_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieRecyclerViewAdapter(mMovieList, new MovieClickListener());
        mRecyclerView.setAdapter(mAdapter);

        mListNavigate = findViewById(R.id.bt_list_navigate);
        mListNavigate.setOnClickListener(new ListNavigateClickListener());

    }

    private void getPopularMoviesFromApi() {
        Log.i(TAG, "getPopularMoviesFromApi aangeroepen");

        MoviePopularApiTask moviePopularApiTask = new MoviePopularApiTask(new MovieApiListener());
        moviePopularApiTask.execute();
    }

    private void getRatedMoviesFromApi() {
        Log.i(TAG, "getRatedMoviesFromApi aangeroepen");
        MovieRatedApiTask movieRatedApiTask = new MovieRatedApiTask(new MovieApiListener());
        movieRatedApiTask.execute();
    }

    private void getNewestMoviesFromApi() {
        Log.i(TAG, "getNewestMoviesFromApi aangeroepen");

        MovieUpcomingApiTask movieUpcomingApiTask = new MovieUpcomingApiTask(new MovieApiListener());
        movieUpcomingApiTask.execute("");
    }

    private void getMoviesFromApi(String searchQuery) {
        Log.i(TAG, "getMoviesFromApi aangeroepen");

        MovieApiTask movieApiTask = new MovieApiTask(new MovieApiListener());
        movieApiTask.execute(searchQuery);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemSelected aangeroepen");
        if (!mFirstTime){
            mSpinnerPos = position;
            SpinnerUtils sortFunctions = new SpinnerUtils(new MovieApiListener());
            // switch case:
            //voor elke case 1 methode.
            switch (position) {
                case 0: //geef movielist mee, wordt gesorteerd en terug gestuurd.
                    getPopularMoviesFromApi();
                    break;
                case 1:
                    getRatedMoviesFromApi();
                    break;
                case 2:
                    getNewestMoviesFromApi();
                    break;
            }
        } else {
            mFirstTime = false;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    class MovieApiListener implements MovieApiTask.MovieApiListener, MoviePopularApiTask.MovieApiListener,
            MovieUpcomingApiTask.MovieApiListener, SpinnerUtils.MovieApiListener, MovieRatedApiTask.MovieApiListener, SearchView.OnQueryTextListener {

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
            mRecyclerView.invalidate();
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            Log.i(TAG, "onQueryTextSubmit aangeroepen");
            if (query.equals("") || query.equals(" ")) {
                switch (mSpinnerPos) {
                    case 0: //geef movielist mee, wordt gesorteerd en terug gestuurd.
                        getPopularMoviesFromApi();
                        break;
                    case 1:
                        getRatedMoviesFromApi();
                        break;
                    case 2:
                        getNewestMoviesFromApi();
                        break;
                }
                return false;
            } else {
                getMoviesFromApi(query);
                return true;
            }
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }

    }

    class MovieClickListener implements MovieRecyclerViewAdapter.MovieClickListener {

        @Override
        public void onItemClick(int position) {
            Log.i(TAG, "onItemClick is aangeroepen");
            Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);

            Movie movie = mMovieList.get(position);
            intent.putExtra(MOVIE_NAME, movie);
            intent.putExtra(SESSION_ID, mSessionID);

            startActivity(intent);
        }
    }

    class ListNavigateClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "onItemClick is aangeroepen");
                Context context = v.getContext();
                Intent intent = new Intent(context, ListActivity.class);

                intent.putExtra(SESSION_ID, mSessionID);

                startActivity(intent);
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
