package com.bobble.bobblesampleapp.database.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabaseLockedException;

import com.bobble.bobblesampleapp.BobbleSampleApp;
import com.bobble.bobblesampleapp.database.LogEvents;
import com.bobble.bobblesampleapp.database.LogEventsDao;
import com.bobble.bobblesampleapp.util.Utils;

import java.util.List;

/**
 * Created by amitshekhar on 04/02/15.
 */
public class LogEventsRepository {
    public static final String TAG = "LogEventsRepository";

    public static void insertOrUpdate(Context context, LogEvents logEvents) {
        try {
            getLogEventsDao(context).insertOrReplace(logEvents);
        } catch (SQLiteDatabaseLockedException e) {
            Utils.logException(TAG, e);
        }
    }

    public static void clearLogEvents(Context context) {
        try {
            getLogEventsDao(context).deleteAll();
        } catch (SQLiteDatabaseLockedException e) {
            Utils.logException(TAG, e);
        }
    }

    public static boolean isEmpty(Context context) {
        try {
            return (getLogEventsDao(context).count() == 0);
        } catch (SQLiteDatabaseLockedException e) {
            Utils.logException(TAG, e);
        }
        return true;
    }

    public static void deleteLogEventsWithId(Context context, long id) {
        getLogEventsDao(context).delete(getLogEventsForId(context, id));
    }

    public static List<LogEvents> getAllLogEvents(Context context) {
        return getLogEventsDao(context).loadAll();
    }

    public static LogEvents getLogEventsForId(Context context, long id) {
        return getLogEventsDao(context).load(id);
    }

    public static LogEventsDao getLogEventsDao(Context c) {
        return ((BobbleSampleApp) c.getApplicationContext()).getDaoSession().getLogEventsDao();
    }
}
