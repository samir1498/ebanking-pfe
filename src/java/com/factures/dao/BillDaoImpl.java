/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.factures.dao;

import com.Account.bean.Account;
import com.factures.bean.Bill;
import connexion.util.SingletonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class BillDaoImpl implements BillDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public String AddBill(Bill bill) {
    String query = "insert into factures (BillerAccountNumber, Amount, PayFrom, BillDate, Description, BillerName, Currency)"
            + "values (?, ?, ?, now(), ?, ?, ?)";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, bill.getBillerAccountNumber());
      preparedStatement.setBigDecimal(2, bill.getAmount());
      preparedStatement.setString(3, bill.getPayFrom());
      preparedStatement.setString(4, bill.getDescription());
      preparedStatement.setString(5, bill.getBillerName());
      preparedStatement.setString(6, bill.getCurrency());
      
      preparedStatement.executeUpdate();
      return "SUCCES";

    } catch (SQLException ex) {
      Logger.getLogger(BillDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "FAILED";
  }

  @Override
  public List<Bill> getBills(Account account) {
    String query = "select id, BillerAccountNumber, Amount, PayFrom, BillDate, Description, BillerName from factures"
            + " where PayFrom = ? or PayFrom= ?";
    List<Bill> l = new LinkedList();

    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, account.getRIB());
      preparedStatement.setString(2, account.getIBAN());
      
      ResultSet rs = preparedStatement.executeQuery();
      
      while(rs.next()){
        Bill bill  = new Bill();
        bill.setAmount(rs.getBigDecimal("Amount"));
        bill.setBillDate(rs.getString("BillDate"));
        bill.setBillerAccountNumber(rs.getString("BillerAccountNumber"));
        bill.setDescription(rs.getString("Description"));
        bill.setId(rs.getInt("id"));
        bill.setPayFrom(rs.getString("PayFrom"));
        bill.setBillerName(rs.getString("BillerName"));
        bill.setCurrency(account.getCurrency().getSymbol());
        
        l.add(bill);
      }


    } catch (SQLException ex) {
      Logger.getLogger(BillDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    return l;
  }

}
