package com.example.currencyexchange.controllers;

import com.example.currencyexchange.services.CurrencyExchangeService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
public class CurrencyExchangeController {
    @Autowired
    private CurrencyExchangeService exchangeService;

    public CurrencyExchangeController(CurrencyExchangeService exchangeService) {

    }

    @GetMapping("/fetchData")
    public ResponseEntity<String> getCurrencyData(@RequestParam String currency) throws IOException, InterruptedException {
        return ResponseEntity.ok(exchangeService.getCurrencyData(currency));
    }

    @GetMapping("/currency")
    public ResponseEntity<Map<String, Double>> fetchDataByDate(@RequestParam(required = false) String date) throws
            InterruptedException, ExecutionException, IOException {
        if(date == null) {
            date = LocalDate.now().toString();
        }
//store all currency in a list
        List<String> allcurrency = Arrays.asList("AED", "CAD", "EUR", "INR", "JPY");
//create a concurrent hashmap to store the exchanged rate
        Map<String, Double> exchangeRates = new ConcurrentHashMap<>();
//create a thread pool of list total size
        ExecutorService executorService = Executors.newFixedThreadPool(allcurrency.size());
        
        String DatetoUse = date;

        //new list of all conversions.
        List<Callable<Map.Entry<String, Double>>> conversions = allcurrency.stream().map(currency -> {
            return (Callable<Map.Entry<String, Double>>)() -> {
        
                   try{
                    Double rate = exchangeService.getAllExchangeRates(DatetoUse, Collections.singletonList(currency)).get(currency);
                    return new AbstractMap.SimpleEntry<>(currency, rate);
                   } catch(IOException | InterruptedException e) {
                       e.printStackTrace();
                       return null;
                   }
                };

        }).collect(Collectors.toList());

        List<Future<Map.Entry<String, Double>>> futures = executorService.invokeAll(conversions, 10, TimeUnit.SECONDS);

        for(Future<Map.Entry<String, Double>> future : futures) {
            Map.Entry<String, Double> entry = future.get();
            if(entry != null) {
                exchangeRates.put(entry.getKey(), entry.getValue());
            }
        }

        //create a new workbook object
        Workbook workbook;
        //create new sheet
        Sheet sheet;
        File file = new File("currency.xlsx");

        if(!file.exists()){
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Exchange Rates");

            //create new header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("BASE_CURRENCY");
            headerRow.createCell(1).setCellValue("TARGET_CURRENCY");
            headerRow.createCell(2).setCellValue("EXCHANGE_RATE");
            headerRow.createCell(3).setCellValue("CREATED_TS");
        } else {
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
        }

        int lastRowIndex = sheet.getLastRowNum();
        for (int i = 0; i < allcurrency.size(); i++) {
            //create data rows
            Row r = sheet.createRow(lastRowIndex + i + 1);
            Double rate = exchangeRates.get(allcurrency.get(i));
            if (rate != null) {
                r.createCell(0).setCellValue("USD");
                r.createCell(1).setCellValue(allcurrency.get(i));
                r.createCell(2).setCellValue(rate);
                r.createCell(3).setCellValue(LocalDateTime.now().toString());
            }
        }

        //write the workbook to a file
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();

        executorService.shutdown();
        return ResponseEntity.ok().header("timeout", "true").body(exchangeRates);
    }
}

