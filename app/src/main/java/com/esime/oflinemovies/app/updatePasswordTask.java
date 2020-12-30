package com.esime.oflinemovies.app;

import android.os.AsyncTask;

import com.esime.oflinemovies.loginActivity.ViewModel.UserViewModel;

public class updatePasswordTask extends AsyncTask<String, Void, String> {
    UserViewModel userViewModel;

    public updatePasswordTask(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        userViewModel.updatePasswordDB(strings[0],strings[1]);
        return null;
    }
}
