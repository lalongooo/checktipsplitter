package com.checktipsplitter.network;/*
* By Jorge E. Hernandez (@lalongooo) 2015
* */

import android.content.Context;

import com.checktipsplitter.model.Currency;
import com.checktipsplitter.rest.mashape.MashapeCurrencyExchangeRestClient;
import com.checktipsplitter.rest.openexchangerates.OpenExchangeRatesRestClient;
import com.checktipsplitter.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class ExchangeRateUpdater {

    public interface OnExchangeRateSyncComplete {
        void onExchangeRateSyncComplete();
    }

    public static void updateExchangeRates(final Context context, final OnExchangeRateSyncComplete[] listeners) {
        if (PrefUtils.shouldSyncExchangeRates(context)) {
            MashapeCurrencyExchangeRestClient.get().getListQuotes(new Callback<ArrayList<String>>() {
                @Override
                public void success(final ArrayList<String> strings, Response response) {
                    OpenExchangeRatesRestClient.get().getCurrencies(new ResponseCallback() {
                        @Override
                        public void success(Response response) {

                            List<Currency> currencies = new ArrayList<>();

                            String responseText = new String(
                                    ((TypedByteArray) response.getBody()).getBytes()
                            );

                            JsonObject jsonObject = new JsonParser()
                                    .parse(responseText)
                                    .getAsJsonObject();

                            for (Map.Entry entry : jsonObject.entrySet()) {
                                JsonElement jsonElement = (JsonElement) entry.getValue();

                                if (strings.contains(entry.getKey().toString())) {
                                    currencies.add(new Currency(entry.getKey().toString(), jsonElement.getAsString()));
                                }
                            }


                            PrefUtils.saveLastExchangeRateSync(context);
                            PrefUtils.saveCurrencyValuesDescription(context, new Gson().toJson(currencies));

                            if (listeners != null && listeners.length > 0) {
                                for (OnExchangeRateSyncComplete nExchangeRateSyncComplete : listeners) {
                                    nExchangeRateSyncComplete.onExchangeRateSyncComplete();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        } else {
            if (listeners != null && listeners.length > 0) {
                for (OnExchangeRateSyncComplete nExchangeRateSyncComplete : listeners) {
                    nExchangeRateSyncComplete.onExchangeRateSyncComplete();
                }
            }
        }
    }
}
