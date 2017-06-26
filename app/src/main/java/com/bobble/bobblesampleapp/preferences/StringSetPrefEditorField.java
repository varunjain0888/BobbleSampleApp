package com.bobble.bobblesampleapp.preferences;

import java.util.Set;

/**
 * Created by varunjain on 26/06/17.
 */
public final class StringSetPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {

    StringSetPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public T put(Set<String> value) {
        SharedPreferencesCompat.putStringSet(editorHelper.getEditor(), key, value);
        return editorHelper;
    }
}

