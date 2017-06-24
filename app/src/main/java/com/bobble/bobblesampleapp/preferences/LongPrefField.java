package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

/**
 * Created by amitshekhar on 26/04/16.
 */
public final class LongPrefField extends AbstractPrefField<Long> {

    LongPrefField(SharedPreferences sharedPreferences, String key, Long defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Long getOr(Long defaultValue) {
        try {
            return sharedPreferences.getLong(key, defaultValue);
        } catch (ClassCastException e) {
            // The pref could be a String, if that is the case try this
            // recovery bit
            try {
                String value = sharedPreferences.getString(key, "" + defaultValue);
                return Long.parseLong(value);
            } catch (Exception e2) {
                // our recovery bit failed. The problem is elsewhere. Send the
                // original error
                throw e;
            }
        }
    }

    @Override
    protected void putInternal(Long value) {
        apply(edit().putLong(key, value));
    }

}