package com.bobble.bobblesampleapp.preferences;

/**
 * Created by varunjain on 26/06/17.
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
