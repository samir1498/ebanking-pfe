/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cards.dao;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.cards.bean.Card;
import com.transaction.bean.Transaction;
import com.transaction.dao.TransactionDaoImpl;
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
public class CardDaoImpl implements CardDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public String AddCard(Card card) {
    String query = "insert into card ("
            + "cardnumber, pin, creationdate, "
            + "expirationdate, cardstate, withdrawlimit,"
            + " holdername, id, cvv, idaccount) values\n"
            + "(?, ?, now(), now()+interval '2 year','new', ?, ?, ?, ?, ?);";

    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, card.getCardNumber());
      preparedStatement.setString(2, card.getPIN());
      preparedStatement.setBigDecimal(3, card.getWithdrawLimit());

      preparedStatement.setString(4, card.getHolderName());
      preparedStatement.setInt(5, Integer.parseInt(card.getIdClient()));
      preparedStatement.setInt(6, card.getCVV());

      preparedStatement.setInt(7, Integer.parseInt(card.getIdAccount()));
      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "failed";
    }

    return "SUCCES";
  }

  @Override
  public String AddCardTransaction(int IDtransaction, String CardNumber) {
    String query = "insert into card_transactions (cardnumber, id_transaction) values (?, ?)";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, CardNumber);
      preparedStatement.setInt(2, IDtransaction);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "failed";
    }

    return "SUCCES";
  }

  @Override
  public List<Transaction> listCardTransactions(String CardNumber) {
    String query = "select * from card_transactions"
            + " ct join transactions t"
            + " on ct.id_transaction = t.id_transaction"
            + " where cardnumber = ?"
            + " and type_transaction<>'AccountDeletion'"
            + " and state='Completed'"
            + " order by date_transaction desc;";
    List<Transaction> l = new LinkedList();
    AccountDao comptedao = new AccountDaoImpl();
    ClientDao clientdao = new ClientDaoImpl();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, CardNumber);

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
  public String EditPIN(int PIN, String CardNumber) {
    String query = "Update card set pin = ? where cardnumber = ?";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, PIN);
      preparedStatement.setString(2, CardNumber);
      
      preparedStatement.executeUpdate();
      
    } catch (SQLException ex) {
      
      Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }

    return "SUCCES";
  }

}
