package com.application.pathe.data_access;

import android.os.AsyncTask;
import android.util.Log;

import com.application.pathe.domain.Lists;
import com.application.pathe.utilities.JSONParseUtils;
import com.application.pathe.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllListsApiTask extends AsyncTask<String, Void, List<Lists>> {

    private static final String TAG = MovieApiTask.class.getSimpleName();


    private ListsApiListener listener;

    public AllListsApiTask(ListsApiListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Lists> doInBackground(String... strings) {
        Log.i(TAG, "doInBackground aangeroepen");
        String sessionID = strings[0];

        URL buildUrl1;
        String listSearchResults = null;
        List<Lists> lists = new ArrayList<>();
        JSONParseUtils jsonParseUtils = new JSONParseUtils();

        try {

            buildUrl1 = NetworkUtils.buildGetLists(sessionID);
            listSearchResults = NetworkUtils.getResponseFromHttpUrl(buildUrl1);

            Log.i(TAG, "Crash check");
            lists = jsonParseUtils.doParseJsonToListsList(listSearchResults);
            System.out.println("lists: " + lists);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "doInBackground afgerond");
        return lists;

    }

    @Override
    protected void onPostExecute(List<Lists> listsList) {
        Log.i(TAG, "onPostExecute aangeroepen");
        listener.onListsAvailable(listsList);
    }



    public interface ListsApiListener {
        public void handleMovieResult(String result);
        public void onListsAvailable(List<Lists> listsList);
    }
}
