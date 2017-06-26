package com.bobble.bobblesampleapp.networking;

import com.bobble.bobblesampleapp.BuildConfig;

/**
 * Created by varunjain on 6/26/17.
 */

public class ApiEndPoint {

    public static final String REGISTER = BuildConfig.BASE_URL + "users/register";

    public static final String STORE_CONTACTS = BuildConfig.BASE_URL + "users/storeContactDetails";

    public static final String LOG_EVENTS = BuildConfig.BASE_URL + "logs/events";

}
