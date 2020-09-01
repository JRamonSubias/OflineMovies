package com.esime.oflinemovies.Data.Local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.esime.oflinemovies.Data.Local.Dao.MovieDao;
import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Local.Entity.SearchEntity;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;

@Database(entities = {MovieEntity.class, TvEntity.class, SearchEntity.class},version = 1,exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao getDao();


}
