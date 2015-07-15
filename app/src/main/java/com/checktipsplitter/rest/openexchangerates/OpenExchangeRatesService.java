package com.checktipsplitter.rest.openexchangerates;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

public interface OpenExchangeRatesService {

    @GET("/listquotes")
    void getListQuotes(Callback<ArrayList<String>> callback);
}
