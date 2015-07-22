package com.checktipsplitter.rest.mashape;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface MashapeCurrencyExchangeService {

    @GET("/listquotes")
    void getListQuotes(Callback<ArrayList<String>> callback);

    @GET("/exchange")
    void exchange(@Query("from") String source, @Query("to") String target, Callback<Float> callback);
}
