package com.bobble.bobblesampleapp.preferences;

/**
 * Created by varunjain on 26/06/17.
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