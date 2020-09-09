package com.esime.oflinemovies.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.ViewModel.MovieInformationViewModel;
import com.esime.oflinemovies.ViewModel.TvInformationViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingDialog extends DialogFragment {
    RatingBar ratingBar;
    TvInformationViewModel viewModelTvInformation;
    MovieInformationViewModel viewModelMovieInformation;
    public int id;
    boolean choose;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelTvInformation = new ViewModelProvider(this).get(TvInformationViewModel.class);
        viewModelMovieInformation = new ViewModelProvider(this).get(MovieInformationViewModel.class);
    }

    public RatingDialog(int id,boolean choose) {
        this.id = id;
        this.choose = choose;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_rating_dialog, container, false);
        ratingBar = view.findViewById(R.id.ratingBarCalificacion);
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            double rate = rating*2;
            String session_id = getActivity().
                    getSharedPreferences(ApiConstants.APP_SETTING_FILE,Context.MODE_PRIVATE).getString(ApiConstants.SESSION_USER_ID,null);
              if(choose){
                  viewModelMovieInformation.postRatingMoview(
                          id,
                          session_id,
                          rate);
              }else{
                  viewModelTvInformation.postRatingTv(
                          id,
                          session_id,
                          rate);
              }
        });

        return view;
    }



}
