package com.esime.oflinemovies.loginActivity.Data.Local.Dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;

import java.util.List;

@Dao
public interface LoginDao {

@Query("SELECT * FROM user")
LiveData<List<UserEntity>> getAllUser();

@Insert
    void insertAll(UserEntity userEntity);

@Query("SELECT idUser,userName,password FROM user WHERE userName LIKE :username")
     UserEntity selectUser(String username);

@Query("UPDATE user SET userName = :newUsername WHERE userName LIKE :OldUsername")
    void updateUserDB(String newUsername,String OldUsername);

@Query("UPDATE user SET password = :newPassword WHERE password LIKE :oldPassword")
    void updatePasswordDB(String newPassword, String oldPassword);

}
