package com.bobble.bobblesampleapp.database.repository;

import android.content.Context;

import com.bobble.bobblesampleapp.GrowthApp;
import com.bobble.bobblesampleapp.database.Gifs;
import com.bobble.bobblesampleapp.database.GifsDao;

import java.util.List;

/**
 * Created by varunjain on 6/23/17.
 */

public class GifsRepository {
    public static void insertOrUpdate(Context context, Gifs gif) {
        getGifDao(context).insertOrReplace(gif);
    }
    public static void clearAccessories(Context context) {
        getGifDao(context).deleteAll();
    }

    public static boolean isEmpty(Context context) {
        return (getGifDao(context).count() == 0);
    }

    public static void deleteGifWithId(Context context, long id) {
        getGifDao(context).delete(getGifsForId(context, id));
    }

    public static List<Gifs> getAllGifs(Context context) {
        return getGifDao(context).loadAll();
    }

    public static Gifs getGifsForId(Context context, long id) {
        return getGifDao(context).load(id);
    }
    public static GifsDao getGifDao(Context c) {
        return ((GrowthApp) c.getApplicationContext()).getDaoSession().getGifsDao();
    }
}
