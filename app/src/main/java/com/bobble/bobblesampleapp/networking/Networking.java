package com.bobble.bobblesampleapp.networking;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bobble.bobblesampleapp.BuildConfig;
import com.bobble.bobblesampleapp.database.LogEvents;
import com.bobble.bobblesampleapp.database.repository.LogEventsRepository;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.bobble.bobblesampleapp.singletons.BobbleEvent;
import com.bobble.bobblesampleapp.util.AppUtils;
import com.bobble.bobblesampleapp.util.BLog;
import com.bobble.bobblesampleapp.util.BobbleConstants;
import com.google.gson.Gson;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import bolts.Task;


/**
 * Created by varunjain on 26/06/17.
 */
public class Networking {

    /**
     * Logcat tag for this Networking class, it can be used to filter logs generated by this class.
     */
    public static final String TAG = "Networking";

    /**
     * private constructor to prevent instantiation of this class
     */
    private Networking() {

    }

    /**
     * This method is use to make register user api call
     * @param context           The context
     */
    public static void registerUser(final Context context, final boolean forBranchReferral) {
        BLog.d(TAG, "registerUser");
        final BobblePrefs bobblePrefs = new BobblePrefs(context);
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceId", bobblePrefs.deviceId().get());
        params.put("packageName", BuildConfig.APPLICATION_ID);
        params.put("deviceInfo", bobblePrefs.deviceInfo().get());
        params.put("appVersion", String.valueOf(bobblePrefs.appVersion().get()));
        params.put("sdkVersion", Build.VERSION.RELEASE);
        params.put("timezone", bobblePrefs.userTimeZone().get());
        params.put("email", AppUtils.getUserIdentity(context));
        params.put("deviceType", BobbleConstants.DEVICE_TYPE);
        if (!bobblePrefs.userId().get().isEmpty()) {
            params.put("userId", bobblePrefs.userId().get());
        }
        params.put("deviceLanguage", Locale.getDefault().toString());
       // params.put("phoneNumber", "8585949830");
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = telephonyManager.getLine1Number();
            if(phoneNumber!=null){
                params.put("phoneNumber", phoneNumber);
            }else{
                params.put("phoneNumber", "123456789");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        AndroidNetworking.post(ApiEndPoint.REGISTER)
                .addBodyParameter(params)
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        BLog.d(TAG, BobbleConstants.API_CALL + "_" + ApiEndPoint.REGISTER + " timeTakenInMillis : " + timeTakenInMillis + " bytesSent : " + bytesSent + " bytesReceived : " + bytesReceived + " isFromCache : " + isFromCache);
                        BobbleEvent.getInstance().log(BobbleConstants.API_CALL, ApiEndPoint.REGISTER, String.valueOf(timeTakenInMillis), bytesSent + "_" + bytesReceived + "_" + isFromCache, System.currentTimeMillis() / 1000);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        BLog.d(TAG, "handleRegisterUserResponse success : " + response.toString());
                        SuccessResponseParseUtil.handleRegisterUserResponse(context, bobblePrefs, forBranchReferral, response);
                    }

                    @Override
                    public void onError(ANError error) {
                        ErrorResponseParseUtil.logError(error, "registerUser");
                    }
                });
    }

    /**
     * This method is use to store encrypted contacts
     *
     * @param context The context
     * @param params  The encrypted contacts
     */
    public static void storeContacts(final Context context, HashMap<String, String> params) {

        String request=  new Gson().toJson(params);
        BLog.d(TAG, "storeContacts"+request);
        AndroidNetworking.post(ApiEndPoint.STORE_CONTACTS)
                .addBodyParameter(params)
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        BLog.d(TAG, BobbleConstants.API_CALL + "_" + ApiEndPoint.STORE_CONTACTS + " timeTakenInMillis : " + timeTakenInMillis + " bytesSent : " + bytesSent + " bytesReceived : " + bytesReceived + " isFromCache : " + isFromCache);
                        BobbleEvent.getInstance().log(BobbleConstants.API_CALL, ApiEndPoint.STORE_CONTACTS, String.valueOf(timeTakenInMillis), bytesSent + "_" + bytesReceived + "_" + isFromCache, System.currentTimeMillis() / 1000);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        BLog.d(TAG, "storeContacts success :" + response.toString());
                        new BobblePrefs(context).isContactsSent().put(true);
                    }

                    @Override
                    public void onError(ANError error) {
                        ErrorResponseParseUtil.logError(error, "storeContact");
                    }
                });
            }

    /**
     * This method is used to log user events to server
     *
     * @param jsonObject    The info data
     * @param context       The context
     * @param logEventsList The list of events to be sent to server
     */
    public static void logEvents(JSONObject jsonObject, final Context context, final List<LogEvents> logEventsList) {
        BLog.d(TAG, "logEvent");
        for (int i = 0; i < logEventsList.size(); i++) {
            logEventsList.get(i).setStatus(BobbleConstants.SENDING);
        }

        LogEventsRepository.getLogEventsDao(context).insertOrReplaceInTx(logEventsList);

        AndroidNetworking.post(ApiEndPoint.LOG_EVENTS)
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .addJSONObjectBody(jsonObject)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        BLog.d(TAG, BobbleConstants.API_CALL + "_" + ApiEndPoint.LOG_EVENTS + " timeTakenInMillis : " + timeTakenInMillis + " bytesSent : " + bytesSent + " bytesReceived : " + bytesReceived + " isFromCache : " + isFromCache);
                        BobbleEvent.getInstance().log(BobbleConstants.API_CALL, ApiEndPoint.LOG_EVENTS, String.valueOf(timeTakenInMillis), bytesSent + "_" + bytesReceived + "_" + isFromCache, System.currentTimeMillis() / 1000);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Task.callInBackground(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                BLog.d(TAG, "logEvent success : " + response.toString());
                                LogEventsRepository.getLogEventsDao(context).deleteInTx(logEventsList);
                                return null;
                            }
                        });
                    }

                    @Override
                    public void onError(ANError error) {
                        ErrorResponseParseUtil.logError(error, "logEvent");
                        Task.callInBackground(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                for (int i = 0; i < logEventsList.size(); i++) {
                                    logEventsList.get(i).setStatus(BobbleConstants.NOT_SENT);
                                }
                                LogEventsRepository.getLogEventsDao(context).insertOrReplaceInTx(logEventsList);
                                return null;
                            }
                        });
                    }
                });
    }
}

