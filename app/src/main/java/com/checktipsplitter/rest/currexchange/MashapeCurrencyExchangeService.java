package com.checktipsplitter.rest.currexchange;

import com.checktipsplitter.model.City;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MashapeCurrencyExchangeService {

    @GET("/listquotes")
    void getListQuotes(@Path("id") String id, Callback<ArrayList<City>> callback);
}
