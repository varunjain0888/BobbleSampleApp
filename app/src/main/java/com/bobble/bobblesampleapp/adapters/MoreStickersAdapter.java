package com.bobble.bobblesampleapp.adapters;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.activities.AdBoardActivity;
import com.bobble.bobblesampleapp.activities.MainActivity;
import com.bobble.bobblesampleapp.database.Morepacks;

import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Varun Jain on 23/06/2017.
 */

public class MoreStickersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_FOOTER = 1;

    private static final int TYPE_ITEM = 2;

    private List<Morepacks> list;

    private Activity activity;

    public MoreStickersAdapter(Activity activity , List<Morepacks> horizontalList) {
        this.list = horizontalList;
        this.activity = activity;
        //Collections.reverse(list);
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_packs, parent, false);
            return new MorePacksViewHolder(itemView);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MorePacksViewHolder){
            ((MorePacksViewHolder)holder).llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, AdBoardActivity.class);
                    intent.putExtra("position",position);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(activity,(View)((MorePacksViewHolder)holder).ivImage,"pack");
                    activity.startActivity(intent, options.toBundle());
                    //activity.startActivity(intent);
                }
            });
            ((MorePacksViewHolder)holder).ivImage.setBackgroundResource(Integer.parseInt(String.valueOf(list.get(position).getId())));
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
        } else if (position == 10) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    public class MorePacksViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llRoot;
        public GifImageView ivImage;
        public ImageView ivfacebook;
        public ImageView ivWhatsapp;
        public MorePacksViewHolder(View view) {
            super(view);

            llRoot = (LinearLayout) view.findViewById(R.id.llRoot);
            ivImage = (GifImageView)view.findViewById(R.id.ivImage);
            ivfacebook = (ImageView)view.findViewById(R.id.ivfacebook);
            ivWhatsapp = (ImageView)view.findViewById(R.id.ivWhatsapp);
            ivfacebook.setVisibility(View.INVISIBLE);
            ivWhatsapp.setVisibility(View.INVISIBLE);
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