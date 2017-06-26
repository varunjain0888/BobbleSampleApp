package com.bobble.bobblesampleapp.preferences;

/**
 * Created by varunjain on 26/06/17.
 */
public final class FloatPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {

    FloatPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public T put(float value) {
        editorHelper.getEditor().putFloat(key, value);
        return editorHelper;
    }
}
