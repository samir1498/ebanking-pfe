/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.factures.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Samir
 */
public class Bill implements Serializable{

  int id;
  String BillerAccountNumber;
  BigDecimal Amount;
  String PayFrom;
  String BillDate;
  String Description;
  String BillerName;
  String Currency;

  public String getCurrency() {
    return Currency;
  }

  public void setCurrency(String Currency) {
    this.Currency = Currency;
  }
  
  

  public String getBillerName() {
    return BillerName;
  }

  public void setBillerName(String BillerName) {
    this.BillerName = BillerName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBillerAccountNumber() {
    return BillerAccountNumber;
  }

  public void setBillerAccountNumber(String BillerAccountNumber) {
    this.BillerAccountNumber = BillerAccountNumber;
  }

  public BigDecimal getAmount() {
    return Amount;
  }

  public void setAmount(BigDecimal Amount) {
    this.Amount = Amount;
  }

  public String getPayFrom() {
    return PayFrom;
  }

  public void setPayFrom(String PayFrom) {
    this.PayFrom = PayFrom;
  }

  public String getBillDate() {
    return BillDate;
  }

  public void setBillDate(String BillDate) {
    this.BillDate = BillDate;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String Description) {
    this.Description = Description;
  }

}
