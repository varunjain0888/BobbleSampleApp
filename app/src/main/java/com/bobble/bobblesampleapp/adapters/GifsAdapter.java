package com.bobble.bobblesampleapp.adapters;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.activities.MainActivity;
import com.bobble.bobblesampleapp.database.Gifs;

import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Varun Jain on 23/06/2017.
 */

public class GifsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_FOOTER = 1;

    private static final int TYPE_ITEM = 2;

    private List<Gifs> list;

    private Activity activity;

    public GifsAdapter(Activity activity ,List<Gifs> horizontalList) {
        this.list = horizontalList;
        this.activity = activity;
        Collections.reverse(list);
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gifs, parent, false);
            return new GifViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_gifs, parent, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_gifs, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof GifViewHolder){
            ((GifViewHolder)holder).llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)activity).openSharingDialog();
                }
            });
            ((GifViewHolder)holder).ivImage.setBackgroundResource(Integer.parseInt(String.valueOf(list.get(position).getId())));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM;
        } else if (position == list.size()-1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    public class GifViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llRoot;
        public GifImageView ivImage;
        public GifViewHolder(View view) {
            super(view);

            llRoot = (LinearLayout) view.findViewById(R.id.llRoot);
            ivImage = (GifImageView)view.findViewById(R.id.ivImage);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public FooterViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.textview1);

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public HeaderViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.textview1);

        }
    }
}