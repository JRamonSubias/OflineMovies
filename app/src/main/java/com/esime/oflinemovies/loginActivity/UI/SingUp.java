package com.esime.oflinemovies.loginActivity.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.UI.MainActivity;
import com.esime.oflinemovies.app.AddUSerTask;
import com.esime.oflinemovies.app.MyApp;
import com.esime.oflinemovies.app.SharedPreferenceManager;
import com.esime.oflinemovies.loginActivity.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingUp extends DialogFragment {
    public TextInputLayout etUser, etPassword, etPasswordConfirm;
   public  Button btnSingUP;
    UserViewModel userViewModel;
   public String user,password,passwordConfirm;
    WebView webView;
    AddUSerTask asinkTask;


    public SingUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_No_Border);
       userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sing_up, container, false);
        findViewIdBy(view);
        btnSingUP.setOnClickListener(v -> CreateUser());



        return view;
    }

    public void findViewIdBy(View view){
        btnSingUP = view.findViewById(R.id.ButtonSing_Up);
        etUser = view.findViewById(R.id.EditTextSingUpUserName);
        etPassword = view.findViewById(R.id.EditTextSingUpPasword);
        etPasswordConfirm = view.findViewById(R.id.EditTextSingUpPaswordConfirm);
    }

    private void CreateUser() {
      user = etUser.getEditText().getText().toString().trim();
      password = etPassword.getEditText().getText().toString().trim();
      passwordConfirm = etPasswordConfirm.getEditText().getText().toString().trim();

      if(user.isEmpty()){
          etUser.setError("Ingrese un Nombre de Usuario");
      } else if(password.isEmpty()){
          etPassword.setError("Ingrese Contraseña");
      }else if(!password.equals(passwordConfirm)){
          etPasswordConfirm.setError("Las Contraseñas Son diferntes, Ingrese la misma");
      }else{
          loadSessionId();
      }

    }


  public void loadSessionId(){
        userViewModel.getTokenResponse().observe(getActivity(), sessionResponse -> {
            String session_Id = sessionResponse.getSessionId();
            asinkTask = new AddUSerTask(getActivity(),session_Id);
            asinkTask.execute(user,password,session_Id);
        });
  }

}

