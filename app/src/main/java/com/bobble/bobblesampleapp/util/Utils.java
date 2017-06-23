package com.bobble.bobblesampleapp.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by varunjain on 6/23/17.
 */

public class Utils {
    public static final String TAG = "Utils";
    public static String getUserIdentity(Context context) {
        AccountManager manager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();
        String emailId = null;
        for (Account account : list) {
            if (account.type.equalsIgnoreCase("com.google")) {
                emailId = account.name;
                break;
            }
        }
        if (emailId != null) {
            Log.d(Utils.TAG, "emailIdOfUser : " + emailId);
            return emailId;
        }
        return "";
    }
}
