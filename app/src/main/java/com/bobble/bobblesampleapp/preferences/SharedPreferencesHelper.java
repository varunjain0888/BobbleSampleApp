package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by varunjain on 26/06/17.
 */
public abstract class SharedPreferencesHelper {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public final SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public final void clear() {
        SharedPreferencesCompat.apply(sharedPreferences.edit().clear());
    }

    protected IntPrefField intField(String key, int defaultValue) {
        return new IntPrefField(sharedPreferences, key, defaultValue);
    }

    protected StringPrefField stringField(String key, String defaultValue) {
        return new StringPrefField(sharedPreferences, key, defaultValue);
    }

    protected StringSetPrefField stringSetField(String key, Set<String> defaultValue) {
        return new StringSetPrefField(sharedPreferences, key, defaultValue);
    }

    protected BooleanPrefField booleanField(String key, boolean defaultValue) {
        return new BooleanPrefField(sharedPreferences, key, defaultValue);
    }

    protected FloatPrefField floatField(String key, float defaultValue) {
        return new FloatPrefField(sharedPreferences, key, defaultValue);
    }

    protected LongPrefField longField(String key, long defaultValue) {
        return new LongPrefField(sharedPreferences, key, defaultValue);
    }
}