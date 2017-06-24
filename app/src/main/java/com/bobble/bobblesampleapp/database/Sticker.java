package com.bobble.bobblesampleapp.database;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "STICKER".
 */
public class Sticker {

    private int id;
    private String stickerName;
    private String path;

    public Sticker() {
    }

    public Sticker(int id) {
        this.id = id;
    }

    public Sticker(int id, String stickerName, String path) {
        this.id = id;
        this.stickerName = stickerName;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
