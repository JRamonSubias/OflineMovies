package com.esime.oflinemovies.loginActivity.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.oflinemovies.Data.Remoto.model.GetTokenResponse;
import com.esime.oflinemovies.Data.Remoto.model.SessionResponse;
import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;
import com.esime.oflinemovies.loginActivity.Data.UserRepositoy;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepositoy userRepositoy;
    private MutableLiveData<SessionResponse> sessionResponseMutableLiveData;
    private LiveData<List<UserEntity>> listUsers;



    public UserViewModel(){
        userRepositoy = new UserRepositoy();
    }



    public MutableLiveData<SessionResponse> getTokenResponse(){
        sessionResponseMutableLiveData = userRepositoy.getToken();
        return sessionResponseMutableLiveData;
    }

    public void InsertUserViewModel(String user, String password,String session_id){
        userRepositoy.InsertUser(user,password,session_id);
    }

    public LiveData<List<UserEntity>> getAllUser(){
        listUsers = userRepositoy.getAllUser();
        return listUsers;
}

    public UserEntity getUserInformation(String username){
       return userRepositoy.getUserInformation(username);
    }

    public void upDateUserDB(String newUsername, String oldUsername){
        userRepositoy.UpdateUser(newUsername, oldUsername);
    }

}
