package com.bobble.bobblesampleapp.singletons;

import android.content.Context;

import com.bobble.bobblesampleapp.BobbleSampleApp;
import com.bobble.bobblesampleapp.database.LogEvents;
import com.bobble.bobblesampleapp.database.repository.LogEventsRepository;
import com.bobble.bobblesampleapp.util.BobbleConstants;

import java.util.concurrent.Callable;

import bolts.Task;

/**
 * Created by varunjain on 6/26/17.
 */

public class BobbleEvent {

    private Context context;

    private static BobbleEvent sInstance;

    private BobbleEvent() {
        context = BobbleSampleApp.getInstance().getApplicationContext();
    }

    public static BobbleEvent getInstance() {
        if (sInstance == null) {
            synchronized (BobbleEvent.class) {
                sInstance = new BobbleEvent();
            }
        }
        return sInstance;
    }
    public void log(final String screenName, final String eventAction, final String eventName, final String eventLabel, final long eventTimestamp) {
        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                LogEvents logEvents = new LogEvents();
                logEvents.setScreenName(screenName);
                logEvents.setEventAction(eventAction);
                logEvents.setEventName(eventName);
                logEvents.setEventLabel(eventLabel);
                logEvents.setEventTimestamp(eventTimestamp);
                logEvents.setStatus(BobbleConstants.NOT_SENT);
                LogEventsRepository.insertOrUpdate(context, logEvents);
                return null;
            }
        });
    }

}
