package com.bobble.bobblesampleapp.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by amitshekhar on 05/10/16.
 */

public class UriUtils {

    private UriUtils() {
        // This utility class is not publicly instantiable.
    }

    public static Uri getUri(Context context, @Nullable String value) {
        if (value == null)
            return null;

        Uri uri;

        if (value.startsWith("/")) {
            uri = Uri.fromFile(new File(value));
        } else {
            int resDrawable = context.getResources().getIdentifier(value,
                    "drawable", context.getPackageName());
            uri = Uri.parse("res://" + context.getPackageName() + "/" + resDrawable);
        }
        return uri;
    }
}
