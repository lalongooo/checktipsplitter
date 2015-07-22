package com.checktipsplitter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.checktipsplitter.Config;
import com.checktipsplitter.model.Currency;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrefUtils {

    /*
    * Preference to store the last date the exchange rates were synchronized
    * */
    public static final String PREF_LAST_EXCHANGE_RATE_SYNC = "last_exch_rate_sync";

    /**
     * The currency options preferences key. These are updated when they're older than {@link Config#EXCHANGE_RATES_SYNC_TIME_INTERVAL}
     */
    public static final String PREF_CURRENCY_VALUES_DESCRIPTION = "pref_curr_values";

    public static void saveCurrencyValuesDescription(final Context context, String values) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_CURRENCY_VALUES_DESCRIPTION, values).apply();
    }

    public static String[] getCurrencyValuesDescription(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sp.getString(PREF_CURRENCY_VALUES_DESCRIPTION, null);

        List<Currency> currencies = null;
        String[] sArray = null;

        if (json != null) {
            Type listType = new TypeToken<ArrayList<Currency>>() {
            }.getType();
            currencies = new Gson().fromJson(json, listType);

            if (currencies != null) {
                sArray = new String[currencies.size()];
                for (int i = 0; i < currencies.size(); i++) {
                    sArray[i] = currencies.get(i).getQuoteKey() + " - " + currencies.get(i).getQuoteDescription();
                }
            }
        }

        return sArray;
    }

    public static void saveLastExchangeRateSync(final Context context) {
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

    private static String LAST_EXCHANGE_SYNC_PREFIX = "_";

    public static boolean shouldSyncExchangeConversion(final Context context, String exchangeSource, String exchangeTarget) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        Date lastSyncDate = new Date(sp.getLong(LAST_EXCHANGE_SYNC_PREFIX + exchangeSource + exchangeTarget, 0));

        Calendar lastSyncCal = Calendar.getInstance();
        lastSyncCal.setTime(lastSyncDate);
        lastSyncCal.add(Calendar.SECOND, Config.EXCHANGE_RATES_SYNC_TIME_INTERVAL);

        return Calendar.getInstance().after(lastSyncCal);
    }

    public static void saveLastExchangeConversionSync(final Context context, String exchangeSource, String exchangeTarget) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(LAST_EXCHANGE_SYNC_PREFIX + exchangeSource + exchangeTarget, new Date().getTime()).apply();
    }

    public static void saveExchangeConversion(final Context context, String exchangeSource, String exchangeTarget, float exchangeValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putFloat(exchangeSource + exchangeTarget, exchangeValue).apply();
    }

    public static float getSavedExchangeConversion(final Context context, String exchangeSource, String exchangeTarget) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getFloat(exchangeSource + exchangeTarget, 0);
    }
}
