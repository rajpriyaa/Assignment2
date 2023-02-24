package com.example.currencyexchange;

import com.example.currencyexchange.services.CurrencyExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest
class CurrencyexchangeApplicationTests {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Test
    public void t1() throws Exception {
        Map<String, Double> exchangeRates = currencyExchangeService.getAllExchangeRates("2020-01-01", Arrays.asList("AED", "CAD", "EUR", "INR", "JPY"));
        System.out.println(exchangeRates);
    }

    @Test
    public void t2() throws Exception {
        String data = currencyExchangeService.getCurrencyData("INR");
        System.out.println(data);
    }

    @Test
    public void t3() throws Exception {
        String data = currencyExchangeService.getCurrencyData("EUR");
        System.out.println(data);
    }
}
