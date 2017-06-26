package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

/**
 * Created by varunjain on 26/06/17.
 */
public final class IntPrefField extends AbstractPrefField<Integer> {

    IntPrefField(SharedPreferences sharedPreferences, String key, Integer defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Integer getOr(Integer defaultValue) {
        try {
            return sharedPreferences.getInt(key, defaultValue);
        } catch (ClassCastException e) {
            // The pref could be a String, if that is the case try this
            // recovery bit
            try {
                String value = sharedPreferences.getString(key, "" + defaultValue);
                return Integer.parseInt(value);
            } catch (Exception e2) {
                // our recovery bit failed. The problem is elsewhere. Send the
                // original error
                throw e;
            }
        }

    }

    @Override
    protected void putInternal(Integer value) {
        apply(edit().putInt(key, value));
    }

}