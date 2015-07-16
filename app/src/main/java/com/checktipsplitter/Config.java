package com.checktipsplitter;

public class Config {
    /**
     * Endpoint of the Mashape currency exchange service
     */
    public static final String MASHAPE_CURRENCY_EXCHANGE_ENDPOINT = "https://currency-exchange.p.mashape.com/";

    /**
     * Endpoint of the Open Exchange Rates service
     */
    public static final String OPEN_EXCHANGE_RATES_API_ENDPOINT = "https://openexchangerates.org/api";

    /**
     * Time (in seconds) between each synchronization of exchange rates.
     */
    public static final int EXCHANGE_RATES_SYNC_TIME_INTERVAL = 10;
}