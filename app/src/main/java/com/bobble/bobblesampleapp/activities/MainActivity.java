package com.bobble.bobblesampleapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.adapters.GifShareAsAdapter;
import com.bobble.bobblesampleapp.adapters.GifsAdapter;
import com.bobble.bobblesampleapp.adapters.MoreStickersAdapter;
import com.bobble.bobblesampleapp.adapters.StickersAdapter;
import com.bobble.bobblesampleapp.custom.PaddingItemDecoration;
import com.bobble.bobblesampleapp.database.repository.GifsRepository;
import com.bobble.bobblesampleapp.database.repository.MorePacksRepository;
import com.bobble.bobblesampleapp.database.repository.StickersRepository;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.bobble.bobblesampleapp.singletons.BobbleEvent;
import com.bobble.bobblesampleapp.util.AppUtils;
import com.bobble.bobblesampleapp.util.BobbleConstants;

import java.io.File;


import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    public static int GIF_WHATSAPP_SUCCESS = 2001;
    public static int GIF_FACEBOOK_SUCCESS = 2002;
    public static int GIF_POPUP__SUCCESS = 2003;
    public static int STICKER_WHATSAPP_SUCCESS = 2004;
    public static int STICKER_FACEBOOK_SUCCESS = 2005;
    public static int STICKER_POPUP_SUCCESS = 2006;
    private RecyclerView rvGifs;
    private LinearLayoutManager mgifLayoutManager;
    private LinearLayoutManager mstickerLayoutManager;
    private LinearLayoutManager mMoreLayoutManager;
    private GifsAdapter gifsAdapter;
    private StickersAdapter stickersAdapter;
    private MoreStickersAdapter moreStickersAdapter;
    private RecyclerView rvSticker;
    private Context context;
    private android.view.animation.Animation slide_up, slide_down;
    private LinearLayout llShare;
    private RecyclerView recyclerViewShare;
    private RecyclerView rvMore;
    private GifImageView stickerPreview;
    private RecyclerView recyclerViewUse;
    private GifShareAsAdapter gifShareAsAdapter;
    private BobblePrefs bobblePrefs;
    private ImageView ivGoogleplaystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        //Hack app analytics
        BobbleEvent.getInstance().log(BobbleConstants.HOME_SCREEN, "Landed on the home screen", "home_screen_view", "", System.currentTimeMillis() / 1000);
        getIds();
        bobblePrefs = new BobblePrefs(this);
        slide_up = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(context, R.anim.slide_down);

    }

    private void getIds() {
        llShare = (LinearLayout) findViewById(R.id.llShare);
        rvGifs = (RecyclerView) findViewById(R.id.rvGifs);
        rvMore = (RecyclerView) findViewById(R.id.rvMore);
        stickerPreview = (GifImageView) findViewById(R.id.stickerPreview);
        rvSticker = (RecyclerView) findViewById(R.id.rvSticker);
        recyclerViewShare = (RecyclerView) findViewById(R.id.recyclerViewShare);
        recyclerViewUse = (RecyclerView) findViewById(R.id.recyclerViewUse);
        ivGoogleplaystore = (ImageView) findViewById(R.id.ivGoogleplaystore);

        mgifLayoutManager = new LinearLayoutManager(getApplicationContext(), GridLayoutManager.HORIZONTAL, false);
        mstickerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mMoreLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);


        rvGifs.setLayoutManager(mgifLayoutManager);
        rvSticker.setLayoutManager(mstickerLayoutManager);
        rvMore.setLayoutManager(mMoreLayoutManager);

        gifsAdapter = new GifsAdapter(this, GifsRepository.getAllGifs(context));
        rvGifs.setAdapter(gifsAdapter);


        stickersAdapter = new StickersAdapter(this, StickersRepository.getAllsticker(context));
        rvSticker.setAdapter(stickersAdapter);


        moreStickersAdapter = new MoreStickersAdapter(this, MorePacksRepository.getAllPacks(context));
        rvMore.setAdapter(moreStickersAdapter);


        rvGifs.addItemDecoration(new PaddingItemDecoration(50));
        rvSticker.addItemDecoration(new PaddingItemDecoration(50));
        rvMore.addItemDecoration(new PaddingItemDecoration(50));

        ivGoogleplaystore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse(BobbleConstants.GOOGLE_PLAY_STORE_LINK_TO_BOOBLE));
                startActivity(i);
            }
        });

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    public void openSharingDialog(int resId,String path,String type) {
        stickerPreview.setImageResource(resId);
        llShare.setVisibility(View.VISIBLE);
        llShare.startAnimation(slide_up);
        File f = new File(path);
        Uri uri =null;
        File newFile = new File(path);
        uri = FileProvider.getUriForFile(this, "com.bobble.bobblesampleapp.fileprovider", newFile);
       /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(f);
        }*/
        onShare(uri,type);
    }

    public void onShare(Uri uri,String type) {

        recyclerViewShare.setLayoutManager(new GridLayoutManager(context,4));
        gifShareAsAdapter = new GifShareAsAdapter(this,uri,type);
        recyclerViewShare.setAdapter(gifShareAsAdapter);
        llShare.setVisibility(View.VISIBLE);
        llShare.startAnimation(slide_up);
    }

    @Override
    public void onBackPressed() {
        if(llShare.getVisibility()==View.VISIBLE){
            llShare.setVisibility(View.GONE);
            llShare.startAnimation(slide_down);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GIF_FACEBOOK_SUCCESS){
            /*if(resultCode==RESULT_OK){
            }else {
                Toast.makeText(context,"Sharing cancelled",Toast.LENGTH_SHORT).show();
            }*/
            BobbleEvent.getInstance().log("Home Screen","Share gif","share_sticker_fb_icon",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);

        }else if(requestCode==GIF_WHATSAPP_SUCCESS){
            /*if(resultCode==RESULT_OK){
            }else {
                Toast.makeText(context,"Sharing cancelled",Toast.LENGTH_SHORT).show();
            }*/
            BobbleEvent.getInstance().log("Home Screen","Share gif","share_sticker_whatsapp_icon",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);

        }else if(requestCode==GIF_POPUP__SUCCESS){
            BobbleEvent.getInstance().log("Home Screen","Share gif","share_sticker_popup",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);

            /*if(resultCode==RESULT_OK){
            }else {
                Toast.makeText(context,"Sharing cancelled",Toast.LENGTH_SHORT).show();
            }*/
        }else if(requestCode==STICKER_FACEBOOK_SUCCESS){
            /*if(resultCode==RESULT_OK){
            }else {
                Toast.makeText(context,"Sharing cancelled",Toast.LENGTH_SHORT).show();
            }*/
            BobbleEvent.getInstance().log("Home Screen","Share sticker","share_gif_fb_icon",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);

        }else if(requestCode==STICKER_WHATSAPP_SUCCESS){
            /*if(resultCode==RESULT_OK){
            }else {
                Toast.makeText(context,"Sharing cancelled",Toast.LENGTH_SHORT).show();
            }*/
            BobbleEvent.getInstance().log("Home Screen","Share sticker","share_gif_whatsapp_icon",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);

        }else if(requestCode==STICKER_POPUP_SUCCESS){
            /*if(resultCode==RESULT_OK){
            }else {
                Toast.makeText(context,"Sharing cancelled",Toast.LENGTH_SHORT).show();
            }*/
            BobbleEvent.getInstance().log("Home Screen","Share sticker","share_gif_popup",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);

        }
    }
}
