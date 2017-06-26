package com.bobble.bobblesampleapp.preferences;

/**
 * Created by varunjain on 26/06/17.
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