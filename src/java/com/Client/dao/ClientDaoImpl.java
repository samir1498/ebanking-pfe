/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Client.dao;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.login.dao.LoginDaoImpl;
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
public class ClientDaoImpl implements ClientDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public Client getClientByUserName(Client client) {
    String query = "SELECT id, username,"
            + " registrationdate, phonenumber, email,"
            + " adresse, clientstate, clienttype, profile_pic_path, fullname"
            + " FROM public.client where username=?;";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, client.getUsername());

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        client.setId(rs.getInt("id"));
        client.setUsername(rs.getString("username"));
        client.setRegistrationDate(rs.getString("registrationdate"));
        client.setPhoneNumber(rs.getString("phonenumber"));

        client.setEmail(rs.getString("email"));
        client.setAdress(rs.getString("adresse"));
        client.setClientState(rs.getString("clientstate"));
        client.setClientType(rs.getString("clienttype"));
        client.setProfilePicturePath(rs.getString("profile_pic_path"));
        client.setFullName(rs.getString("fullname"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return client;
  }

  @Override
  public Client getClientById(int id) {
    Client client = new Client();
    String query = "SELECT id, username,"
            + " registrationdate, phonenumber, email,"
            + " adresse, clientstate, clienttype, documentspath, profile_pic_path, fullname"
            + " FROM public.client where id=?;";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {

        client.setId(rs.getInt("id"));
        client.setUsername(rs.getString("username"));
        client.setRegistrationDate(rs.getString("registrationdate"));
        client.setPhoneNumber(rs.getString("phonenumber"));
        client.setEmail(rs.getString("email"));
        client.setAdress(rs.getString("adresse"));
        client.setClientState(rs.getString("clientstate"));
        client.setClientType(rs.getString("clienttype"));
        client.setProfilePicturePath(rs.getString("profile_pic_path"));
        client.setFullName(rs.getString("fullname"));
        client.setDocumentsPath(rs.getString("documentspath"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return client;
  }

  @Override
  public Client getClientByAccountId(int id) {
    Client client = new Client();
    String query = "SELECT c.id, username,"
            + " registrationdate, phonenumber, email,"
            + " adresse, clientstate, clienttype, documentspath, fullname, profile_pic_path"
            + " FROM public.client c join account a on c.id = a.ownerid where a.id = ?;";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        client.setId(rs.getInt("id"));
        client.setUsername(rs.getString("username"));
        client.setRegistrationDate(rs.getString("registrationdate"));
        client.setPhoneNumber(rs.getString("phonenumber"));

        client.setEmail(rs.getString("email"));
        client.setAdress(rs.getString("adresse"));
        client.setClientState(rs.getString("clientstate"));
        client.setClientType(rs.getString("clienttype"));
        client.setDocumentsPath(rs.getString("documentspath"));
        client.setFullName(rs.getString("fullname"));
        client.setProfilePicturePath(rs.getString("profile_pic_path"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return client;
  }

  @Override
  public List<Transaction> getTransactions(int id) {
    String query = "SELECT id_transaction,"
            + " somme, date_transaction,"
            + " type_transaction,"
            + " fromaccount,"
            + " toaccount, state,"
            + " clientfrom, clientto, "
            + " description, convertedamount,"
            + " clienttoname, accounttorib"
            + " FROM public.transactions where"
            + " (clientfrom=? or clientto=?)"
            + " and type_transaction<>'AccountDeletion' "
            + " order by date_transaction desc;";

    List<Transaction> l = new LinkedList<>();
    AccountDao comptedao = new AccountDaoImpl();
    ClientDao clientdao = new ClientDaoImpl();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, id);

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
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
      }
    }
    return l;
  }

  @Override
  public String setProfilePic(int id, String Path) {
    String query = "UPDATE public.client "
            + "set profile_pic_path = '" + Path
            + "' WHERE id = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public String UpdateUsername(int id, String username) {
    String query = "UPDATE public.client "
            + "set username = '" + username
            + "' WHERE id = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public String UpdateEmail(int id, String email) {
    String query = "UPDATE public.client "
            + "set email = '" + email
            + "' WHERE id = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public String UpdatePhoneNumber(int id, String phonenumber) {
    String query = "UPDATE public.client "
            + "set phonenumber = ?"
            + " WHERE id = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setString(1, phonenumber);
      preparedStatement.setInt(2, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public String UpdateAddress(int id, String address) {
    String query = "UPDATE public.client "
            + "set adresse = '" + address
            + "' WHERE id = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public String ChangePassword(int id, String password) {
    String query = "UPDATE public.client "
            + "set passwordclient = '" + password
            + "' WHERE id = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public String UpdatePassword(String Email, String password) {
    String query = "UPDATE public.client "
            + "set passwordclient = ?" 
            + " WHERE email = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, password);
      preparedStatement.setString(2, Email);

      
      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

}
