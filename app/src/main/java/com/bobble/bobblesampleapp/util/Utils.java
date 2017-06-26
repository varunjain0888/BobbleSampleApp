package com.bobble.bobblesampleapp.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.bobble.bobblesampleapp.database.LogEvents;
import com.bobble.bobblesampleapp.database.LogEventsDao;
import com.bobble.bobblesampleapp.database.repository.LogEventsRepository;

import java.util.List;

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

    public static void logException(String TAG, final Exception e) {
        BLog.e(TAG, e.getMessage() + "");
    }

    static void resetStatusOfSendingEvents(Context context) {
        List<LogEvents> logEventsList = LogEventsRepository.getLogEventsDao(context).queryBuilder().where(LogEventsDao.Properties.Status.eq(BobbleConstants.SENDING)).list();
        for (int i = 0; i < logEventsList.size(); i++) {
            logEventsList.get(i).setStatus(BobbleConstants.NOT_SENT);
        }
        LogEventsRepository.getLogEventsDao(context).insertOrReplaceInTx(logEventsList);
    }
}
