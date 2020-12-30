package com.esime.oflinemovies.UI;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.app.GetPasswordTask;
import com.esime.oflinemovies.app.MyApp;
import com.esime.oflinemovies.app.SharedPreferenceManager;
import com.esime.oflinemovies.app.updatePasswordTask;
import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;
import com.esime.oflinemovies.loginActivity.Data.UserRepositoy;
import com.esime.oflinemovies.loginActivity.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutionException;

public class GestionarCuenta extends DialogFragment {
    TextView tvUserName, tvPassword;
    Button btnApplyUser,btnApplyPassword, btnEditUser,btnEditPassword;
    TextInputLayout etUserName, etPasswrd,etPasswordConfirm;
    UserViewModel userViewModel;
    String newUser, passwordNew, passwordConfirm,oldPassword;
    UserEntity userEntity;
    int idUserDB;

    public GestionarCuenta() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_gestionar_cuenta, container, false);
        findViewBy(view);
        loadInformationUserName();

        btnEditUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               etUserName.setVisibility(View.VISIBLE);
               btnEditUser.setVisibility(View.INVISIBLE);
               btnApplyUser.setVisibility(View.VISIBLE);
           }
       });

        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPasswordConfirm.setVisibility(View.VISIBLE);
                btnApplyPassword.setVisibility(View.VISIBLE);
            }
        });

        btnApplyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser = etUserName.getEditText().getText().toString();
                if(newUser.isEmpty()){
                    etUserName.setError("Ingrese nuevo nombre de usuario");
                }else if(newUser.equals(tvUserName.getText().toString())){
                     etUserName.setError("Mismo nombre de usuario.");
                }else {
                    updateUserNameToDB(newUser);
                    Toast.makeText(getActivity(), "Usuario Actualizado", Toast.LENGTH_SHORT).show();
                    dismiss();

                }
            }
        });

        btnApplyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPasswordConfirm.setError(null);
                etPasswrd.setError(null);
                passwordConfirm = etPasswordConfirm.getEditText().getText().toString();
                passwordNew = etPasswrd.getEditText().getText().toString();

                if(passwordNew.isEmpty()){
                    if(passwordConfirm.isEmpty()){
                        etPasswordConfirm.setError("Ingrese su contraseña");
                    }else {
                        try {
                            if(passwordConfirm.equals(new GetPasswordTask(userViewModel)
                                    .execute(passwordConfirm,tvUserName.getText().toString()).get())){

                                Toast.makeText(MyApp.getContext(), "Contraseña Correcta", Toast.LENGTH_SHORT).show();
                                etPasswordConfirm.setVisibility(View.INVISIBLE);
                                etPasswrd.setVisibility(View.VISIBLE);

                            }else {
                                Toast.makeText(MyApp.getContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(passwordNew.isEmpty()){
                    etPasswrd.setError("Ingrese nueva contraseña");
                }else if(passwordNew.equals(passwordConfirm)){
                    etPasswrd.setError("La contraseña es la misma");
                }else{
                    updatePasswordTask passwordTask = new updatePasswordTask(userViewModel);
                    passwordTask.execute(passwordConfirm,passwordNew);
                    Toast.makeText(MyApp.getContext(), "Contraseña Actualizada", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
             }
        });

        return view;
    }


    private void updateUserNameToDB(String newUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userViewModel.upDateUserDB(newUser,tvUserName.getText().toString());
                SharedPreferenceManager.DeleteValue(ApiConstants.USER_SAVE);
                SharedPreferenceManager.setSomeStringValue(ApiConstants.USER_SAVE,newUser);
            }
        }).start();
    }

    private void loadInformationUserName(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String username = SharedPreferenceManager.getSomeStringValue(ApiConstants.USER_SAVE);
                userEntity = userViewModel.getUserInformation(username);
                tvUserName.setText(userEntity.getUserName());
            }
        }).start();
    }


    private void findViewBy(View view) {
        tvUserName = view.findViewById(R.id.textViewEditUsername);
        tvPassword = view.findViewById(R.id.textViewEditPassword);
        btnEditUser = view.findViewById(R.id.buttonEditarUser);
        btnEditPassword = view.findViewById(R.id.buttonEditarPassword);
        btnApplyUser = view.findViewById(R.id.ButtonEditarApplyUser);
        btnApplyPassword = view.findViewById(R.id.buttonEditarApplyPassword);
        etUserName = view.findViewById(R.id.EditTextUserEditUser);
        etPasswrd = view.findViewById(R.id.EditTextEditPasword);
        etPasswordConfirm = view.findViewById(R.id.EditTextEditPaswordConfirm);
    }
}







