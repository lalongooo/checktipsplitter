package com.checktipsplitter.rest.openexchangerates;

import retrofit.ResponseCallback;
import retrofit.http.GET;

public interface OpenExchangeRatesService {

    @GET("/currencies.json")
    void getCurrencies(ResponseCallback responseCallback);
}
