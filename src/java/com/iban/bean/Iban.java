/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iban.bean;

import com.rib.bean.RIB;
import com.transaction.util.RIBValidationMsgs;
import java.util.HashMap;

import nl.garvelink.iban.CountryCodes;
import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.Modulo97;

/**
 *
 * @author Samir
 */
public class Iban {

  private static final String SOURCES
          = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String RESULTS
          = "123456789123456789234567890123456789";

  private String AccountNumber;
  private String BankCode;
  private String CountryCode;
  private String CheckDigits;

  public String getBankCode() {
    return BankCode;
  }

  public void setBankCode(String BankCode) {
    this.BankCode = BankCode;
  }

  public String getAccountNumber() {
    return AccountNumber;
  }

  public void setAccountNumber(String AccountNumber) {
    this.AccountNumber = AccountNumber;
  }

  public String getCountryCode() {
    return CountryCode;
  }

  public void setCountryCode(String CountryCode) {
    this.CountryCode = CountryCode;
  }

  public String getCheckDigits() {
    return CheckDigits;
  }

  public void setCheckDigits(String CheckDigits) {
    this.CheckDigits = CheckDigits;
  }

  @Override
  public String toString() {
    return IBAN.parse(CountryCode + CheckDigits + BankCode + AccountNumber).toString();
  }

  private static int computeCheckSum(String countryCode, String AccountNumber) {

    int check = Modulo97.calculateCheckDigits(countryCode, AccountNumber);

    return check;
  }

  public static boolean isValid(String tmpIban) {
    boolean valid = Modulo97.verifyCheckDigits(tmpIban);
    return valid;
  }

  // function to generate a random string of length n
  private static String generateAccountNumber(int n) {
    // chose a Character random from this String
    String AlphaNumericString = "0123456789";

    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(n);

    for (int i = 0; i < n; i++) {

      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index = (int) (AlphaNumericString.length() * Math.random());

      // add Character one by one in end of sb
      sb.append(AlphaNumericString.charAt(index));
    }

    return sb.toString();
  }

  public static Iban toIBAN(String Iban) {
    boolean valid = Modulo97.verifyCheckDigits(Iban);
    if (valid) {
      Iban iban = new Iban();
      iban.setCountryCode(Iban.substring(0, 2));
      iban.setCheckDigits(Iban.substring(2, 4));
      iban.setBankCode(Iban.substring(4, 9));
      iban.setAccountNumber(Iban.substring(9));
      return iban;
    }

    return null;

  }

  public static String composeIBAN(String countryCode, String BankCode) {
    final StringBuilder buffer = new StringBuilder(BankCode.toUpperCase());

    for (int i = 0; i < BankCode.length(); i++) {

      buffer.setCharAt(i, RESULTS.charAt(SOURCES.indexOf(
              buffer.charAt(i))));
    }
    //get iban length for country code
    int length = CountryCodes.getLengthForCountryCode(countryCode);
    //Calculate account number length 
    //(iban length for country code - (countrycodeLength + checkdigit=2))-bank code length;
    int accNumLength = (length - 4) - BankCode.length();
    //generate account number
    String AccountNumber = BankCode + generateAccountNumber(accNumLength);
    //calculate checkdigits
    int checksum = computeCheckSum(countryCode, AccountNumber);
    //Build iban String
    String iban = countryCode.concat(String.valueOf(checksum)).concat(AccountNumber);
    //Check if valid
    if (!isValid(iban)) {
      //if not valid recursive call until valid
      return composeIBAN(countryCode, BankCode);
    }
    //return iban string when valid
    return iban;
  }

  public static void main(String[] args) {
      String arString = "غير صالح";
      RIBValidationMsgs msg = new RIBValidationMsgs();
      msg.setARMessage(arString);
      System.out.println(msg.getARMessage());

      System.out.println(RIB.composeRIB("54545", "56987", "11111111111"));
      
      /*boolean check = true;
      HashMap<String, Boolean> map = new HashMap<>();
      for (int i = 0; i < 10; i++) {
      System.out.println();
      String iban = composeIBAN("DZ", "54545");
      System.out.println(toIBAN("DZ155454512616799771057162"));
      System.out.println(iban);
      System.out.println(isValid(iban));
      if (!isValid(iban)) {
      check = false;
      map.put(iban, isValid(iban));
      }
      
      }
      
      System.out.println("check : " + check);
      
      System.out.println("Map : " + map);*/
      
    boolean check = true;
    HashMap<String, Boolean> map = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      System.out.println();
      String iban = composeIBAN("DZ", "05569");
      System.out.println(toIBAN("DZ320A56904074095865050687"));
      //String friban = "FR970A569967032752290450697";
      System.out.println(iban);
      System.out.println(isValid(iban));
      if (!isValid(iban)) {
        check = false;
        map.put(iban, isValid(iban));
      }

    }

    System.out.println("check : " + check);

    System.out.println("Map : " + map);

  }

}
