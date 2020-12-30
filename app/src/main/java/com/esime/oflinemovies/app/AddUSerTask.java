package com.esime.oflinemovies.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.UI.MainActivity;
import com.esime.oflinemovies.loginActivity.ViewModel.UserViewModel;

import java.lang.ref.WeakReference;

public class AddUSerTask extends AsyncTask<String, Void, String> {

    private WeakReference<Activity> weakActivity;
    private String session_id;

    public AddUSerTask(Activity activity, String session){
        weakActivity = new WeakReference<>(activity);
        session_id = session;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(MyApp.getContext(), "Insertar Usuario", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        SharedPreferenceManager.setSomeStringValue(ApiConstants.SESSION_USER_ID,s);
        Activity activity = weakActivity.get();
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected String doInBackground(String... strings) {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.InsertUserViewModel(strings[0],strings[1],strings[2]);
        onPostExecute(session_id);
        return null;
    }
}
