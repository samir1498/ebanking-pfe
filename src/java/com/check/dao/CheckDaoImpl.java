/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.check.dao;

import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.dao.ClientDaoImpl;
import com.check.bean.Check;
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
public class CheckDaoImpl implements CheckDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public String AddCheck(Check check) {
    String query = "insert into checks"
            + " (checknumber, checkaccount,"
            + " checkdate, checkpayee,"
            + " checkcurrency, amount, state)"
            + " values "
            + " (?, ?, TO_DATE(?, 'YYYY/MM/DD'), ?, ?, ?, ?)";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setString(1, check.getCheckNumber());
      preparedStatement.setInt(2, Integer.parseInt(check.getAccount()));
      preparedStatement.setString(3, check.getDate());

      preparedStatement.setString(4, check.getPayee());
      preparedStatement.setString(5, check.getCurrency());
      preparedStatement.setBigDecimal(6, check.getAmount());
      preparedStatement.setString(7, check.getState());

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public Check getCheck(int checkNumber) {
    String query = "select checknumber, checkaccount, checkdate, checkpayee,\n"
            + "            checkcurrency, amount, state\n"
            + "            from checks \n"
            + "            where checknumber =? ";
    Check check = new Check();
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setString(1, String.valueOf(checkNumber));

      ResultSet rs = preparedStatement.executeQuery();
      AccountDao accountdao = new AccountDaoImpl();

      if (rs.next()) {

        check.setCheckNumber(rs.getString("checknumber"));
        //get rib from id
        String RIB = accountdao.getAccountById(rs.getInt("checkaccount")).getRIB();
        check.setAccount(RIB);
        check.setAmount(rs.getBigDecimal("amount"));
        //get currency symbol
        String currency = accountdao.getCurrency(rs.getString("checkcurrency")).getSymbol();
        check.setCurrency(currency);
        check.setDate(rs.getString("checkdate"));
        check.setPayee(rs.getString("checkpayee"));
        check.setState(rs.getString("state"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return check;
  }

  @Override
  public List<Check> getChecks(int idClient) {
    String query = "select checknumber, checkaccount, checkdate, checkpayee,\n"
            + "            checkcurrency, amount, state\n"
            + "            from client c join account a on c.id = a.ownerid \n"
            + "            join checks ck on ck.checkaccount = a.id\n"
            + "            where c.id =? ";
    List<Check> l = new LinkedList<>();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, idClient);

      ResultSet rs = preparedStatement.executeQuery();
      AccountDao accountdao = new AccountDaoImpl();

      while (rs.next()) {
        Check check = new Check();
        check.setCheckNumber(rs.getString("checknumber"));
        //get rib from id
        String Name = accountdao.getAccountById(rs.getInt("checkaccount")).getName();
        check.setAccount(Name);
        check.setAmount(rs.getBigDecimal("amount"));
        //get currency symbol
        String currency = accountdao.getCurrency(rs.getString("checkcurrency")).getSymbol();
        check.setCurrency(currency);
        check.setDate(rs.getString("checkdate"));
        check.setPayee(rs.getString("checkpayee"));
        check.setState(rs.getString("state"));
        l.add(check);

      }

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return l;

  }

  @Override
  public String UpdateCheckState(String state, String CheckNumber) {
    String query = "update checks set state=? where checknumber=?";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setString(1, state);
      preparedStatement.setString(2, CheckNumber);


      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

}
