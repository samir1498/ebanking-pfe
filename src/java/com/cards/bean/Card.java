/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cards.bean;

import com.Client.bean.Client;
import com.transaction.bean.Transaction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Samir
 */
public class Card implements Serializable {

  private String CardNumber;
  private String PIN;
  private String CreationDate;
  private String ExpirationDate;
  private String CardState;
  private BigDecimal WithdrawLimit;
  private String HolderName;
  private String idAccount;
  private String idClient;
  private List<Transaction> Transactions = new LinkedList<>();

  private int CVV;
  

  public String getIdClient() {
    return idClient;
  }

  public void setIdClient(String idClient) {
    this.idClient = idClient;
  }

  public int getCVV() {
    return CVV;
  }

  public void setCVV(int CVV) {
    this.CVV = CVV;
  }

  public String getIdAccount() {
    return idAccount;
  }

  public void setIdAccount(String idAccount) {
    this.idAccount = idAccount;
  }

  public String getHolderName() {
    return HolderName;
  }

  public void setHolderName(String HolderName) {
    this.HolderName = HolderName;
  }

  public String getCardNumber() {
    return CardNumber;
  }

  public void setCardNumber(String CardNumber) {
    this.CardNumber = CardNumber;
  }

  public String getPIN() {
    return PIN;
  }

  public void setPIN(String PIN) {
    this.PIN = PIN;
  }

  public String getCreationDate() {
    return CreationDate;
  }

  public void setCreationDate(String CreationDate) {
    this.CreationDate = CreationDate;
  }

  public String getExpirationDate() {
    return ExpirationDate;
  }

  public void setExpirationDate(String ExpirationDate) {
    this.ExpirationDate = ExpirationDate;
  }

  public String getCardState() {
    return CardState;
  }

  public void setCardState(String CardState) {
    this.CardState = CardState;
  }

  public BigDecimal getWithdrawLimit() {
    return WithdrawLimit;
  }

  public void setWithdrawLimit(BigDecimal WithdrawLimit) {
    this.WithdrawLimit = WithdrawLimit;
  }

  @Override
  public String toString() {
    return "Card{" + "CardNumber=" + CardNumber + ", PIN=" + PIN + ", CreationDate=" + CreationDate + ", ExpirationDate=" + ExpirationDate + ", CardState=" + CardState + ", WithdrawLimit=" + WithdrawLimit + ", HolderName=" + HolderName + ", idAccount=" + idAccount + ", idClient=" + idClient + ", CVV=" + CVV + '}';
  }

  public List<Transaction> getTransactions() {
    return Transactions;
  }

  public void setTransactions(List<Transaction> Transactions) {
    this.Transactions = Transactions;
  }


}
