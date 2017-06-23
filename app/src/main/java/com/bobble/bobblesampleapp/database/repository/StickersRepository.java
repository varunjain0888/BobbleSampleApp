package com.bobble.bobblesampleapp.database.repository;

import android.content.Context;

import com.bobble.bobblesampleapp.BobbleSampleApp;
import com.bobble.bobblesampleapp.database.Sticker;
import com.bobble.bobblesampleapp.database.stickerDao;

import java.util.List;

/**
 * Created by varunjain on 6/23/17.
 */

public class StickersRepository {

    public static void insertOrUpdate(Context context, Sticker sticker) {
        getstickerDao(context).insertOrReplace(sticker);
    }
    public static void clearAccessories(Context context) {
        getstickerDao(context).deleteAll();
    }

    public static boolean isEmpty(Context context) {
        return (getstickerDao(context).count() == 0);
    }

    public static void deleteStikerWithId(Context context, long id) {
        getstickerDao(context).delete(getstickerDaoForId(context, id));
    }

    public static List<Sticker> getAllsticker(Context context) {
        return getstickerDao(context).loadAll();
    }

    public static Sticker getstickerDaoForId(Context context, long id) {
        return getstickerDao(context).load(id);
    }
    public static stickerDao getstickerDao(Context c) {
        return ((BobbleSampleApp) c.getApplicationContext()).getDaoSession().getStickerDao();
    }

}
