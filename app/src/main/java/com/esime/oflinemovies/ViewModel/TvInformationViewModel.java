package com.esime.oflinemovies.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.oflinemovies.Data.MovieRepository;
import com.esime.oflinemovies.Data.Remoto.model.TvInformationResponse;

public class TvInformationViewModel extends ViewModel {
    private MutableLiveData<TvInformationResponse> tvinformation;
    private MovieRepository movieRepository;

    public TvInformationViewModel(){
        movieRepository = new MovieRepository();
    }

    public MutableLiveData<TvInformationResponse> getTvinformation(){
        tvinformation = movieRepository.getTvCompleteInformation();
        return tvinformation;
    }

    public void setIdMovie(int idMovie){movieRepository.setId(idMovie);}

    public void postRatingTv(int id, String session_id, Double rating){
        movieRepository.PostRatingTv(id,session_id,rating);
    }

}
