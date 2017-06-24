package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

/**
 * Created by amitshekhar on 26/04/16.
 */
public final class FloatPrefField extends AbstractPrefField<Float> {

    FloatPrefField(SharedPreferences sharedPreferences, String key, Float defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Float getOr(Float defaultValue) {
        try {
            return sharedPreferences.getFloat(key, defaultValue);
        } catch (ClassCastException e) {
            // The pref could be a String, if that is the case try this
            // recovery bit
            try {
                String value = sharedPreferences.getString(key, "" + defaultValue);
                return Float.parseFloat(value);
            } catch (Exception e2) {
                // our recovery bit failed. The problem is elsewhere. Send the
                // original error
                throw e;
            }
        }

    }

    @Override
    protected void putInternal(Float value) {
        apply(edit().putFloat(key, value));
    }

}
