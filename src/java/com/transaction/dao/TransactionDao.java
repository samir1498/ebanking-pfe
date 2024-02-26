/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.transaction.dao;

import com.Account.bean.Account;
import com.transaction.bean.Transaction;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface TransactionDao {
    public int AddTransaction(Transaction transaction);
    public List<Transaction> getTransactions(Account compte);
    public List<Transaction> getTransactions(String dateFrom, String dateTo);
}
