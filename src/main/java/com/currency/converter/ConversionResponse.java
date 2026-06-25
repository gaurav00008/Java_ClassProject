package com.currency.converter;

/**
 * This is our Response Model (also called DTO - Data Transfer Object).
 * It holds the result data that we send back to the frontend as JSON.
 */
public class ConversionResponse {

    private String fromCurrency;
    private String toCurrency;
    private double originalAmount;
    private double exchangeRate;
    private double convertedAmount;

    // Constructor
    public ConversionResponse(String fromCurrency, String toCurrency,
                               double originalAmount, double exchangeRate,
                               double convertedAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
    }

    // Getters (Spring uses these to build JSON automatically)
    public String getFromCurrency() { return fromCurrency; }
    public String getToCurrency()   { return toCurrency; }
    public double getOriginalAmount()  { return originalAmount; }
    public double getExchangeRate()    { return exchangeRate; }
    public double getConvertedAmount() { return convertedAmount; }
}
