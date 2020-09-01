package com.esime.oflinemovies.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.oflinemovies.Data.MovieRepository;
import com.esime.oflinemovies.Data.Remoto.model.MovieCompleteResponse;

public class MovieInformationViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<MovieCompleteResponse> movieInformation;

    public MovieInformationViewModel(){
        movieRepository = new MovieRepository();
    }

    public MutableLiveData<MovieCompleteResponse> getMovieInformation(){
        movieInformation = movieRepository.getMovieCompleteInformation();
        return movieInformation;
    }

    public void setIdMovie(int idMovie){movieRepository.setId(idMovie);}

    public void postRatingMoview(int id, String session_id, Double rating){
        movieRepository.PostRatingMovie(id,session_id,rating);
    }
}
