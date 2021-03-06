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
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.bobble.bobblesampleapp.database.Gifs;
import com.bobble.bobblesampleapp.database.Sticker;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.bobble.bobblesampleapp.singletons.BobbleEvent;
import com.bobble.bobblesampleapp.util.BobbleConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private BobblePrefs bobblePrefs;

    Intent sendIntent = new Intent(Intent.ACTION_SEND);
    Uri imageUri = null;

    public StickersAdapter(final Activity activity, List<Sticker> horizontalList) {
        this.list = horizontalList;
        this.activity =activity;
        //Sorting array according to required sequence

        for(int i = 0; i<list.size();i++){
            String name = list.get(i).getStickerName().substring(8);
            list.get(i).setStickerName(name);
        }
        Collections.sort(list, new Comparator<Sticker>() {
            @Override
            public int compare(Sticker o1, Sticker o2) {

                return Integer.parseInt(o1.getStickerName())-Integer.parseInt(o2.getStickerName());
            }
        });
        bobblePrefs = new BobblePrefs(activity);
        for (int i = 0; i < list.size()+1; i++) {
            if((i+1 )% 5 ==0 && i!=5){
                list.add(i,new Sticker());
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false);
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
                    //Bobble analytics
                    BobbleEvent.getInstance().log("Home Screen","Tap on sticker","tap_on_sticker",list.get(position).getStickerName(),System.currentTimeMillis()/1000);

                    ((MainActivity)activity).openSharingDialog(Integer.parseInt(String.valueOf(list.get(position).getId())),list.get(position).getPath(),"sticker");
                }
            });

            ((StickersAdapter.StickerViewHolder)holder).ivImage.setImageURI(FileProvider.getUriForFile(activity, "com.bobble.bobblesampleapp.fileprovider", new File(list.get(position).getPath())));

            ((StickerViewHolder)holder).ivfacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(list.get(position).getPath(), BobbleConstants.FACEBOOK_PACKAGE_NAME,BobbleConstants.FACEBOOK_CLASS_NAME);
                }
            });

            ((StickerViewHolder)holder).ivWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(list.get(position).getPath(),BobbleConstants.WHATSAPP_PACKAGE_NAME,BobbleConstants.WHATSAPP_CLASS_NAME);
                }
            });

        }else if(holder instanceof StickersAdapter.FooterViewHolder){
            ((StickersAdapter.FooterViewHolder)holder).ivGoogleplaystore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Growth App analytics
                    BobbleEvent.getInstance().log(BobbleConstants.HOME_SCREEN, "click on get more love stickers", "sticker_scroll_go_to_play_store", "", System.currentTimeMillis() / 1000);
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse(BobbleConstants.GOOGLE_PLAY_STORE_LINK_TO_BOOBLE));
                    activity.startActivity(i);
                }
            });
        }
    }
    void share(String path,String packageName, String activityName){
        File f = new File(path);
        Uri uri =null;
        File newFile = new File(path);
        try {
            uri = FileProvider.getUriForFile(activity, "com.bobble.bobblesampleapp.fileprovider", newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(f);
        }*/
        PackageManager pm=activity.getPackageManager();
        try{
        sendIntent.setType("image/gif");
        ComponentName name = new ComponentName(packageName,activityName);
        Log.d("", "ComponentName : " + name);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shareIntent.setType("image/gif");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setComponent(name);
            if(packageName.contains("whatsapp")){
                BobbleEvent.getInstance().log("Home Screen","Share sticker","share_gif_whatsapp_icon",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);
                activity.startActivity(shareIntent);
            }else{
                BobbleEvent.getInstance().log("Home Screen","Share sticker","share_gif_fb_icon",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);
                activity.startActivity(shareIntent);
            }
        }catch(PackageManager.NameNotFoundException e){
            Toast.makeText(activity,"Application not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position)!=null && !TextUtils.isEmpty(list.get(position).getStickerName())){
            return TYPE_ITEM;
        }
        return TYPE_FOOTER;
    }

    public class StickerViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llRoot;
        public ImageView ivImage;
        public ImageView ivfacebook;
        public ImageView ivWhatsapp;

        public StickerViewHolder(View view) {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.ivImage);
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