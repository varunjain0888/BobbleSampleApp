package com.bobble.bobblesampleapp.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

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
import com.bobble.bobblesampleapp.singletons.BobbleEvent;
import com.bobble.bobblesampleapp.util.BLog;
import com.bobble.bobblesampleapp.util.BobbleConstants;
import com.bobble.bobblesampleapp.util.FileUtil;
import com.bobble.bobblesampleapp.util.GrowthAppWorkUtil;
import com.bobble.bobblesampleapp.util.SaveUtils;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
       /* if (bobblePrefs.appOpenedCount().get() == 0) {
            FileUtil.delete(bobblePrefs.privateDirectory().get() + File.separator + "resources" + File.separator + "bobbleAnimations");
        }*/

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
                //Bobble analytics
                BobbleEvent.getInstance().log("Splash Screen","Splash time","splash_time",String.valueOf(System.currentTimeMillis()/1000),System.currentTimeMillis()/1000);
            }
        },SPLASH_SCREEN_DELAY);
        //Bobble analytics
        BobbleEvent.getInstance().log("Splash Screen","App launch","splash_launch","",System.currentTimeMillis()/1000);

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
                GrowthAppWorkUtil.processAppStartUpWork(getApplicationContext());
                openNextActivity();
            }
        }else{
            GrowthAppWorkUtil.processAppStartUpWork(getApplicationContext());
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
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE}, CONTACTS_REQUEST_CODE);

        } else {
            // External Storage permission has not been granted yet. Request it directly without rationale.
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE}, CONTACTS_REQUEST_CODE);

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
            if (grantResults.length == 4 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED&& grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                GrowthAppWorkUtil.processAppStartUpWork(getApplicationContext());
                createBobbleDirectory(CREATE_EXTERNAL_DIRECTORY);
                seedAllImages();
                openNextActivity();
            } else if (grantResults.length == 4 && grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED
                    || grantResults[2] == PackageManager.PERMISSION_GRANTED&& grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                seedAllImages();
                openNextActivity();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void seedAllImages() {
                Field[] ID_Fields = R.drawable.class.getFields();
        int i =1,j=1;
                    for (Field f : ID_Fields) {
                        if(f.getName().contains("sticker_")){
                            try {
                                Sticker sticker = new Sticker();
                                sticker.setStickerName(f.getName());
                                sticker.setPath(bobblePrefs.privateDirectory().get() + "/" + f.getName() + ".jpg");
                                sticker.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                                StickersRepository.insertOrUpdate(context, sticker);
                                SaveUtils.saveGiforJPG(context, bobblePrefs.privateDirectory().get(), getResources().getIdentifier(f.getName(), "drawable", getPackageName()), "jpg", f.getName());
                                } catch (Exception e) {
                                    e.printStackTrace();
                            }
                        }else if(f.getName().contains("gif_")){
                            try {
                                Gifs gifs =new Gifs();
                                gifs.setGifName(f.getName());
                                gifs.setPath(bobblePrefs.privateDirectory().get()+"/"+f.getName()+".gif");
                                gifs.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                                GifsRepository.insertOrUpdate(context,gifs);

                                SaveUtils.saveGiforJPG(context,bobblePrefs.privateDirectory().get(),getResources().getIdentifier(f.getName(), "drawable", getPackageName()),"gif",f.getName());
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            }else if(f.getName().contains("advertisement_")){
                            try {
                                Morepacks morepacks = new Morepacks();
                                morepacks.setPackName(f.getName());
                                morepacks.setId(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                                MorePacksRepository.insertOrUpdate(context,morepacks);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
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
}
