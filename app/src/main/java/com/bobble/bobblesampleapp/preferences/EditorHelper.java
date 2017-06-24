package com.bobble.bobblesampleapp.preferences;

import android.content.SharedPreferences;


/**
 * Created by amitshekhar on 26/04/16.
 */
public abstract class EditorHelper<T extends EditorHelper<T>> {

    private final SharedPreferences.Editor editor;

    public EditorHelper(SharedPreferences sharedPreferences) {
        editor = sharedPreferences.edit();
    }

    protected SharedPreferences.Editor getEditor() {
        return editor;
    }

    public final T clear() {
        editor.clear();
        return cast();
    }

    public final void apply() {
        SharedPreferencesCompat.apply(editor);
    }

    protected IntPrefEditorField<T> intField(String key) {
        return new IntPrefEditorField<T>(cast(), key);
    }

    protected StringPrefEditorField<T> stringField(String key) {
        return new StringPrefEditorField<T>(cast(), key);
    }

    protected StringSetPrefEditorField<T> stringSetField(String key) {
        return new StringSetPrefEditorField<T>(cast(), key);
    }

    protected BooleanPrefEditorField<T> booleanField(String key) {
        return new BooleanPrefEditorField<T>(cast(), key);
    }

    protected FloatPrefEditorField<T> floatField(String key) {
        return new FloatPrefEditorField<T>(cast(), key);
    }

    protected LongPrefEditorField<T> longField(String key) {
        return new LongPrefEditorField<T>(cast(), key);
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }

}
