package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

/**
 * Created by varunjain on 26/06/17.
 */
public final class StringPrefField extends AbstractPrefField<String> {

    StringPrefField(SharedPreferences sharedPreferences, String key, String defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public String getOr(String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    protected void putInternal(String value) {
        apply(edit().putString(key, value));
    }

}