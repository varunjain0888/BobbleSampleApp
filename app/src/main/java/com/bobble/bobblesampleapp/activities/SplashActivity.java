package com.bobble.bobblesampleapp.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bobble.bobblesampleapp.BobbleSampleApp;
import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.database.DaoMaster;
import com.bobble.bobblesampleapp.database.DaoSession;
import com.bobble.bobblesampleapp.database.Gifs;
import com.bobble.bobblesampleapp.database.Sticker;
import com.bobble.bobblesampleapp.database.repository.GifsRepository;
import com.bobble.bobblesampleapp.database.repository.StickersRepository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by varunjain on 6/23/17.
 */

public class SplashActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private final int CONTACTS_REQUEST_CODE = 3000;
    private final int SPLASH_SCREEN_DELAY = 2000;
    private String TAG = SplashActivity.class.getSimpleName();
    private Context context;
    private GifImageView draweeView;
    private Handler mSplashDelayHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        context = this;
        getIds();
        setData();
        mSplashDelayHandler = new Handler();
        mSplashDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               requestPermission();
            }
        },SPLASH_SCREEN_DELAY);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "inside contact permission");
                requestContactsPermission();
            }else{
                openNextActivity();
            }
        }else{
            openNextActivity();
        }
    }

    private void setData() {
        try {
            draweeView.setImageDrawable(new GifDrawable(getAssets(), "bobble_splash.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getIds() {
            draweeView = (GifImageView) findViewById(R.id.simpleDraweeView);
    }

    private void requestContactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.GET_ACCOUNTS)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i("test",
                    "Displaying external storage permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS}, CONTACTS_REQUEST_CODE);

        } else {
            // External Storage permission has not been granted yet. Request it directly without rationale.
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS}, CONTACTS_REQUEST_CODE);

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == CONTACTS_REQUEST_CODE) {
            if (grantResults == null || grantResults.length < 1) {
                return;
            }
            Log.i("test", "Received response for contact permission request.");
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openNextActivity();
                seedAllImages();
            } else if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                openNextActivity();
                seedAllImages();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void seedAllImages() {
        Field[] ID_Fields = R.drawable.class.getFields();
        for (Field f : ID_Fields) {
            try {
                if(f.getName().contains("sticker_original")){
                    Sticker sticker = new Sticker();
                    sticker.setStickerName(f.getName());
                    sticker.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                    StickersRepository.insertOrUpdate(context,sticker);
                }else if(f.getName().contains("animation_preview")){
                    Gifs gifs =new Gifs();
                    gifs.setGifName(f.getName());
                    gifs.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                    GifsRepository.insertOrUpdate(context,gifs);
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        StickersRepository.insertOrUpdate(context,new Sticker());
        GifsRepository.insertOrUpdate(context,new Gifs());
    }

    void openNextActivity() {
        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }
}
