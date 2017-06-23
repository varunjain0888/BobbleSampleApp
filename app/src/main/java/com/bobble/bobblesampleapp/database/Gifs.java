package com.bobble.bobblesampleapp.database;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "GIFS".
 */
public class Gifs {

    private long id;
    private String gifName;
    private String path;

    public Gifs() {
    }

    public Gifs(long id) {
        this.id = id;
    }

    public Gifs(long id, String gifName, String path) {
        this.id = id;
        this.gifName = gifName;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGifName() {
        return gifName;
    }

    public void setGifName(String gifName) {
        this.gifName = gifName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
