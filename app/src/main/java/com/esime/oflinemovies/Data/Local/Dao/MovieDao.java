package com.esime.oflinemovies.Data.Local.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Local.Entity.SearchEntity;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;

import java.util.List;
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<MovieEntity>> loadMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<MovieEntity> movieEntityList);


    @Query("SELECT * FROM tv")
    LiveData<List<TvEntity>> loadTV();
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void saveTv(List<TvEntity> tvEntityList);

    @Query("SELECT * FROM search")
    LiveData<List<SearchEntity>> loadSearch();
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void saveSearch(List<SearchEntity> searchEntityList);

    @Query("DELETE FROM search")
    void DeleteTableSearch();




}
