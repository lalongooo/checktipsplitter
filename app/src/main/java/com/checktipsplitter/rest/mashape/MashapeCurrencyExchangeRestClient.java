package com.checktipsplitter.rest.mashape;

import com.checktipsplitter.config.Config;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class MashapeCurrencyExchangeRestClient {

    private static MashapeCurrencyExchangeService restClient;

    static {
        setupRestClient();
    }

    private MashapeCurrencyExchangeRestClient() {}

    public static MashapeCurrencyExchangeService get() {
        return restClient;
    }

    private static void setupRestClient() {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-Mashape-Key", "l3DQioyM7QmshMfWRKZdHuPHQF9Fp1Z56HLjsnGFWH9NFdYD4R");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.MASHAPE_CURRENCY_EXCHANGE_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        restClient = restAdapter.create(MashapeCurrencyExchangeService.class);
    }
}
