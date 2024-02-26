/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transaction.util;

import com.currency.bean.Currency;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class TransactionUtil {

  public static BigDecimal convert(Currency c1, Currency c2, BigDecimal c1Amount) throws IOException {

    String url_str = "https://api.exchangerate.host/latest";
    BigDecimal c2Amount = BigDecimal.ZERO;
    try {
      URL url = new URL(url_str);
      HttpURLConnection request = (HttpURLConnection) url.openConnection();
      request.connect();
      // Convert to JSON
      JsonParser jp = new JsonParser();
     
      JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
      JsonObject jsonobj = root.getAsJsonObject();
       

// Accessing object
      JsonObject req_result = jsonobj.get("rates").getAsJsonObject();
      BigDecimal c1ExchangeRate = req_result.get(c1.getCode()).getAsBigDecimal();
      BigDecimal c2ExchangeRate = req_result.get(c2.getCode()).getAsBigDecimal();

      BigDecimal c1AmountInEURO = c1Amount.divide(c1ExchangeRate, MathContext.DECIMAL32);

      c2Amount = c1AmountInEURO.multiply(c2ExchangeRate);

    } catch (MalformedURLException ex) {
      Logger.getLogger(TransactionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(TransactionUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    return c2Amount;

  }
}
