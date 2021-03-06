package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

/**
 * Created by varunjain on 26/06/17.
 */
public final class BooleanPrefField extends AbstractPrefField<Boolean> {

    BooleanPrefField(SharedPreferences sharedPreferences, String key, Boolean defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Boolean getOr(Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    protected void putInternal(Boolean value) {
        apply(edit().putBoolean(key, value));
    }

}