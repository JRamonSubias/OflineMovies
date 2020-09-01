package com.esime.oflinemovies.loginActivity.Data.Local.Dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;

import java.util.List;

@Dao
public interface LoginDao {

@Query("SELECT * FROM user")
LiveData<List<UserEntity>> getAllUser();

@Insert
    void insertAll(UserEntity userEntity);



}
