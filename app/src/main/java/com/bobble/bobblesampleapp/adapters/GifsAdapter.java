package com.bobble.bobblesampleapp.adapters;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.activities.MainActivity;
import com.bobble.bobblesampleapp.database.Gifs;
import com.bobble.bobblesampleapp.database.Morepacks;
import com.bobble.bobblesampleapp.singletons.BobbleEvent;
import com.bobble.bobblesampleapp.util.BobbleConstants;

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

    Intent sendIntent = new Intent(Intent.ACTION_SEND);
    Uri imageUri = null;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof GifViewHolder){
            ((GifViewHolder)holder).llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)activity).openSharingDialog(Integer.parseInt(String.valueOf(list.get(position).getId())),list.get(position).getPath());
                }
            });
            ((GifViewHolder)holder).ivImage.setBackgroundResource(Integer.parseInt(String.valueOf(list.get(position).getId())));
            ((GifViewHolder)holder).ivfacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                                BitmapFactory.decodeResource(activity.getResources(),Integer.parseInt(String.valueOf(list.get(position).getId()))), null, null));
                    } catch (NullPointerException e) {
                    }
                    share(imageUri, BobbleConstants.FACEBOOK_PACKAGE_NAME,BobbleConstants.FACEBOOK_CLASS_NAME,((GifsAdapter.GifViewHolder)holder));
                }
            });

            ((GifsAdapter.GifViewHolder)holder).ivWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                                BitmapFactory.decodeResource(activity.getResources(),Integer.parseInt(String.valueOf(list.get(position).getId()))), null, null));
                    } catch (NullPointerException e) {
                    }
                    share(imageUri,BobbleConstants.WHATSAPP_PACKAGE_NAME,BobbleConstants.WHATSAPP_CLASS_NAME,((GifsAdapter.GifViewHolder)holder));
                }
            });

        }else if(holder instanceof FooterViewHolder){
            ((FooterViewHolder)holder).ivGoogleplaystore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Hack app analytics
                    BobbleEvent.getInstance().log(BobbleConstants.HOME_SCREEN, "click on get more love gifs", "google_play_store_view", "", System.currentTimeMillis() / 1000);
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse(BobbleConstants.GOOGLE_PLAY_STORE_LINK_TO_BOOBLE));
                    activity.startActivity(i);
                }
            });
        }
    }
    void share(Uri uri,String packageName, String activityName,GifViewHolder gifViewHolder){
        sendIntent.setType("image/gif");
        ComponentName name = new ComponentName(packageName,activityName);
        Log.d("", "ComponentName : " + name);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shareIntent.setType("image/gif");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setComponent(name);
        activity.startActivity(shareIntent);
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
        public ImageView ivfacebook;
        public ImageView ivWhatsapp;
        public ProgressBar ivProgressBar;
        public GifViewHolder(View view) {
            super(view);

            llRoot = (LinearLayout) view.findViewById(R.id.llRoot);
            ivImage = (GifImageView)view.findViewById(R.id.ivImage);
            ivfacebook = (ImageView)view.findViewById(R.id.ivfacebook);
            ivWhatsapp = (ImageView)view.findViewById(R.id.ivWhatsapp);
            ivProgressBar = (ProgressBar)view.findViewById(R.id.ivProgressBar);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivGoogleplaystore;

        public FooterViewHolder(View view) {
            super(view);

            ivGoogleplaystore = (ImageView) view.findViewById(R.id.ivGoogleplaystore);

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