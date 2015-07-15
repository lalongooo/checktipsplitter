package com.checktipsplitter.rest.openexchangerates;

import com.checktipsplitter.config.Config;

import retrofit.RestAdapter;

public class OpenExchangeRatesRestClient {

    private static OpenExchangeRatesService restClient;

    static {
        setupRestClient();
    }

    private OpenExchangeRatesRestClient() {}

    public static OpenExchangeRatesService get() {
        return restClient;
    }

    private static void setupRestClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.OPEN_EXCHANGE_RATES_API_ENDPOINT)
                .build();

        restClient = restAdapter.create(OpenExchangeRatesService.class);
    }
}
