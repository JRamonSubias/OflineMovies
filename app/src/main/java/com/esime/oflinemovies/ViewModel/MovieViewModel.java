package com.esime.oflinemovies.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;
import com.esime.oflinemovies.Data.MovieRepository;
import com.esime.oflinemovies.Data.Remoto.model.MovieCompleteResponse;
import com.esime.oflinemovies.UI.MovieInformation;
import com.esime.oflinemovies.network.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {
   // private final LiveData<Resource<List<MovieEntity>>> popularMovie;
    private MutableLiveData<List<MovieEntity>> popularMovie;
    private final LiveData<Resource<List<TvEntity>>> popularTv;
    private final MovieRepository movieRepository;
    private int Page;


    public MovieViewModel(){

        movieRepository = new MovieRepository();
       // popularMovie = movieRepository.getPopularMovies();
        popularMovie = movieRepository.getMovieWithOutBD();
        popularTv = movieRepository.getPopularTv();
    }

   // public LiveData<Resource<List<MovieEntity>>> getPopularMovie(){ return popularMovie; }
    public LiveData<List<MovieEntity>> getPopularMovie(){return popularMovie;}

    public LiveData<Resource<List<TvEntity>>> getPopularTv(){return popularTv; }

    public int getTotalPage(){
        return movieRepository.getTotalPage();
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        movieRepository.setPage(page);
    }




}
