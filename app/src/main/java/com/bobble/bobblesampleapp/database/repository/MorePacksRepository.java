package com.bobble.bobblesampleapp.database.repository;

import android.content.Context;

import com.bobble.bobblesampleapp.BobbleSampleApp;
import com.bobble.bobblesampleapp.database.Morepacks;
import com.bobble.bobblesampleapp.database.MorepacksDao;

import java.util.List;

/**
 * Created by varunjain on 6/23/17.
 */

public class MorePacksRepository {
    public static void insertOrUpdate(Context context, Morepacks gif) {
        getMorepacksDao(context).insertOrReplace(gif);
    }
    public static void clearPacks(Context context) {
        getMorepacksDao(context).deleteAll();
    }

    public static boolean isEmpty(Context context) {
        return (getMorepacksDao(context).count() == 0);
    }

    public static void deletePacksWithId(Context context, int id) {
        getMorepacksDao(context).delete(getPacksForId(context, id));
    }

    public static List<Morepacks> getAllPacks(Context context) {
        return getMorepacksDao(context).loadAll();
    }

    public static Morepacks getPacksForId(Context context, int id) {
        return getMorepacksDao(context).load(id);
    }
    public static MorepacksDao getMorepacksDao(Context c) {
        return ((BobbleSampleApp) c.getApplicationContext()).getDaoSession().getMorepacksDao();
    }
}
