package com.checktipsplitter;

import android.app.Application;


import com.checktipsplitter.model.Currency;
import com.checktipsplitter.rest.mashape.MashapeCurrencyExchangeRestClient;
import com.checktipsplitter.rest.openexchangerates.OpenExchangeRatesRestClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CheckTipSplitterApplication extends Application {

    public CheckTipSplitterApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MashapeCurrencyExchangeRestClient.get().getListQuotes(new Callback<ArrayList<String>>() {
            @Override
            public void success(ArrayList<String> strings, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        OpenExchangeRatesRestClient.get().getCurrencies(new Callback<ArrayList<Currency>>() {
            @Override
            public void success(ArrayList<Currency> currencies, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}