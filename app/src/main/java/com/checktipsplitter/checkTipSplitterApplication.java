package com.checktipsplitter;

import android.app.Application;
import android.util.Log;

import com.checktipsplitter.model.Currency;
import com.checktipsplitter.rest.mashape.MashapeCurrencyExchangeRestClient;
import com.checktipsplitter.rest.openexchangerates.OpenExchangeRatesRestClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class CheckTipSplitterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MashapeCurrencyExchangeRestClient.get().getListQuotes(new Callback<ArrayList<String>>() {
            @Override
            public void success(final ArrayList<String> strings, Response response) {


                OpenExchangeRatesRestClient.get().getCurrencies(new ResponseCallback() {
                    @Override
                    public void success(Response response) {

                        ArrayList<Currency> currencies = new ArrayList<>();

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
    }
}