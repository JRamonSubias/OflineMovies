package com.esime.oflinemovies.loginActivity.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.UI.MainActivity;
import com.esime.oflinemovies.app.MyApp;
import com.esime.oflinemovies.app.SharedPreferenceManager;
import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;
import com.esime.oflinemovies.loginActivity.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private TextInputLayout etUser, etPassword;
    String user, password;
    private TextView tvSingUp;
    private List<UserEntity> listUsers;
    private UserViewModel userViewModel;
    private CheckBox checkBoxRememberme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_movie_actitity);
        getSupportActionBar().hide();
        FindViewById();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        if(validarSesion()){
            toActivity();
        }
        
        login.setOnClickListener(v -> {
            goToMainActivity();
        });
        tvSingUp.setOnClickListener(v -> {
            goToSingUp();
        });

    }

    private void goToSingUp() {
        SingUp singUp = new SingUp();
        singUp.show(getSupportFragmentManager(),"SingUp");
    }

    private void goToMainActivity() {
        user = etUser.getEditText().getText().toString().trim();
        password = etPassword.getEditText().getText().toString().trim();
        if (user.isEmpty()){
            etUser.setError("Ingrese Usuario");
        }else if(password.isEmpty()){
            etPassword.setError("Ingrese Contraseña");
        }else{
            loadListUsers();
        }
    }

    private void FindViewById() {
        login = findViewById(R.id.ButtonLogin);
        etUser = findViewById(R.id.EditTextUserLogin);
        etPassword = findViewById(R.id.EditTextUserPasword);
        tvSingUp = findViewById(R.id.textViewSingUp);
        checkBoxRememberme = findViewById(R.id.checkBoxRememberme);
    }

    public void loadListUsers (){
         userViewModel.getAllUser().observe(this, userEntities -> {
             listUsers = userEntities;
            ConfirmUser(listUsers,user,password);
         });
    }

    public void ConfirmUser(List<UserEntity> listUsers,String user, String password){
        for(int i=0; i<listUsers.size();i++){
            if(user.equals(listUsers.get(i).getUserName()) && password.equals(listUsers.get(i).getPassword())){
                goToActivity(listUsers.get(i).getSession_id(), listUsers.get(i).getUserName());
            }
        }
    }

    public void goToActivity(String id,String user){
        if(checkBoxRememberme.isChecked()){
            SharedPreferenceManager.setSomeStringValue(ApiConstants.SESSION_USER_ID,id);
        }
        SharedPreferenceManager.setSomeStringValue(ApiConstants.USER_SAVE,user);
        toActivity();
    }

    public boolean validarSesion (){
        String session_id = SharedPreferenceManager.getSomeStringValue(ApiConstants.SESSION_USER_ID);
        if(session_id == null){
            return false;
        }else{
            return true;
        }
    }

    public void toActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}


