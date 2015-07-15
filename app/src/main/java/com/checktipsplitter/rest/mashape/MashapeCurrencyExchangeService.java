package com.checktipsplitter.rest.mashape;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

public interface MashapeCurrencyExchangeService {

    @GET("/listquotes")
    void getListQuotes(Callback<ArrayList<String>> callback);
}
