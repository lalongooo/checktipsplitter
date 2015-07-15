package com.checktipsplitter.config;

public class Config {

    /**
     * The URL of the web service for the users registration 
     */
	public static final String APP_PREFERENCES_NAME = "PERMUTAS_SEP_PREFERENCES";	

    /**
     * Endpoint of the Mashape currency exchange service
     */
    public static final String MASHAPE_CURRENCY_EXCHANGE_ENDPOINT = "https://currency-exchange.p.mashape.com/";

	/**
	 * Endpoint of the Mashape currency exchange service
	 */
	public static final String OPEN_EXCHANGE_RATES_API_ENDPOINT = "https://openexchangerates.org/api";

    /**
     * Date format used in the entire application
     */
    public static final String APP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * Number of publications per page in the news feed screen (fragment).
	 */
	public static final int NEWS_FEED_ITEMS_PER_PAGE = 12;

	/**
	 * Password min length
	 */
	public static final int PASSWORD_MIN_LENGTH = 8;

	/**
	 * Password max length
	 */
	public static final int PASSWORD_MAX_LENGTH = 32;

	/**
	 * Key of the token in the query string parameter for the password reset URL
	 */
	public static final String PWD_RESET_TOKEY_KEY = "token";

	/**
	 * Key of the email in the query string parameter for the password reset URL
	 */
	public static final String PWD_RESET_EMAIL_KEY = "email";
}