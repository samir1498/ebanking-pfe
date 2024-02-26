/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transaction.bean;

import com.Account.bean.Account;
import com.Client.bean.Client;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Samir
 */
public class Transaction implements Serializable {

  private int Id;
  private String Date;
  private BigDecimal Amount;
  private BigDecimal ConvertedAmount;
  private String Type;
  private Account AccountFrom;
  private Account AccountTo;
  private String AccountToRIB;
  private Client ClientTo;
  private Client ClientFrom;
  private String ClienttToName;
  private String State;
  private String Description;
  private BigDecimal convertedAmoutLocalCurrency;

  public BigDecimal getConvertedAmoutLocalCurrency() {
    return convertedAmoutLocalCurrency;
  }

  public void setConvertedAmoutLocalCurrency(BigDecimal convertedAmoutLocalCurrency) {
    this.convertedAmoutLocalCurrency = convertedAmoutLocalCurrency;
  }
  

  public String getAccountToRIB() {
    return AccountToRIB;
  }

  public void setAccountToRIB(String AccountToRIB) {
    this.AccountToRIB = AccountToRIB;
  }

  public String getClienttToName() {
    return ClienttToName;
  }

  public void setClienttToName(String ClienttToName) {
    this.ClienttToName = ClienttToName;
  }

  public Client getClientTo() {
    return ClientTo;
  }

  public void setClientTo(Client ClientTo) {
    this.ClientTo = ClientTo;
  }

  public Client getClientFrom() {
    return ClientFrom;
  }

  public void setClientFrom(Client ClientFrom) {
    this.ClientFrom = ClientFrom;
  }

  public String getState() {
    return State;
  }

  public void setState(String State) {
    this.State = State;
  }

  public int getId() {
    return Id;
  }

  public void setId(int Id) {
    this.Id = Id;
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

  public String getType() {
    return Type;
  }

  public void setType(String Type) {
    this.Type = Type;
  }

  public Account getAccountFrom() {
    return AccountFrom;
  }

  public void setAccountFrom(Account AccountFrom) {
    this.AccountFrom = AccountFrom;
  }

  public Account getAccountTo() {
    return AccountTo;
  }

  public void setAccountTo(Account AccountTo) {
    this.AccountTo = AccountTo;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String Description) {
    this.Description = Description;
  }

  public BigDecimal getConvertedAmount() {
    return ConvertedAmount;
  }

  public void setConvertedAmount(BigDecimal ConvertedAmount) {
    this.ConvertedAmount = ConvertedAmount;
  }

  @Override
  public String toString() {
    return "Transaction{" + "Id=" + Id + ", Date=" + Date + ", Amount=" + Amount + ", ConvertedAmount=" + ConvertedAmount + ", Type=" + Type + ", AccountFrom=" + AccountFrom + ", AccountTo=" + AccountTo + ", ClientTo=" + ClientTo + ", ClientFrom=" + ClientFrom + ", ClienttToName=" + ClienttToName + ", State=" + State + ", Description=" + Description + '}';
  }

}
