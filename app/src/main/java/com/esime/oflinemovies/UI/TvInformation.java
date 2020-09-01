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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esime.oflinemovies.Data.Local.Entity.GenerosEntity;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.Data.Remoto.model.TvInformationResponse;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.RatingDialog;
import com.esime.oflinemovies.ViewModel.TvInformationViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvInformation extends Fragment {
    ImageView imageViewCover;
    TextView tvTitulo, tvFecha, tvDescripcion, tvGenero, tvCalificacion, tvLinea;
    TvInformationViewModel viewModel;
    TvInformationResponse tvResponse;
    List<GenerosEntity> generosList;
    int id_tv;
    ChipGroup chipGroupTV;


    public TvInformation(int id_tv) {
        this.id_tv = id_tv;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TvInformationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_information, container, false);
        FindViewById(view);
        viewModel.setIdMovie(id_tv);
        loadTvInformation();
        return  view;
    }

    private void loadTvInformation() {
        viewModel.getTvinformation().observe(getActivity(), tvInformationResponse ->{
            tvResponse = tvInformationResponse;
            loadGenres(tvResponse);
            loadInformation(tvResponse);

        });
    }

    private void loadInformation(TvInformationResponse tvResponse) {
        Glide.with(getContext())
                .load(ApiConstants.IMAGE_API_PREFIX + tvResponse.getPosterPath())
                .apply(RequestOptions.centerCropTransform())
                .into(imageViewCover);
        imageViewCover.setClipToOutline(true);
        tvTitulo.setText(tvResponse.getName());
        tvFecha.setText(tvResponse.getFirstAirDate().substring(0,4));
        tvCalificacion.setText(String.valueOf(tvResponse.getVoteAverage())+"/10");
        tvCalificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingDialog ratingDialog = new RatingDialog(tvResponse.getId(),false);
                ratingDialog.show(getChildFragmentManager(),"RatingBar");
            }
        });
        tvDescripcion.setText(tvResponse.getOverview()+"...");
        tvLinea.setText(tvResponse.getStatus());



    }

    private void loadGenres(TvInformationResponse tvResponse) {
        generosList = tvResponse.getGenres();
        for(int j=0; j<generosList.size();j++){
            Chip chip = new Chip(chipGroupTV.getContext());
            chip.setText(generosList.get(j).getName());
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.BackGround,null)));
            chip.setChipStrokeWidth(1);
            chip.setChipStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent,null)));
            chip.setTextColor(getResources().getColor(R.color.white,null));
            chip.setTextSize(12);
            chip.setChipIconVisible(false);
            chipGroupTV.addView(chip);
        }

    }

    private void FindViewById(View view) {
        imageViewCover = view.findViewById(R.id.imageViewCoverInformationTV);
        tvTitulo = view.findViewById(R.id.textViewTituloTV);
        tvFecha = view.findViewById(R.id.textViewFechaTV);
        tvDescripcion = view.findViewById(R.id.textViewDescripcionTV);
        tvCalificacion = view.findViewById(R.id.textViewCalificacionTV);
        tvLinea = view.findViewById(R.id.textViewLineaTV);
        chipGroupTV = view.findViewById(R.id.chipGroupTV);
    }
}
