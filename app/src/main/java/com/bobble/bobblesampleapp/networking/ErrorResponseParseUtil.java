package com.bobble.bobblesampleapp.networking;

import android.content.Context;
import com.androidnetworking.error.ANError;
import com.bobble.bobblesampleapp.util.BLog;
import org.json.JSONObject;

/**
 * Created by varunjain on 26/06/17.
 */
public class ErrorResponseParseUtil {

    public static final String TAG = "ErrorResponseParseUtil";

    public static void logError(ANError error, String reference) {
        try {
            if (error != null) {
                if (error.getErrorCode() != 0) {
                    BLog.d(TAG, reference + " onError errorCode : " + error.getErrorCode());
                    BLog.d(TAG, reference + " onError errorBody : " + error.getErrorBody());
                    BLog.d(TAG, reference + " onError errorDetail : " + error.getErrorDetail());
                } else {
                    BLog.d(TAG, reference + " onError errorDetail : " + error.getErrorDetail());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleCommonErrorResponse(ANError error, final String reference, final Context context) {
        logError(error, reference);
        if (error != null) {
            if (error.getErrorCode() != 0) {
                BLog.d(TAG, "handleCommonErrorResponse errorResponseFromServer " + reference + " : " + error.getErrorBody());
                try {
                    JSONObject jsonObject = new JSONObject(error.getErrorBody());
                    String errorCode = null, authError = null;
                    try {
                        errorCode = jsonObject.getString("errorCode");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        authError = jsonObject.getString("error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (errorCode != null && errorCode.equals("deviceIsNotRegistered")) {
                        Networking.registerUser(context, false);
                        return;
                    } else if (authError != null && authError.equals("access_denied")) {
                        //Networking.generateAccessToken(context);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handleExceptionError(reference);
                }
            } else {
                BLog.d(TAG, "handleVerifyCodeErrorResponse errorResponseOther " + reference + " : " + error.getErrorDetail());
                handleExceptionError(reference);
            }
        }
    }

    private static void handleExceptionError(String reference) {
        BLog.d(TAG, "handleExceptionError");
    }

}
