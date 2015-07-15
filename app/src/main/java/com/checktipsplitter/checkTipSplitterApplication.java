package com.checktipsplitter;

import android.app.Application;


import com.checktipsplitter.rest.mashape.MashapeCurrencyExchangeRestClient;

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
    }
}