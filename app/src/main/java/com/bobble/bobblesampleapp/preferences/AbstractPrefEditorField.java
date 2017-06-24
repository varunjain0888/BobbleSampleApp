package com.bobble.bobblesampleapp.preferences;


/**
 * Created by amitshekhar on 26/04/16.
 */
public abstract class AbstractPrefEditorField<T extends EditorHelper<T>> {

    protected final T editorHelper;
    protected final String key;

    public AbstractPrefEditorField(T editorHelper, String key) {
        this.editorHelper = editorHelper;
        this.key = key;
    }

    public final T remove() {
        editorHelper.getEditor().remove(key);
        return editorHelper;
    }

}