package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by varunjain on 26/06/17.
 */
public final class StringSetPrefField extends AbstractPrefField<Set<String>> {

    StringSetPrefField(SharedPreferences sharedPreferences, String key, Set<String> defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Set<String> getOr(Set<String> defaultValue) {
        return SharedPreferencesCompat.getStringSet(sharedPreferences, key, defaultValue);
    }

    @Override
    protected void putInternal(Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferencesCompat.putStringSet(editor, key, value);
        apply(editor);
    }

}