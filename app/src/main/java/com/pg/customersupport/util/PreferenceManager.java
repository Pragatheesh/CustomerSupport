package com.pg.customersupport.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Handles shared preference to store user basic
 * information in key value pairs
 *
 * @author PG
 * @version 1.0
 */
public class PreferenceManager {
    private static final String PREFERENCE_FILE_KEY = "com.pg.customersupport.app_shared_preference";

    private static final String AUTH_TOKEN = "_auth_token";
    private static final String USER_NAME = "_user_name";
    private static final String USER_AVATAR = "_user_avatar";

    private final SharedPreferences mPrefs;

    public PreferenceManager(Context context) {
        mPrefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);

    }

    /**
     * Method to store auth token into the shared preference
     *
     * @param authToken The auth token of the logged in user
     */
    public void storeAuthToken(String authToken) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(AUTH_TOKEN, authToken);
        editor.apply();
    }

    /**
     * Method to access the stored auth token of the logged in user
     *
     * @return The auth token if available in the shared preference
     * else return a null value
     */
    public String getAccessToken() {
        return mPrefs.getString(AUTH_TOKEN, null);
    }

    /**
     * Removes the currently stored auth token of the logged in user
     */
    public void removeAuthToken() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(AUTH_TOKEN);
        editor.apply();
    }

    /**
     * Method to clear all shared preference stored values.
     * Ideal to use when a user logs out of a system
     */
    public void clearAll() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Check if the logged in user is authenticated or not
     *
     * @return true if the user is authenticated
     */
    public Boolean isUserAuthenticated() {
        String token = mPrefs.getString(AUTH_TOKEN, null);
        return (token != null && !TextUtils.isEmpty(token));
    }
}
