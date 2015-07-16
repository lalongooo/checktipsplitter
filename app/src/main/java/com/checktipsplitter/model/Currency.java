package com.checktipsplitter.model;

/*
* By Jorge E. Hernandez (@lalongooo) 2015
* */

public class Currency {

    private String mQuoteKey;
    private String mQuoteDescription;

    public Currency(String quoteKey, String quoteDescription) {
        this.mQuoteKey = quoteKey;
        this.mQuoteDescription = quoteDescription;
    }

    public String getQuoteKey() {
        return mQuoteKey;
    }

    public String getQuoteDescription() {
        return mQuoteDescription;
    }
}
