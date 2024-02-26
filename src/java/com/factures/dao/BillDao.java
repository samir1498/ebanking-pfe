/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.factures.dao;

import com.Account.bean.Account;
import com.factures.bean.Bill;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface BillDao {
  public String AddBill(Bill bill);
  
  public List<Bill> getBills(Account account);
}
