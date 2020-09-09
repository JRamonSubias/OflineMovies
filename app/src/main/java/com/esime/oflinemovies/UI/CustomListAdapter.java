package com.esime.oflinemovies.UI;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esime.oflinemovies.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int id;
    private List <String>items ;

    public CustomListAdapter(Context context, int textViewResourceId , List<String> list)
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = mView.findViewById(R.id.textViewListAdapter);

        if(items.get(position) != null)
        {
            text.setTextColor(Color.WHITE);
            text.setText(items.get(position));



        }

        return mView;
    }

}