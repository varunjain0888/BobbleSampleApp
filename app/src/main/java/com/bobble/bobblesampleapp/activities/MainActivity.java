package com.bobble.bobblesampleapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.bobble.bobblesampleapp.custom.PaddingItemDecoration;
import com.bobble.bobblesampleapp.database.repository.GifsRepository;
import com.bobble.bobblesampleapp.database.repository.StickersRepository;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getIds();
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


        moreStickersAdapter = new MoreStickersAdapter(this, new ArrayList<String>());
        rvMore.setAdapter(moreStickersAdapter);


        rvGifs.addItemDecoration(new PaddingItemDecoration(50));
        rvSticker.addItemDecoration(new PaddingItemDecoration(50));
        rvMore.addItemDecoration(new PaddingItemDecoration(50));
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    public void openSharingDialog(int resId) {
        stickerPreview.setImageResource(resId);
        llShare.setVisibility(View.VISIBLE);
        llShare.startAnimation(slide_up);
        onShare();
    }

    public void onShare() {
        recyclerViewShare.setLayoutManager(new GridLayoutManager(context,4));
        gifShareAsAdapter = new GifShareAsAdapter(this);
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
