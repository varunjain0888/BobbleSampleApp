package com.bobble.bobblesampleapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.adapters.GifShareAsAdapter;
import com.bobble.bobblesampleapp.adapters.GifsAdapter;
import com.bobble.bobblesampleapp.adapters.MoreStickersAdapter;
import com.bobble.bobblesampleapp.adapters.StickersAdapter;
import com.bobble.bobblesampleapp.custom.CircleProgressBarDrawable;
import com.bobble.bobblesampleapp.custom.PaddingItemDecoration;
import com.bobble.bobblesampleapp.database.repository.GifsRepository;
import com.bobble.bobblesampleapp.database.repository.MorePacksRepository;
import com.bobble.bobblesampleapp.database.repository.StickersRepository;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.bobble.bobblesampleapp.singletons.BobbleEvent;
import com.bobble.bobblesampleapp.util.BobbleConstants;
import com.bobble.bobblesampleapp.util.SaveUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
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

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    public void openSharingDialog(int resId,String path) {
        stickerPreview.setImageResource(resId);
        llShare.setVisibility(View.VISIBLE);
        llShare.startAnimation(slide_up);
        File f = new File(path);
        Uri uri = Uri.fromFile(f);
        onShare(uri);
    }

    public void onShare(Uri uri) {

        recyclerViewShare.setLayoutManager(new GridLayoutManager(context,4));
        gifShareAsAdapter = new GifShareAsAdapter(this,uri);
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
}
