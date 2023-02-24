package com.example.currencyexchange.services;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CurrencyExchangeService {

    Map<String, Double> getAllExchangeRates(String date, List<String> currency) throws IOException, InterruptedException;

    String getCurrencyData(String currency) throws IOException, InterruptedException;

}
