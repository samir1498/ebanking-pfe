/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cards.dao;

import com.cards.bean.Card;
import com.transaction.bean.Transaction;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface CardDao {
  public String AddCard(Card card);
  
  public String AddCardTransaction(int IDtransaction, String CardNumber);
  
  public List<Transaction> listCardTransactions(String CardNumber);
  
  public String EditPIN(int PIN, String CardNumber);
}
