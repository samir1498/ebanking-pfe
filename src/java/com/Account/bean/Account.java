/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Account.bean;

import com.cards.bean.Card;
import com.currency.bean.Currency;
import com.transaction.bean.Transaction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Samir
 */
public class Account implements Serializable {

  protected String Name;
  protected BigDecimal Balance;
  protected int ownerID;
  protected String AccountType;
  protected Currency Currency;
  protected String AccountState;
  protected int Id;
  protected BigDecimal transferLimit;
  protected String CreationDate;
  private String RIB;
  private String IBAN;
  protected List<Transaction> Transactions = new LinkedList<>();
  protected List<Card> Cards = new LinkedList<>();

  public List<Card> getCards() {
    return Cards;
  }

  public void setCards(List<Card> Cards) {
    this.Cards = Cards;
  }

  public String getCreationDate() {
    return CreationDate;
  }

  public void setCreationDate(String CreationDate) {
    this.CreationDate = CreationDate;
  }

  public BigDecimal getTransferLimit() {
    return transferLimit;
  }

  public void setTransferLimit(BigDecimal transferLimit) {
    this.transferLimit = transferLimit;
  }

  public List<Transaction> getTransactions() {
    return Transactions;
  }

  public void setTransactions(List<Transaction> Transactions) {
    this.Transactions = Transactions;
  }

  public String getName() {
    return Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

  public BigDecimal getBalance() {
    return Balance;
  }

  public void setBalance(BigDecimal Balance) {
    this.Balance = Balance;
  }

  public int getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(int ownerID) {
    this.ownerID = ownerID;
  }

  public String getAccountType() {
    return AccountType;
  }

  public void setAccountType(String AccoutType) {
    this.AccountType = AccoutType;
  }

  public Currency getCurrency() {
    return Currency;
  }

  public void setCurrency(Currency Currency) {
    this.Currency = Currency;
  }

  public String getAccountState() {
    return AccountState;
  }

  public void setAccountState(String AccountState) {
    this.AccountState = AccountState;
  }

  public int getId() {
    return Id;
  }

  public void setId(int Id) {
    this.Id = Id;
  }

  @Override
  public String toString() {
    return "Account{" + "Name=" + Name + ", Balance=" + Balance + ", ownerID=" + ownerID + ", AccoutType=" + AccountType + ", Currency=" + Currency + ", AccountState=" + AccountState + ", Id=" + Id + ", transferLimit=" + transferLimit + ", CreationDate=" + CreationDate + ", Transactions=" + Transactions + '}';
  }



  public String getRIB() {
    return RIB;
  }

  public void setRIB(String RIB) {
    this.RIB = RIB;
  }

  public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

}
