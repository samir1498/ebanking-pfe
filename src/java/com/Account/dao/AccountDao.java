/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Account.dao;

import com.Account.bean.Account;
import com.Account.bean.CurrentAccount;
import com.Account.bean.SavingsAccount;
import com.Client.bean.Client;
import com.cards.bean.Card;
import com.currency.bean.Currency;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface AccountDao {

  public int AddAccount(Account account);

  public String AddCurrentAccount(CurrentAccount account);

  public String AddSavingsAccount(SavingsAccount account);

  public Account getAccount(String RIB);
  
  public Account getAccountById(int id);
  
  public Account getAccountByIBAN(String IBAN);

  public String DeleteAccount(int id);

  public String DeleteSavingsAccount(int id);

  public String DeleteCurrentAccount(int id);

  public String EditAccount(Account account);

  public String EditSavingsAccount(SavingsAccount account);

  public String EditCurrentAccount(CurrentAccount account);

  public List<Account> listAccounts(Client client);

  public String Deposit(int id, BigDecimal amount);

  public String Withdraw(int id, BigDecimal amount);

  public List<Card> listCards(int idClient);

  public Currency getCurrency(String code);
  
  public List<Currency> getCurrencies();
}
