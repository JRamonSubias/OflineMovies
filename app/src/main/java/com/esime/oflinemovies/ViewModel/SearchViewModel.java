package com.esime.oflinemovies.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.oflinemovies.Data.Local.Entity.SearchEntity;
import com.esime.oflinemovies.Data.MovieRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final MovieRepository movieRepository;
    private MutableLiveData<List<SearchEntity>> listLiveDataSearch;



    public SearchViewModel(){
        movieRepository = new MovieRepository();
    }



    public LiveData<List<SearchEntity>> getListLiveDataSearch(){
        listLiveDataSearch = movieRepository.getSearchWithOutBD();
        return listLiveDataSearch;
    }

    public void InsertSearchQuery(String query){ movieRepository.setQuery(query);}

}
