package com.application.pathe.utilities;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.application.pathe.data_access.LoginApiTask;
import com.application.pathe.presentation.LoadingScreen;
import com.application.pathe.presentation.MainActivity;

public class LoadingScreenUtils extends AsyncTask<String, Void, String> {
    private static final String TAG = LoadingScreenUtils.class.getSimpleName();

    private LoadingListener listener;

    public LoadingScreenUtils(LoadingListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute aangeroepen");
        listener.onCompletion(s);
    }

    public interface LoadingListener {
        public void handleLoginResult(String result);
        public void onCompletion(String sessionId);
    }
}
