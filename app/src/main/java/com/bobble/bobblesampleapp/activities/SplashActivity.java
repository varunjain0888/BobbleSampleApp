package com.bobble.bobblesampleapp.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.bobble.bobblesampleapp.BobbleSampleApp;
import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.database.Gifs;
import com.bobble.bobblesampleapp.database.Morepacks;
import com.bobble.bobblesampleapp.database.Preferences;
import com.bobble.bobblesampleapp.database.Sticker;
import com.bobble.bobblesampleapp.database.repository.GifsRepository;
import com.bobble.bobblesampleapp.database.repository.MorePacksRepository;
import com.bobble.bobblesampleapp.database.repository.PreferencesRepository;
import com.bobble.bobblesampleapp.database.repository.StickersRepository;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.bobble.bobblesampleapp.services.BackgroundJob;
import com.bobble.bobblesampleapp.util.BLog;
import com.bobble.bobblesampleapp.util.BobbleConstants;
import com.bobble.bobblesampleapp.util.FileUtil;
import com.bobble.bobblesampleapp.util.HackAppWorkUtil;
import com.bobble.bobblesampleapp.util.SaveUtils;
import com.bobble.bobblesampleapp.util.Utils;
import com.bobble.bobblesampleapp.util.ZipUtil;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by varunjain on 6/23/17.
 */

public class SplashActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private final String DRAWABLE_URI_PATH = "android.resource://com.bobble.bobblesampleapp/drawable/";
    private final String CREATE_EXTERNAL_DIRECTORY = "external";
    private final int EXTERNAL_STORAGE_REQUEST_CODE = 1000;
    private final int CONTACTS_REQUEST_CODE = 3000;
    private final int SPLASH_SCREEN_DELAY = 2000;
    private final int INVALID_JOB_ID = -1;
    private String TAG = SplashActivity.class.getSimpleName();
    private Context context;
    private GifImageView draweeView;
    private Handler mSplashDelayHandler;
    private BobblePrefs bobblePrefs;
    private Display display;
    private Point size = new Point();
    private ByteArrayOutputStream bytearrayoutputstream;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        context = this;
        bobblePrefs = new BobblePrefs(context);
        bytearrayoutputstream =new ByteArrayOutputStream();
        getIds();
        setData();
        bobblePrefs.appOpenedCount().put(bobblePrefs.appOpenedCount().get() + 1);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        bobblePrefs.deviceHeight().put(size.y);
        bobblePrefs.deviceWidth().put(size.x);
        bobblePrefs.screenHeight().put(size.y);
        bobblePrefs.screenWidth().put(size.x);
        if (bobblePrefs.appOpenedCount().get() == 0) {
            FileUtil.delete(bobblePrefs.privateDirectory().get() + File.separator + "resources" + File.separator + "bobbleAnimations");
        }

        //TODO NOT IN USE STATIC BLOCK BUT REQUIRED IN FUTURE
        {
            String root = Environment.getExternalStorageDirectory().toString();
            File file = new File(root + File.separator + BobbleConstants.BOBBLE_FOLDER);
            file.mkdirs();
            bobblePrefs.privateDirectory().put(file.getAbsolutePath());
            bobblePrefs.sharingDirectory().put(file.getAbsolutePath());
            file = new File(root + File.separator + BobbleConstants.BOBBLE_FOLDER + File.separator + ".nomedia");
            try {
                file.createNewFile();
            } catch (IOException e) {
                //Android 6.0 : no need of logging as it only comes when there is no prior permission.
            }
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getResources().getString(
                    R.string.app_name));
            file.mkdirs();
            bobblePrefs.publicDirectory().put(file.getAbsolutePath());
            BLog.d(TAG, "privateDirectory path : " + bobblePrefs.privateDirectory().get());

            //Check if SD card is mounted
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {

                //Check for WRITE_EXTERNAL_STORAGE permission before creating directory
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // External Storage permission has not been granted.

                    //TODO IF REQUIRED IN FUTURE
                    //requestExternalStoragePermission();
                } else {
                    // Camera permissions is already available, show the camera preview.
                    BLog.i("test", "External Storage permission has already been granted. Displaying camera preview.");
                    createBobbleDirectory(CREATE_EXTERNAL_DIRECTORY);
                    seedPrefrences();
                    seedAllImages();
                    HackAppWorkUtil.processAppStartUpWork(getApplicationContext());
                }
            }
        }
        //Schedule a job for background user registration and log events to server
        scheduleABackgroundJob();
        try {
            if (!bobblePrefs.disableSimilarWebSDK().get()) {
                ((BobbleSampleApp) getApplicationContext()).initialiseSimilarWeb();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mSplashDelayHandler = new Handler();
        mSplashDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestPermission();
            }
        },SPLASH_SCREEN_DELAY);
    }

    private void seedPrefrences() {
        List<Preferences> preferencesList = new ArrayList<Preferences>();

        Preferences preferences = new Preferences();
        if (PreferencesRepository.getPreferencesForId(context, BobbleConstants.APP_VERSION) == null) {
            preferences = new Preferences(BobbleConstants.APP_VERSION, "0");
            preferencesList.add(preferences);
        }
        PreferencesRepository.getPreferencesDao(context).insertOrReplaceInTx(preferencesList);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                BLog.e(TAG, "inside contact permission");
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
            BLog.i("test",
                    "Displaying external storage permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CONTACTS_REQUEST_CODE);

        } else {
            // External Storage permission has not been granted yet. Request it directly without rationale.
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CONTACTS_REQUEST_CODE);

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == EXTERNAL_STORAGE_REQUEST_CODE) {
            // Received permission result for external storage permission.
            BLog.i("test", "Received response for extrnal storage permission request.");
            if (permissions == null || permissions.length < 1) {
                return;
            }
            if (grantResults == null || grantResults.length < 1) {
                return;
            }
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // External storage permission has been granted, create directory in external storage
                BLog.i("test", "External storage permission has now been granted. Creating Directory.");
                requestPermission();
            } else {
                if (permissions == null || permissions.length < 1) {
                    return;
                }
                if (grantResults == null || grantResults.length < 1) {
                    return;
                }
            }
        } else if (requestCode == CONTACTS_REQUEST_CODE) {
            if (grantResults == null || grantResults.length < 1) {
                return;
            }
            BLog.i("test", "Received response for contact permission request.");
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                createBobbleDirectory(CREATE_EXTERNAL_DIRECTORY);
                seedAllImages();
                openNextActivity();
            } else if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED
                    || grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                seedAllImages();
                openNextActivity();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void seedAllImages() {
        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Field[] ID_Fields = R.drawable.class.getFields();
                for (Field f : ID_Fields) {
                    try {
                        if(f.getName().contains("sticker_original")){
                            Sticker sticker = new Sticker();
                            sticker.setStickerName(f.getName());
                            sticker.setPath(bobblePrefs.privateDirectory().get()+"/"+f.getName()+".jpg");
                            sticker.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                            StickersRepository.insertOrUpdate(context,sticker);
                            SaveUtils.saveGiforJPG(context,bobblePrefs.privateDirectory().get(),getResources().getIdentifier(f.getName(), "drawable", getPackageName()),"jpg",f.getName());
                        }else if(f.getName().contains("animation_preview")){
                            Gifs gifs =new Gifs();
                            gifs.setGifName(f.getName());
                            gifs.setPath(bobblePrefs.privateDirectory().get()+"/"+f.getName()+".gif");
                            gifs.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                            GifsRepository.insertOrUpdate(context,gifs);
                            SaveUtils.saveGiforJPG(context,bobblePrefs.privateDirectory().get(),getResources().getIdentifier(f.getName(), "drawable", getPackageName()),"gif",f.getName());
                        }else if(f.getName().contains("bobble_more")){
                            Morepacks morepacks = new Morepacks();
                            morepacks.setPackName(f.getName());
                            morepacks.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                            MorePacksRepository.insertOrUpdate(context,morepacks);
                        }
                        StickersRepository.insertOrUpdate(context,new Sticker());
                        GifsRepository.insertOrUpdate(context,new Gifs());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
    void saveImagesToExternalStorage(final int resId, final String name){
        Drawable drawable = getResources().getDrawable(resId);

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
        File file = new File(bobblePrefs.privateDirectory().get() + "/"+name);
        try
        {
            file.createNewFile();
            FileOutputStream fileoutputstream = new FileOutputStream(file);

            fileoutputstream.write(bytearrayoutputstream.toByteArray());
            fileoutputstream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createBobbleDirectory(String storageType) {

        if (storageType.equalsIgnoreCase(CREATE_EXTERNAL_DIRECTORY)) {

            String root = Environment.getExternalStorageDirectory().toString();
            File file = new File(root + File.separator + BobbleConstants.BOBBLE_FOLDER);
            file.mkdirs();
            bobblePrefs.privateDirectory().put(file.getAbsolutePath());
            bobblePrefs.sharingDirectory().put(file.getAbsolutePath());
            file = new File(root + File.separator + BobbleConstants.BOBBLE_FOLDER + File.separator + ".nomedia");
            try {
                file.createNewFile();
            } catch (IOException e) {
                //Utils.BLogException(TAG, e);
            }
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getResources().getString(
                    R.string.app_name));
            file.mkdirs();
            bobblePrefs.publicDirectory().put(file.getAbsolutePath());
            BLog.e(TAG, "privateDirectory path : " + bobblePrefs.privateDirectory().get());

        } else {
            BLog.e(TAG, "Making folder in internal directory");

            File internalDirectory = context.getDir(BobbleConstants.BOBBLE_FOLDER, Context.MODE_PRIVATE);

            bobblePrefs.privateDirectory().put(internalDirectory.getAbsolutePath());
            bobblePrefs.sharingDirectory().put(internalDirectory.getAbsolutePath());

            BLog.e(TAG, "privateDirectory path : " + bobblePrefs.privateDirectory().get());

            internalDirectory = context.getDir("Bobble", Context.MODE_PRIVATE);
            internalDirectory.setReadable(true, false);

            bobblePrefs.publicDirectory().put(internalDirectory.getAbsolutePath());

            BLog.e(TAG, "publicdirectory path : " + bobblePrefs.publicDirectory().get());
        }

    }

    private void scheduleABackgroundJob() {
        int scheduledJobId = bobblePrefs.scheduledJobId().get();
        if(scheduledJobId == INVALID_JOB_ID) {
            int newScheduledJobId = BackgroundJob.scheduleBackgroundJob();
            bobblePrefs.scheduledJobId().put(newScheduledJobId);
        } else {
            JobRequest scheduledJobRequest = JobManager.instance().getJobRequest(scheduledJobId);
            if(scheduledJobRequest == null) { //this condition tell that scheduled job is finished
                int newScheduledJobId = BackgroundJob.scheduleBackgroundJob();
                bobblePrefs.scheduledJobId().put(newScheduledJobId);
            }
        }
    }

    private void requestExternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            BLog.i("test",
                    "Displaying external storage permission rationale to provide additional context.");

            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST_CODE);

        } else {
            // External Storage permission has not been granted yet. Request it directly without rationale.
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    void openNextActivity() {
        Task.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Intent intent = MainActivity.getStartIntent(SplashActivity.this);
                startActivity(intent);
                finish();
                return null;
            }
        },Task.UI_THREAD_EXECUTOR);

    }





    private void seedBobbleAnimationPacks() {
            BobblePrefs prefs = new BobblePrefs(context);
            String zipFile = prefs.privateDirectory().get() + File.separator + "resources" + File.separator + "animation_pack_seed.zip";
            String unzipPath = prefs.privateDirectory().get() + File.separator + "resources";
            if (!FileUtil.isFilePresent(context, zipFile)) {
                SaveUtils.copyAssets("animation_pack_seed.zip", unzipPath, context);
            }
            boolean zipSuccessful = ZipUtil.unzip(zipFile, unzipPath);
            if (zipSuccessful) {
                String folderPath = prefs.privateDirectory().get() + File.separator + "resources" + File.separator + "animation_pack_seed";
                String sourcePath1 = folderPath + File.separator + "accessories";
                String sourcePath2 = folderPath + File.separator + "bobbleAnimationPack";
                FileUtil.move(sourcePath1, unzipPath + File.separator + "accessories", true);
                FileUtil.move(sourcePath2, unzipPath + File.separator + "bobbleAnimationPack", true);
                String animationFolder = prefs.privateDirectory().get() + File.separator + "resources" + File.separator + "bobbleAnimationPack";

            }
        }




}
