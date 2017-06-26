package com.bobble.bobblesampleapp.database.repository;

import android.content.Context;


import com.bobble.bobblesampleapp.BobbleSampleApp;
import com.bobble.bobblesampleapp.database.Preferences;
import com.bobble.bobblesampleapp.database.PreferencesDao;

import java.util.List;

public class PreferencesRepository {

    public static void insertOrUpdate(Context context, Preferences preferences) {
        getPreferencesDao(context).insertOrReplace(preferences);
    }

    public static void clearPreferences(Context context) {
        getPreferencesDao(context).deleteAll();
    }

    public static boolean isEmpty(Context context) {
        return (getPreferencesDao(context).count() == 0);
    }

    public static void deletePreferencesWithId(Context context, String key) {
        getPreferencesDao(context).delete(getPreferencesForId(context, key));
    }

    public static List<Preferences> getAllPreferences(Context context) {
        return getPreferencesDao(context).loadAll();
    }

    public static Preferences getPreferencesForId(Context context, String key) {
        return getPreferencesDao(context).load(key);
    }

    public static PreferencesDao getPreferencesDao(Context c) {
        return ((BobbleSampleApp) c.getApplicationContext()).getDaoSession().getPreferencesDao();
    }
}
