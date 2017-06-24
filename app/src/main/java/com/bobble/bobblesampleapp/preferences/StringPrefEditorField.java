package com.bobble.bobblesampleapp.preferences;

/**
 * Created by amitshekhar on 26/04/16.
 */
public final class StringPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {

    StringPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public T put(String value) {
        editorHelper.getEditor().putString(key, value);
        return editorHelper;
    }
}