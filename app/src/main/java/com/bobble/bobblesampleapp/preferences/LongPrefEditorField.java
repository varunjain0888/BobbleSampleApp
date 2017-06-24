package com.bobble.bobblesampleapp.preferences;

/**
 * Created by amitshekhar on 26/04/16.
 */
public final class LongPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {

    LongPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public T put(long value) {
        editorHelper.getEditor().putLong(key, value);
        return editorHelper;
    }
}