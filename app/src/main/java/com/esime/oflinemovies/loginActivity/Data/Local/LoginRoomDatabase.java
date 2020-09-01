package com.esime.oflinemovies.loginActivity.Data.Local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.esime.oflinemovies.loginActivity.Data.Local.Dao.LoginDao;
import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;

@Database(entities = {UserEntity.class},version = 1,exportSchema = false)
public abstract  class LoginRoomDatabase extends RoomDatabase {

    public abstract LoginDao getDao();
}
