package com.esime.oflinemovies.UI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esime.oflinemovies.Data.Local.Entity.SearchEntity;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolderSearch> implements View.OnClickListener{
    private List<SearchEntity> mValues;
    Context context;
    View.OnClickListener listener;


    public SearchRecyclerViewAdapter(Context context, List<SearchEntity> searchEntityList){
        this.context = context;
        mValues = searchEntityList;
    }


    @NonNull
    @Override
    public SearchRecyclerViewAdapter.ViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.ViewHolderSearch holder, int position) {
        holder.mItem = mValues.get(position);
        String url = ApiConstants.IMAGE_API_PREFIX + holder.mItem.getPosterPath();
        Glide.with(context)
                .load(ApiConstants.IMAGE_API_PREFIX + holder.mItem.getPosterPath())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageViewSearch);
        holder.imageViewSearch.setClipToOutline(true);
    }


    public void setData(List<SearchEntity> search){
        this.mValues = search;
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolderSearch extends RecyclerView.ViewHolder {
        public ImageView imageViewSearch;
        public SearchEntity mItem;

        public ViewHolderSearch(@NonNull View itemView) {
            super(itemView);
            imageViewSearch = itemView.findViewById(R.id.imageViewRecyclerSearch);
        }
    }
}
