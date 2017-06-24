package com.bobble.bobblesampleapp.adapters;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.activities.MainActivity;
import com.bobble.bobblesampleapp.database.Sticker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Varun Jain on 23/06/2017.
 */

public class StickersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_FOOTER = 1;

    private static final int TYPE_ITEM = 2;

    private List<Sticker> list;

    private Activity activity;

    Intent sendIntent = new Intent(Intent.ACTION_SEND);
    Uri imageUri = null;

    public StickersAdapter(final Activity activity, List<Sticker> horizontalList) {
        this.list = horizontalList;
        this.activity =activity;
        Collections.reverse(list);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gifs, parent, false);
            return new StickersAdapter.StickerViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_gifs, parent, false);
            return new StickersAdapter.HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_gifs, parent, false);
            return new StickersAdapter.FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof StickersAdapter.StickerViewHolder){
            ((StickersAdapter.StickerViewHolder)holder).llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)activity).openSharingDialog(Integer.parseInt(String.valueOf(list.get(position).getId())),list.get(position).getPath());
                }
            });
            ((StickersAdapter.StickerViewHolder)holder).ivImage.setBackgroundResource(Integer.parseInt(String.valueOf(list.get(position).getId())));

            ((StickerViewHolder)holder).ivfacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                                BitmapFactory.decodeResource(activity.getResources(),Integer.parseInt(String.valueOf(list.get(position).getId()))), null, null));
                    } catch (NullPointerException e) {
                    }
                    share(imageUri,"com.facebook.katana","com.facebook.composer.shareintent.ImplicitShareIntentHandlerDefaultAlias");
                }
            });

            ((StickerViewHolder)holder).ivWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                                BitmapFactory.decodeResource(activity.getResources(),Integer.parseInt(String.valueOf(list.get(position).getId()))), null, null));
                    } catch (NullPointerException e) {
                    }
                    share(imageUri,"com.whatsapp","com.whatsapp.ContactPicker");
                }
            });

        }else if(holder instanceof StickersAdapter.FooterViewHolder){
            ((StickersAdapter.FooterViewHolder)holder).ivGoogleplaystore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.touchtalent.bobbleapp"));
                    activity.startActivity(i);
                }
            });
        }
    }
    void share(Uri uri,String packageName, String activityName){
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

    public class StickerViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llRoot;
        public GifImageView ivImage;
        public ImageView ivfacebook;
        public ImageView ivWhatsapp;

        public StickerViewHolder(View view) {
            super(view);
            ivImage = (GifImageView)view.findViewById(R.id.ivImage);
            llRoot = (LinearLayout) view.findViewById(R.id.llRoot);
            ivfacebook = (ImageView)view.findViewById(R.id.ivfacebook);
            ivWhatsapp = (ImageView)view.findViewById(R.id.ivWhatsapp);
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