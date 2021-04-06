package com.application.pathe.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.pathe.R;
import com.application.pathe.data_access.AllListsApiTask;
import com.application.pathe.domain.Lists;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    private static final String TAG = ListActivity.class.getSimpleName();
    private static final String SESSION_ID = "Session_Id";
    private static String mSessionId;
    private RecyclerView mRecyclerView;
    private ListRecyclerViewAdapter mAdapter;
    private List<Lists> mListsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_list_view);
        Intent intent = getIntent();
        mSessionId = (String) intent.getSerializableExtra(SESSION_ID);
        System.out.println("SessionID: " + mSessionId);

        mRecyclerView = findViewById(R.id.rv_list_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListRecyclerViewAdapter(mListsList, new ListActivity.ListClickListener());
        mRecyclerView.setAdapter(mAdapter);


        getAllLists();
    }
    private void getAllLists() {
        Log.i(TAG, "getAllLists aangeroepen");

        AllListsApiTask allListsApiTask = new AllListsApiTask(new ListActivity.ListsApiListener());
        allListsApiTask.execute(mSessionId);
    }


    public class ListsApiListener implements AllListsApiTask.ListsApiListener {
        @Override
        public void handleMovieResult(String result) {

        }

        @Override
        public void onListsAvailable(List<Lists> listsList) {
            Log.d(TAG, "onListsAvailable aangeroepen - " + listsList.size());

            mListsList.clear();
            mListsList.addAll(listsList);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.invalidate();

        }
    }

    private class ListClickListener implements ListRecyclerViewAdapter.ListClickListener {

        @Override
        public void onItemClick(int position) {

        }
    }
}
