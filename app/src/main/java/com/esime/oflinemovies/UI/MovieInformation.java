package com.esime.oflinemovies.UI;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.esime.oflinemovies.Data.Local.Entity.GenerosEntity;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.Data.Remoto.model.MovieCompleteResponse;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.ViewModel.MovieInformationViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;


import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInformation extends Fragment {
    ImageView imageViewCover,ivCard;
    TextView tvTitulo, tvFecha, tvDescripcion, tvGenero, tvCalificacion, tvLinea;
    MovieInformationViewModel viewModel;
    MovieCompleteResponse movieResponse;
    List<GenerosEntity> generosList;
    String generos=" |";
    ScrollView scrollView;
    private ChipGroup chipGroup;
    int id_movie;
    ChipDrawable chipDrawable;

    public MovieInformation(int id_movie) {
        this.id_movie = id_movie;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MovieInformationViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_movie_information, container, false);
        FindViewById(view);
        viewModel.setIdMovie(id_movie);
        loadMovieInformation();

        return view;
    }

    private void loadInformation(MovieCompleteResponse movieCompleteResponse) {
        Glide.with(getContext())
                .load(ApiConstants.IMAGE_API_PREFIX + movieCompleteResponse.getPosterPath())
                .apply(RequestOptions.centerCropTransform())
                .into(imageViewCover);
        imageViewCover.setClipToOutline(true);
        tvTitulo.setText(movieCompleteResponse.getTitle());
        String fecha = "("+movieCompleteResponse.getReleaseDate().substring(0,4)+")";
        tvFecha.setText(fecha);
        tvCalificacion.setText(String.valueOf(movieCompleteResponse.getVoteAverage())+"/10");
        tvDescripcion.setText(movieCompleteResponse.getOverview()+"...");
        tvLinea.setText(movieCompleteResponse.getTagline());
        tvCalificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingDialog ratingDialog = new RatingDialog(movieCompleteResponse.getId(),true);
                ratingDialog.show(getChildFragmentManager(),"RatingDialog");

            }
        });
        chipGroup.setChipSpacingVertical(10);


    }

    private void loadGenres(MovieCompleteResponse movieCompleteResponse) {
        generosList = movieCompleteResponse.getGenres();
        for(int j=0; j<generosList.size();j++){
             Chip chip = new Chip(chipGroup.getContext());
                chip.setText(generosList.get(j).getName());
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.BackGround,null)));
                chip.setChipStrokeWidth(1);
                chip.setChipStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent,null)));
                chip.setTextColor(getResources().getColor(R.color.white,null));
                chip.setTextSize(12);
                chip.setChipIconVisible(false);
                chipGroup.addView(chip);
        }
    }

    private void loadMovieInformation() {
        viewModel.getMovieInformation().observe(getActivity(), movieCompleteResponse -> {
           movieResponse= movieCompleteResponse;
            loadGenres(movieResponse);
            loadInformation(movieResponse);
        });
    }



    public void FindViewById(View view){
        imageViewCover = view.findViewById(R.id.imageViewCoverInformationTV);
        tvTitulo = view.findViewById(R.id.textViewTituloTV);
        tvFecha = view.findViewById(R.id.textViewFechaTV);
        tvDescripcion = view.findViewById(R.id.textViewDescripcionTV);
        tvGenero = view.findViewById(R.id.textViewGenerosTV);
        tvCalificacion = view.findViewById(R.id.textViewCalificacionTV);
        tvLinea = view.findViewById(R.id.textViewLineaTV);
        scrollView = view.findViewById(R.id.endView);
        chipGroup = view.findViewById(R.id.chipGroupTV);
        chipDrawable  = ChipDrawable.createFromResource(getContext(),R.xml.chip);
    }
}


