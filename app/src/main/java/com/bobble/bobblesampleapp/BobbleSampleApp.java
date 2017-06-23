package com.bobble.bobblesampleapp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.bobble.bobblesampleapp.database.DaoMaster;
import com.bobble.bobblesampleapp.database.DaoSession;
import com.bobble.bobblesampleapp.util.Constants;
import com.facebook.drawee.backends.pipeline.Fresco;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by varunjain on 6/23/17.
 */

public class BobbleSampleApp extends Application {
    CalligraphyConfig mCalligraphyConfig;
    private static BobbleSampleApp appInstance;
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        CalligraphyConfig.initDefault(mCalligraphyConfig);
        Fresco.initialize(getApplicationContext());
        try {
            setupDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,
                Constants.DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
    public static synchronized BobbleSampleApp getInstance() {
        return appInstance;
    }
}
