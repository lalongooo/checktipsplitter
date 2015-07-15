package com.checktipsplitter.rest.openexchangerates;

import com.checktipsplitter.model.Currency;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

public interface OpenExchangeRatesService {

    @GET("/currencies.json")
    void getCurrencies(Callback<ArrayList<Currency>> callback);
}
