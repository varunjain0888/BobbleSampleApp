package com.bobble.bobblesampleapp.preferences;

/**
 * Created by amitshekhar on 26/04/16.
 */
public final class IntPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {

    IntPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public T put(int value) {
        editorHelper.getEditor().putInt(key, value);
        return editorHelper;
    }
}
