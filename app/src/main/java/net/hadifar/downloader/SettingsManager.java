package net.hadifar.downloader;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Amir on 6/18/2017 AD
 * Project : Downloader
 * Hadifar.net
 */

public class SettingsManager {
    private static final String DEFAULT_PREFERENCES_NAME = "defaultPreferences";

    private static final String PREFERENCE_FIRST_RUN = "isFirstRun";


    private static SharedPreferences getDefaultPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isFirstRun(Context context) {
        SharedPreferences preferences = getDefaultPreferences(context);
        boolean isFirstRun = preferences.getBoolean(PREFERENCE_FIRST_RUN, true);
        if (!isFirstRun)
            preferences.edit().putBoolean(PREFERENCE_FIRST_RUN, false).apply();
        return isFirstRun;
    }
}
