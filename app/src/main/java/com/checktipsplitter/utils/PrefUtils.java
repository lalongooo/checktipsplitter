package com.checktipsplitter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.checktipsplitter.Config;

import java.util.Calendar;
import java.util.Date;

public class PrefUtils {

    /*
    * Preference to store the last date the exchange rates were synchronized
    * */
    public static final String PREF_LAST_EXCHANGE_RATE_SYNC = "last_exch_rate_sync";

    public static void setLastExchangeRateSync(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(PREF_LAST_EXCHANGE_RATE_SYNC, new Date().getTime()).apply();
    }

    public static boolean shouldSyncExchangeRates(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        Date lastSyncDate = new Date(sp.getLong(PREF_LAST_EXCHANGE_RATE_SYNC, 0));

        Calendar lastSyncCal = Calendar.getInstance();
        lastSyncCal.setTime(lastSyncDate);
        lastSyncCal.add(Calendar.SECOND, Config.EXCHANGE_RATES_SYNC_TIME_INTERVAL);

        return Calendar.getInstance().after(lastSyncCal);
    }
}
