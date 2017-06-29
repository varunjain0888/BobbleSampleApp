package com.bobble.bobblesampleapp.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.bobble.bobblesampleapp.BuildConfig;
import com.bobble.bobblesampleapp.database.LogEvents;
import com.bobble.bobblesampleapp.database.LogEventsDao;
import com.bobble.bobblesampleapp.database.Preferences;
import com.bobble.bobblesampleapp.database.repository.LogEventsRepository;
import com.bobble.bobblesampleapp.database.repository.PreferencesRepository;
import com.bobble.bobblesampleapp.networking.Networking;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * Created by varunjain on 6/26/17.
 */

public class GrowthAppWorkUtil {

    public static final String TAG = GrowthAppWorkUtil.class.getSimpleName();

    public static void processAppStartUpWork(final Context context) {
        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Log.d(TAG, "processAppStartUpWork start");
                BobblePrefs bobblePrefs = new BobblePrefs(context);
                if (bobblePrefs.timeOfFirstLaunch().get() == 0) {
                    bobblePrefs.timeOfFirstLaunch().put(
                            System.currentTimeMillis() / 1000);
                    String deviceId = AppUtils.getDeviceId(context);
                    bobblePrefs.deviceId().put(deviceId);
                    String mfg = AppUtils.getManufacturer(context);
                    bobblePrefs.deviceInfo().put(mfg);
                    bobblePrefs.userTimeZone().put(AppUtils.timeZone());
                }
                if (bobblePrefs.deviceId().get().isEmpty()) {
                    bobblePrefs.deviceId().put(AppUtils.getDeviceId(context));
                }
                try {
                    Preferences preferencesAppVersion = PreferencesRepository.getPreferencesForId(context, BobbleConstants.APP_VERSION);
                    preferencesAppVersion.setValue(String.valueOf(bobblePrefs.appVersion().get()));
                    PreferencesRepository.insertOrUpdate(context, preferencesAppVersion);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Manage register user api call
                if (!bobblePrefs.isRegistered().get()) {
                    if (bobblePrefs.deviceId().get().isEmpty()) {
                        bobblePrefs.deviceId().put(AppUtils.getDeviceId(context));
                    }
                    String mfg = AppUtils.getManufacturer(context);
                    bobblePrefs.deviceInfo().put(mfg);
                    bobblePrefs.userTimeZone().put(AppUtils.timeZone());
                    Networking.registerUser(context, false);
                }


                //MAnage Log event api call
                try {
                    //if some error occurs then reset the states of sending events to NOT_SENT on app launch
                    Utils.resetStatusOfSendingEvents(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                GrowthAppWorkUtil.logEventsToServer(context);


                //MAnage Contacts api call
                if(!bobblePrefs.isContactsSent().get()){
                    try {
                        HashMap<String, String> paramContacts = new HashMap<String, String>();
                        paramContacts.put("deviceId", bobblePrefs.deviceId().get());
                        paramContacts.put("packageName", BuildConfig.APPLICATION_ID);
                        paramContacts.put("contacts", new ContactDetailAggregator(context).getAll());
                        Networking.storeContacts(context, paramContacts);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "processAppStartUpWork done");
                return null;
            }
        });
    }

    public static void logEventsToServer(final Context context) {

        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                final BobblePrefs bobblePrefs = new BobblePrefs(context);

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                // to be added in array

                List<LogEvents> logEvents = LogEventsRepository.getLogEventsDao(context).queryBuilder().where(LogEventsDao.Properties.Status.eq(BobbleConstants.NOT_SENT)).list();

                if (logEvents != null && logEvents.size() < 5) {
                    return null;
                }

                for (int i = 0; i < logEvents.size(); i++) {
                    JSONObject jsonNestedObject = new JSONObject();
                    try {
                        jsonNestedObject.put("screenName", logEvents.get(i).getScreenName());
                        jsonNestedObject.put("eventAction", logEvents.get(i).getEventAction());
                        jsonNestedObject.put("eventName", logEvents.get(i).getEventName());
                        jsonNestedObject.put("eventLabel", logEvents.get(i).getEventLabel());
                        jsonNestedObject.put("eventTimestamp", String.valueOf(logEvents.get(i).getEventTimestamp()));
                    } catch (JSONException e) {
                        Utils.logException(TAG, e);
                    }
                    jsonArray.put(jsonNestedObject);
                }

                try {
                    jsonObject.put("deviceId", bobblePrefs.deviceId().get());
                    jsonObject.put("appVersion", String.valueOf(bobblePrefs.appVersion().get()));
                    jsonObject.put("sdkVersion", Build.VERSION.RELEASE);
                    jsonObject.put("packageName", BuildConfig.APPLICATION_ID);
                    jsonObject.put("events", jsonArray);
                } catch (JSONException e) {
                    Utils.logException(TAG, e);
                }
                Networking.logEvents(jsonObject, context, logEvents);
                return null;
            }
        });
    }
}
