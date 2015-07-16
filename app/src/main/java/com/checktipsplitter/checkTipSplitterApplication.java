package com.checktipsplitter;

import android.app.Application;

import com.checktipsplitter.network.ExchangeRateUpdater;

public class CheckTipSplitterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExchangeRateUpdater.updateExchangeRates(this, null);
    }
}