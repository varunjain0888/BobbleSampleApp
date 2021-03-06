package com.bobble.bobblesampleapp.database;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MOREPACKS".
 */
public class Morepacks {

    private long id;
    private String packName;
    private String path;
    private Boolean isAd;

    public Morepacks() {
    }

    public Morepacks(long id) {
        this.id = id;
    }

    public Morepacks(long id, String packName, String path, Boolean isAd) {
        this.id = id;
        this.packName = packName;
        this.path = path;
        this.isAd = isAd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getIsAd() {
        return isAd;
    }

    public void setIsAd(Boolean isAd) {
        this.isAd = isAd;
    }

}
