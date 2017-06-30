package com.bobble.bobblesampleapp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.androidnetworking.AndroidNetworking;
import com.bobble.bobblesampleapp.database.DaoMaster;
import com.bobble.bobblesampleapp.database.DaoSession;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.bobble.bobblesampleapp.services.BackgroundJobCreator;
import com.bobble.bobblesampleapp.util.AppUtils;
import com.bobble.bobblesampleapp.util.BobbleConstants;
import com.evernote.android.job.JobManager;

import co.tmobi.Skydive;


/**
 * Created by varunjain on 6/23/17.
 */

public class GrowthApp extends Application {
    private static GrowthApp appInstance;
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        AndroidNetworking.initialize(getApplicationContext());
        JobManager.create(getApplicationContext()).addJobCreator(new BackgroundJobCreator()); // initialising JobManger
        BobblePrefs bobblePrefs = new BobblePrefs(this);
        if (bobblePrefs.appVersion().get() != 0 && AppUtils.getAppVersion(this) > bobblePrefs.appVersion().get()) {
            bobblePrefs.isRegistered().put(false);
            AppUtils.updateAppVersion(this);
        } else if (bobblePrefs.appVersion().get() == 0) {
            AppUtils.updateAppVersion(this);
            bobblePrefs.hasAssetsUrlCallCompleted().put(true);
        }
        if (bobblePrefs.deviceId().get().isEmpty()) {
            bobblePrefs.deviceId().put(AppUtils.getDeviceId(this));
        }
        try {
            setupDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,
                BobbleConstants.DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
    public static synchronized GrowthApp getInstance() {
        return appInstance;
    }
    public void initialiseSimilarWeb() {
        Skydive.start(getApplicationContext());
    }
}
