package com.esime.oflinemovies.app;

import android.os.AsyncTask;

import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;
import com.esime.oflinemovies.loginActivity.ViewModel.UserViewModel;

public class GetPasswordTask extends AsyncTask<String, String, String> {
    String passwordOld;
    UserViewModel userViewModel;

    public GetPasswordTask(UserViewModel userViewModel) {
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
        UserEntity userEntity = userViewModel.getUserInformation(strings[1]);
        String password = userEntity.getPassword();
        return password;
    }
}
