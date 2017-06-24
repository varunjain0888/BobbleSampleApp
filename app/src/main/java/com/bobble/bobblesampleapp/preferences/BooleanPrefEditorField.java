package com.bobble.bobblesampleapp.preferences;


/**
 * Created by amitshekhar on 26/04/16.
 */
public final class BooleanPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {

    BooleanPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public T put(boolean value) {
        editorHelper.getEditor().putBoolean(key, value);
        return editorHelper;
    }
}
