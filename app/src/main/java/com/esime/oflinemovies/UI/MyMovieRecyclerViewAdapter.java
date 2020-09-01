package com.esime.oflinemovies.UI;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;


import java.util.List;

public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> implements  View.OnClickListener{

    private List<MovieEntity> mValues;
    Context context;
    View.OnClickListener listener;


    public MyMovieRecyclerViewAdapter(Context context, List<MovieEntity> items ) {
        this.context = context;
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(context)
                .load(ApiConstants.IMAGE_API_PREFIX + holder.mItem.getPosterPath())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageViewCover);
        holder.imageViewCover.setClipToOutline(true);
    }

    public void setData(List<MovieEntity> movies){
        this.mValues = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        }else{
            return 0;
        }
    }

    public void SetOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MovieEntity mItem;
        public ImageView imageViewCover;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewCover = view.findViewById(R.id.imageViewCoverInformationTV);

        }
    }
}
