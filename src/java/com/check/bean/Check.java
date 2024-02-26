/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.check.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Samir
 */
public class Check implements Serializable {

  private String CheckNumber;
  private String Date;
  private BigDecimal Amount;
  private String Payee;
  private String Account;
  private String currency;
  private String state;

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getAccount() {
    return Account;
  }

  public void setAccount(String Account) {
    this.Account = Account;
  }

  public String getCheckNumber() {
    return CheckNumber;
  }

  public void setCheckNumber(String CheckNumber) {
    this.CheckNumber = CheckNumber;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String Date) {
    this.Date = Date;
  }

  public BigDecimal getAmount() {
    return Amount;
  }

  public void setAmount(BigDecimal Amount) {
    this.Amount = Amount;
  }

  public String getPayee() {
    return Payee;
  }

  public void setPayee(String Payee) {
    this.Payee = Payee;
  }

  @Override
  public String toString() {
    return "Check{" + "CheckNumber=" + CheckNumber + ", Date=" + Date + ", Amount=" + Amount + ", Payee=" + Payee + ", Account=" + Account + ", currency=" + currency + '}';
  }

  // function to generate a random string of length n
  public static String generateCheckNumber(int n) {
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
}
