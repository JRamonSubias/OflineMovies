package com.esime.oflinemovies.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.ViewModel.MovieViewModel;
import com.google.android.material.transition.platform.MaterialSharedAxis;


import java.util.List;
public class MovieFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private List<MovieEntity> movieList;
    private List<TvEntity> tvList;
    MyMovieRecyclerViewAdapter adapter;
    TvRecyclerViewAdapter adapterTv;
    MovieViewModel movieViewModel;
    RecyclerView recyclerViewTv;
    private List<MovieEntity> listMovie;
    private boolean aptoParaCargar;
    private int page=1;
    ConstraintLayout constraintLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieViewModel =  new ViewModelProvider(this).get(MovieViewModel.class);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
            constraintLayout = view.findViewById(R.id.ConstrainLayoutList);
            Context context = view.getContext();
            RecyclerView recyclerViewMovie = view.findViewById(R.id.listMoviePopular);


                LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
                recyclerViewMovie.setLayoutManager(linearLayoutManager);

            adapter = new MyMovieRecyclerViewAdapter(getActivity(),movieList);
            recyclerViewMovie.setAdapter(adapter);
            adapter.SetOnClickListener(v -> {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                MovieInformation movieInformation = new MovieInformation(movieList.get(recyclerViewMovie.getChildAdapterPosition(v)).getId());
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                        .replace(R.id.fragment,movieInformation)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            });

        loadMovies();

            recyclerViewTv = view.findViewById(R.id.listTvPopular);
            LinearLayoutManager linearLayoutManagerTv = new LinearLayoutManager(context);
            linearLayoutManagerTv.setOrientation(linearLayoutManagerTv.HORIZONTAL);
            recyclerViewTv.setLayoutManager(linearLayoutManagerTv);
            adapterTv = new TvRecyclerViewAdapter(getActivity(),tvList);
            recyclerViewTv.setAdapter(adapterTv);
            adapterTv.setOnClickListener(v -> {

                FragmentManager manager = getActivity().getSupportFragmentManager();
                TvInformation tvInformation = new TvInformation(tvList.get( recyclerViewTv.getChildAdapterPosition(v)).getId());
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                        .replace(R.id.fragment,tvInformation)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            });
            loadTv();
        return view;
    }

    private void loadTv() {
        movieViewModel.getPopularTv().observe(getActivity(), listResourceTv -> {
            tvList = listResourceTv.data;
            adapterTv.setData(tvList);
        });
    }

    private void loadMovies(){
        movieViewModel.getPopularMovie().observe(getActivity(), movieEntities -> {
            movieList = movieEntities;
            adapter.setData(movieList);
        });
    }


}
