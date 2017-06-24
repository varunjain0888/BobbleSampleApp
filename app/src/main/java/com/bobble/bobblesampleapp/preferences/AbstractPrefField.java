package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

/**
 * Created by amitshekhar on 26/04/16.
 */
public abstract class AbstractPrefField<T> {

    protected final T defaultValue;

    protected final SharedPreferences sharedPreferences;
    protected final String key;

    public AbstractPrefField(SharedPreferences sharedPreferences, String key, T defaultValue) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public final boolean exists() {
        return sharedPreferences.contains(key);
    }

    public String key() {
        return this.key;
    }

    public final T get() {
        return getOr(defaultValue);
    }

    public abstract T getOr(T defaultValue);

    public final void put(T value) {
        putInternal((value == null) ? defaultValue : value);
    }

    ;

    protected abstract void putInternal(T value);

    public final void remove() {
        apply(edit().remove(key));
    }

    protected SharedPreferences.Editor edit() {
        return sharedPreferences.edit();
    }

    protected final void apply(SharedPreferences.Editor editor) {
        SharedPreferencesCompat.apply(editor);
    }

}
