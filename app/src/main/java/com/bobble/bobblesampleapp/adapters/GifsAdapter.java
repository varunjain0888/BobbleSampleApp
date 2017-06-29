package com.bobble.bobblesampleapp.adapters;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.activities.MainActivity;
import com.bobble.bobblesampleapp.database.Gifs;
import com.bobble.bobblesampleapp.database.Morepacks;
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

public class GifsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_FOOTER = 1;

    private static final int TYPE_ITEM = 2;

    private List<Gifs> list;

    private Activity activity;

    private BobblePrefs bobblePrefs;


    Intent sendIntent = new Intent(Intent.ACTION_SEND);
    Uri imageUri = null;
    public GifsAdapter(Activity activity ,List<Gifs> horizontalList) {
        this.list = horizontalList;
        this.activity = activity;

        //Sorting array according to required sequence

            for(int i = 0; i<list.size();i++){
                String name = list.get(i).getGifName().substring(4);
                list.get(i).setGifName(name);
            }
            Collections.sort(list, new Comparator<Gifs>() {
                @Override
                public int compare(Gifs o1, Gifs o2) {

                    return Integer.parseInt(o1.getGifName())-Integer.parseInt(o2.getGifName());
                }
            });

            bobblePrefs = new BobblePrefs(activity);
            for (int i = 0; i < list.size()+1; i++) {
            if((i+1 )% 5 ==0 && i!=5){
                list.add(i,new Gifs());
            }
        }
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
                    //Bobble Analytics
                    BobbleEvent.getInstance().log("Home Screen","Tap on gif","tap_on_gif",list.get(position).getGifName(),System.currentTimeMillis()/1000);
                    ((MainActivity)activity).openSharingDialog(Integer.parseInt(String.valueOf(list.get(position).getId())),list.get(position).getPath(),"gif");
                }
            });
            ((GifViewHolder)holder).ivImage.setBackgroundResource(Integer.parseInt(String.valueOf(list.get(position).getId())));
            ((GifViewHolder)holder).ivfacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(list.get(position).getPath(), BobbleConstants.FACEBOOK_PACKAGE_NAME,BobbleConstants.FACEBOOK_CLASS_NAME,((GifsAdapter.GifViewHolder)holder),list.get(position).getGifName());
                }
            });

            ((GifsAdapter.GifViewHolder)holder).ivWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(list.get(position).getPath(),BobbleConstants.WHATSAPP_PACKAGE_NAME,BobbleConstants.WHATSAPP_CLASS_NAME,((GifsAdapter.GifViewHolder)holder),list.get(position).getGifName());
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
    void share(String path,String packageName, String activityName,GifViewHolder gifViewHolder,String id){
        File f = new File(path);

        Uri uri =null;

        File newFile = new File(path);

        uri = FileProvider.getUriForFile(activity, "com.bobble.bobblesampleapp.fileprovider", newFile);

        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(f);
        }*/
        PackageManager pm=activity.getPackageManager();
        try{
            sendIntent.setType("image/gif");
            ComponentName name = new ComponentName(packageName,activityName);
            Log.d("", "ComponentName : " + name);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            PackageInfo info=pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            shareIntent.setType("image/gif");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setComponent(name);
            if(packageName.contains("whatsapp")){
                activity.startActivityForResult(shareIntent, MainActivity.GIF_WHATSAPP_SUCCESS);
            }else{
                activity.startActivityForResult(shareIntent, MainActivity.GIF_FACEBOOK_SUCCESS);
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
        if(list.get(position)!=null && !TextUtils.isEmpty(list.get(position).getGifName())){
            return TYPE_ITEM;
        }
        return TYPE_FOOTER;
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