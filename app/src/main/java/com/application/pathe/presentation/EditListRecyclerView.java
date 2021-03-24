package com.application.pathe.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.pathe.R;
import com.application.pathe.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class EditListRecyclerView extends AppCompatActivity {

    private final static String TAG = EditListRecyclerView.class.getSimpleName();
    RecyclerView mRecyclerView;
    EditListRecyclerViewAdapter mAdapter;
    List<Movie> mMovieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_list_view);

        mRecyclerView = findViewById(R.id.rv_movie_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new EditListRecyclerViewAdapter(mMovieList, new ButtonClickListener());
        mRecyclerView.setAdapter(mAdapter);

    }

    class ButtonClickListener implements EditListRecyclerViewAdapter.ButtonClickListener {

        @Override
        public void onItemClick(int position) {
            Log.i(TAG, "onItemClick is aangeroepen");
            mMovieList.remove(position);
        }
    }
}
