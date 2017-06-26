package com.bobble.bobblesampleapp.preferences;


/**
 * Created by varunjain on 26/06/17.
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
