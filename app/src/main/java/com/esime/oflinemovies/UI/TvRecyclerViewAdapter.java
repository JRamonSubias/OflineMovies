package com.esime.oflinemovies.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.R;

import java.util.List;

public class TvRecyclerViewAdapter extends RecyclerView.Adapter<TvRecyclerViewAdapter.ViewHolderTV> implements View.OnClickListener{

    private List<TvEntity> mValues;
    Context context;
    View.OnClickListener listener;

    public TvRecyclerViewAdapter(Context context, List<TvEntity> items){
        this.context = context;
        mValues = items;

    }


    @NonNull
    @Override
    public TvRecyclerViewAdapter.ViewHolderTV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tv,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderTV(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvRecyclerViewAdapter.ViewHolderTV holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(context)
                .load(ApiConstants.IMAGE_API_PREFIX + holder.mItem.getPosterPath())
                 .apply(RequestOptions.centerCropTransform())
                .into(holder.imageViewTv);
        holder.imageViewTv.setClipToOutline(true);

    }

    public void setData (List<TvEntity> tvEntities){
        this.mValues = tvEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        }else return 0;
    }

    @Override
    public void onClick(View v) {
        if(listener !=null){
            listener.onClick(v); {
            }
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolderTV extends RecyclerView.ViewHolder {
        public TvEntity mItem;
        public ImageView imageViewTv;

        public ViewHolderTV(@NonNull View itemView) {
            super(itemView);
            imageViewTv = itemView.findViewById(R.id.imageViewCoverTv);
        }
    }
}


