package com.bobble.bobblesampleapp.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig gifsDaoConfig;
    private final DaoConfig stickerDaoConfig;
    private final DaoConfig morepacksDaoConfig;

    private final GifsDao gifsDao;
    private final StickerDao stickerDao;
    private final MorepacksDao morepacksDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        gifsDaoConfig = daoConfigMap.get(GifsDao.class).clone();
        gifsDaoConfig.initIdentityScope(type);

        stickerDaoConfig = daoConfigMap.get(StickerDao.class).clone();
        stickerDaoConfig.initIdentityScope(type);

        morepacksDaoConfig = daoConfigMap.get(MorepacksDao.class).clone();
        morepacksDaoConfig.initIdentityScope(type);

        gifsDao = new GifsDao(gifsDaoConfig, this);
        stickerDao = new StickerDao(stickerDaoConfig, this);
        morepacksDao = new MorepacksDao(morepacksDaoConfig, this);

        registerDao(Gifs.class, gifsDao);
        registerDao(Sticker.class, stickerDao);
        registerDao(Morepacks.class, morepacksDao);
    }
    
    public void clear() {
        gifsDaoConfig.getIdentityScope().clear();
        stickerDaoConfig.getIdentityScope().clear();
        morepacksDaoConfig.getIdentityScope().clear();
    }

    public GifsDao getGifsDao() {
        return gifsDao;
    }

    public StickerDao getStickerDao() {
        return stickerDao;
    }

    public MorepacksDao getMorepacksDao() {
        return morepacksDao;
    }

}
