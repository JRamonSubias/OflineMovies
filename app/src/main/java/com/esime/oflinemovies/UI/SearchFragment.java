package com.esime.oflinemovies.UI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.esime.oflinemovies.Data.Local.Entity.SearchEntity;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;
import com.esime.oflinemovies.ViewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private List<SearchEntity> searchList;
    private SearchRecyclerViewAdapter searchAdapter;
    private SearchViewModel searchViewModel;
    RecyclerView recyclerViewSearch;
    EditText EtSearch;
    ImageView ivSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new SearchViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_search, container, false);

       EtSearch = view.findViewById(R.id.editText_Search);
       ivSearch = view.findViewById(R.id.imageViewSearch);

       recyclerViewSearch = view.findViewById(R.id.recyclerView_Search);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),3);
        recyclerViewSearch.setLayoutManager(gridLayoutManager);

        searchAdapter = new SearchRecyclerViewAdapter(getActivity(),searchList);
        recyclerViewSearch.setAdapter(searchAdapter);
        searchAdapter.setOnClickListener(v -> {

            if(searchList.get(recyclerViewSearch.getChildAdapterPosition(v)).getMediaType().equals("movie")){

                FragmentManager manager = getActivity().getSupportFragmentManager();
                MovieInformation movieInformation = new MovieInformation(searchList.get( recyclerViewSearch.getChildAdapterPosition(v)).getId());
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                        .replace(R.id.fragment,movieInformation)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();


            }else if(searchList.get(recyclerViewSearch.getChildAdapterPosition(v)).getMediaType().equals("tv")){

                FragmentManager manager = getActivity().getSupportFragmentManager();
                TvInformation tvInformation = new TvInformation(searchList.get( recyclerViewSearch.getChildAdapterPosition(v)).getId());
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                        .replace(R.id.fragment,tvInformation)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }else {
                Toast.makeText(getActivity(), "No Encontrado", Toast.LENGTH_SHORT).show();
            }
        });

        EtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchViewModel.InsertSearchQuery(s.toString());
                loadSearchEntity();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

       return view;
    }

    private void loadSearchEntity() {
        searchViewModel.getListLiveDataSearch().observe(getActivity(), searchEntityList -> {
            searchList = searchEntityList;
            searchAdapter.setData(searchList);
        });
    }

}
