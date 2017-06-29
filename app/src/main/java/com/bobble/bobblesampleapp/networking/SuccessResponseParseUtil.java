package com.bobble.bobblesampleapp.networking;

import android.content.Context;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.concurrent.Callable;
import bolts.Task;


/**
 * Created by varunjain on 26/06/17.
 */
public class SuccessResponseParseUtil {
    public static final String TAG = "SuccessResponseParseUtil";

    public static void handleConfigResponse(final Context context, final JSONObject response) {
        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                BobblePrefs bobblePrefs = new BobblePrefs(context);
                bobblePrefs.actualAppVersionCodeAvailableOnPlayStore().put(response.getInt("currentAppVersion"));
                bobblePrefs.imageSampling().put(response.getBoolean("imageSampling"));
                bobblePrefs.faceImageSampling().put(response.getBoolean("faceImageSampling"));
                bobblePrefs.playStoreUrl().put(response.getString("playStoreUrl"));
                bobblePrefs.isForceUpdate().put(response.getBoolean("isForceUpdate"));

                if (response.has("googleAnalytics")) {
                    JSONObject googleAnalytics = response.getJSONObject("googleAnalytics");
                    bobblePrefs.isEventCategoryOneRequired().put(googleAnalytics.getBoolean("category1"));
                    bobblePrefs.isEventCategoryTwoRequired().put(googleAnalytics.getBoolean("category2"));
                    bobblePrefs.isEventCategoryThreeRequired().put(googleAnalytics.getBoolean("category3"));
                }

                if (response.has("useClientAuthentication")) {
                    bobblePrefs.useClientAuthentication().put(response.getBoolean("useClientAuthentication"));
                }
                if (response.has("uploadAppDebugData")) {
                    bobblePrefs.uploadAppDebugData().put(response.getBoolean("uploadAppDebugData"));
                }

                try {
                    if (response.has("abTests")) {
                        bobblePrefs.abTests().put(response.getJSONObject("abTests").toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //BobbleEvent.getInstance().update();
                return null;
            }
        });
    }

    public static void handleRegisterUserResponse(final Context context, final BobblePrefs bobblePrefs, final boolean forBranchReferral, final JSONObject response) {
        try {
            bobblePrefs.isRegistered().put(true);
            if(response.getString("userId")!=null){
                bobblePrefs.userId().put(response.getString("userId"));
            }

            bobblePrefs.lastInvitePopupTime().put(Calendar.getInstance().getTimeInMillis());
            if (!forBranchReferral && !bobblePrefs.utmCampaign().get().isEmpty()) {
                bobblePrefs.isWorkDoneForReferral().put(true);
            }
            if (forBranchReferral) {
                bobblePrefs.isWorkDoneForBranchReferral().put(true);
            }
            if (response.has("actionData")) {
                JSONObject data = response.getJSONObject("actionData");
                if (data.has("category")) {
                    bobblePrefs.referralCategory().put(data.getString("category"));
                }
                if (data.has("tabName")) {
                    bobblePrefs.tabName().put(data.getString("tabName"));
                }
                if (data.has("pageUrl")) {
                    bobblePrefs.urlForReferral().put(data.getString("pageUrl"));
                }
            }
            try {
                if (bobblePrefs.countryCodeFromServer().get().isEmpty() && response.has("ipAddressCountryCode")) {
                    bobblePrefs.countryCodeFromServer().put(response.getString("ipAddressCountryCode"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
