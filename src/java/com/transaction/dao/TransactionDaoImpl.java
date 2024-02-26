/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transaction.dao;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.transaction.bean.Transaction;
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
public class TransactionDaoImpl implements TransactionDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public int AddTransaction(Transaction transaction) {
    try {
      String query = "INSERT INTO public.transactions "
              + "(somme, date_transaction,"
              + " type_transaction, "
              + " fromaccount, toaccount,"
              + " state, clientfrom, clientto,"
              + " description, convertedamount,"
              + " clienttoname, accounttorib, convertedamoutlocalcurrency) "
              + " VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id_transaction ;";

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setBigDecimal(1, transaction.getAmount());
      preparedStatement.setString(2, transaction.getType());
      preparedStatement.setInt(3, transaction.getAccountFrom().getId());
      preparedStatement.setInt(4, transaction.getAccountTo().getId());
      preparedStatement.setString(5, transaction.getState());
      preparedStatement.setInt(6, transaction.getClientFrom().getId());
      preparedStatement.setInt(7, transaction.getClientTo().getId());
      preparedStatement.setString(8, transaction.getDescription());
      preparedStatement.setBigDecimal(9, transaction.getConvertedAmount());
      preparedStatement.setString(10, transaction.getClienttToName());
      preparedStatement.setString(11, transaction.getAccountToRIB());
      preparedStatement.setBigDecimal(12, transaction.getConvertedAmoutLocalCurrency());
      ResultSet rs = preparedStatement.executeQuery();
      if(rs.next()){
        int id = rs.getInt("id_transaction");
        return id;
      }

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1;
  }

  @Override
  public List<Transaction> getTransactions(Account compte) {
    String query = "SELECT id_transaction,"
            + " somme, date_transaction,"
            + " type_transaction,"
            + " fromaccount,"
            + " toaccount, state,"
            + " description, convertedamount,"
            + " clienttoname, accounttorib"
            + " FROM public.transactions where"
            + " (fromaccount=? or toaccount=?)"
            + " and type_transaction<>'AccountDeletion'"
            + " and state='Completed'"
            + " order by date_transaction desc;";

    List<Transaction> l = new LinkedList<>();
    AccountDao comptedao = new AccountDaoImpl();
    ClientDao clientdao = new ClientDaoImpl();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setLong(1, compte.getId());
      preparedStatement.setLong(2, compte.getId());

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Transaction transaction = new Transaction();

        transaction.setId(rs.getInt("id_transaction"));
        transaction.setType(rs.getString("type_transaction"));

        Account accountFrom = comptedao.getAccountById(rs.getInt("fromaccount"));
        transaction.setAccountFrom(accountFrom);

        Account accountTo = comptedao.getAccountById(rs.getInt("toaccount"));
        transaction.setAccountTo(accountTo);

        transaction.setAmount(rs.getBigDecimal("somme"));
        transaction.setDate(rs.getString("date_transaction"));
        transaction.setState(rs.getString("state"));

        Client clienfrom = clientdao.getClientByAccountId(accountFrom.getId());
        Client cliento = clientdao.getClientByAccountId(accountTo.getId());

        transaction.setClientFrom(clienfrom);
        transaction.setClientTo(cliento);
        transaction.setDescription(rs.getString("description"));
        transaction.setConvertedAmount(rs.getBigDecimal("convertedamount"));
        transaction.setClienttToName(rs.getString("clienttoname"));
        transaction.setAccountToRIB(rs.getString("accounttorib"));
        l.add(transaction);
      }
    } catch (SQLException e) {
      Logger.getLogger(TransactionDaoImpl.class.getName()).log(Level.SEVERE, null, e);

      try {
        if (connexion != null) {

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(TransactionDaoImpl.class.getName()).log(Level.SEVERE, null, e2);
      }
    }
    return l;
  }

  @Override
  public List<Transaction> getTransactions(String dateFrom, String dateTo) {
    String query = "SELECT id_transaction, somme,"
            + " date_transaction, type_transaction,"
            + " fromaccount, toaccount, state, description, clienttoname, accounttorib"
            + " FROM public.transactions where"
            + " date_transaction between"
            + " (cast(? as text) || ' 00:00:00')::timestamp "
            + " and (cast(? as text) || ' 23:59:59')::timestamp\n"
            + " and state='Completed'"
            + " order by date_transaction desc;";

    List<Transaction> l = new LinkedList<>();
    AccountDao comptedao = new AccountDaoImpl();
    ClientDao clientdao = new ClientDaoImpl();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, dateFrom);
      preparedStatement.setString(2, dateTo);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Transaction transaction = new Transaction();

        transaction.setId(rs.getInt("id_transaction"));
        transaction.setType(rs.getString("type_transaction"));

        Account accountFrom = comptedao.getAccount(rs.getString("fromaccount"));
        transaction.setAccountFrom(accountFrom);

        Account accountTo = comptedao.getAccount(rs.getString("toaccount"));
        transaction.setAccountTo(accountTo);

        transaction.setDescription(rs.getString("description"));

        transaction.setAmount(rs.getBigDecimal("somme"));
        transaction.setDate(rs.getString("date_transaction"));
        transaction.setState(rs.getString("state"));
        Client clienfrom = clientdao.getClientByAccountId(accountFrom.getId());
        Client cliento = clientdao.getClientByAccountId(accountTo.getId());
        transaction.setClientFrom(clienfrom);
        transaction.setClientTo(cliento);

        transaction.setClienttToName(rs.getString("clienttoname"));
        
        transaction.setAccountToRIB(rs.getString("accounttorib"));
        l.add(transaction);
      }
    } catch (SQLException e) {
      Logger.getLogger(TransactionDaoImpl.class.getName()).log(Level.SEVERE, null, e);
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(TransactionDaoImpl.class.getName()).log(Level.SEVERE, null, e2);
      }
    }
    return l;
  }

}
