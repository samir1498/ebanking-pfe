/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.transaction.controller;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.currency.bean.Currency;
import com.iban.bean.Iban;
import com.login.util.LoginUtil;
import com.rib.bean.RIB;
import com.transaction.bean.Transaction;
import com.transaction.dao.TransactionDao;
import com.transaction.dao.TransactionDaoImpl;
import com.transaction.util.TransactionUtil;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samir
 */
@WebServlet(name = "TransactionServlet", urlPatterns = {"/Transaction"})
public class TransactionServlet extends HttpServlet {

  Account compteTo;
  String AccountTo;
  Account compteFrom;
  Transaction transaction;
  BigDecimal Amount;
  String FullName;
  String description;
  //Create a data access object for transaction and account 
  TransactionDao transactiondao = new TransactionDaoImpl();
  AccountDao comptedao = new AccountDaoImpl();
  ClientDao clientdao = new ClientDaoImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    RequestDispatcher rd;
    HttpSession session = request.getSession();
    Client client = (Client) session.getAttribute("client");
    if (client == null) {
      LoginUtil.LoadClientByUserName(request, response);
      client = (Client) session.getAttribute("client");
      try {
        List<Transaction> l = clientdao.getTransactions(client.getId());
        if (!l.isEmpty()) {
          Collections.sort(l, (Transaction o2, Transaction o1) -> o1.getId() - o2.getId());
          session.setAttribute("client_transactions", l);
        }
      } catch (NullPointerException ex) {
        Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);

      }

      rd = request.getRequestDispatcher("Views/Client/transactions.jsp");
      rd.forward(request, response);
    } else {
      client = (Client) session.getAttribute("client");
      List<Transaction> l = clientdao.getTransactions(client.getId());
      session.setAttribute("client_transactions", l);
      rd = request.getRequestDispatcher("Views/Client/transactions.jsp");
      rd.forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    //get session from request
    HttpSession session = request.getSession();
    //remove transfer error of the previous request
    session.removeAttribute("transfer_error");
    //
    String bank = (String) session.getAttribute("bank");
    String country = (String) session.getAttribute("country");

    //Getting submited parameter
    AccountTo = request.getParameter("AccountTo");
    String AccountFrom = request.getParameter("AccountFrom");
    Amount = new BigDecimal(request.getParameter("transferAmount"));
    description = request.getParameter("description_area");

    String RIBorIBAN = request.getParameter("RIBorIBAN");

    FullName = request.getParameter("FullName");

    //create account from object
    compteFrom = new Account();
    //set id
    compteFrom.setRIB(AccountFrom);
    //getting the account from DB
    compteFrom = comptedao.getAccount(AccountFrom);

    transaction = new Transaction();

    ResourceBundle bundle = ResourceBundle.getBundle("configuration.application");
    if (bundle.getString("International").equals("true")) {

      //test if iban
      if (RIBorIBAN.equals("IBAN")) {
        //test if same country and same bank code
        Iban iban = Iban.toIBAN(AccountTo);
        String bankCode = iban.getBankCode();

        //test country code
        if (iban.getCountryCode().equals(country)) {
          if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
            compteTo = comptedao.getAccountByIBAN(AccountTo);
            //internal transfer
            InternalTransfer(request, response, compteTo);

          } else {
            B2BTransfer(request, response);
          }
        } else {
          InternationalTransfer(request, response);
        }

      } else if (RIBorIBAN.equals("RIB")) {
        RIB rib = RIB.toRIB(AccountTo);
        String bankCode = rib.getBankCode();

        //test if same bank
        if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
          compteTo = comptedao.getAccount(AccountTo);
          //internal transfer
          InternalTransfer(request, response, compteTo);
        } else {
          //B2B transfer
          B2BTransfer(request, response);
        }
      }

    } else if (bundle.getString("International").equals("false")) {
      //test if iban
      if (RIBorIBAN.equals("IBAN")) {
        //test if same country and same bank code
        Iban iban = Iban.toIBAN(AccountTo);
        String bankCode = iban.getBankCode();
        //test country code
        if (iban.getCountryCode().equals(country)) {
          //test if account to is in the same bank
          if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
            compteTo = comptedao.getAccountByIBAN(AccountTo);
            //internal transfer
            InternalTransfer(request, response, compteTo);

          } else {
            B2BTransfer(request, response);
          }
        }
        /////////////////////////////////////////////////////////////////////////////
        //////////////////////////////
      } else if (RIBorIBAN.equals("RIB")) {
        RIB rib = RIB.toRIB(AccountTo);
        String bankCode = rib.getBankCode();
        //test if same bank
        if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
          compteTo = comptedao.getAccount(AccountTo);
          //internal transfer
          InternalTransfer(request, response, compteTo);

        } else {
          //B2B transfer
          B2BTransfer(request, response);

        }
      }
    }

  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  public void InternalTransfer(HttpServletRequest request, HttpServletResponse response, Account compteTo) throws IOException {

    HttpSession session = request.getSession();
    ResourceBundle bundle = ResourceBundle.getBundle("configuration.application");

    if (compteFrom.getBalance().compareTo(Amount) == -1) {
      session.setAttribute("transfer_error", "Balance is less then amount");
      setCookie(response, "transfer_error", "Balance is less then amount", 1);

      response.sendRedirect(response.encodeURL("Account"));

    } else if (bundle.getString("Echange_Devise").equals("false") && !compteFrom.getCurrency().equals(compteTo.getCurrency())) {
      session.setAttribute("transfer_error", "Currency Exchange Not supported");
      setCookie(response, "transfer_error", "Currency Exchange Not supported", 1);

      response.sendRedirect(response.encodeURL("Account"));

    } else if (compteFrom.getId() == compteTo.getId()) {
      session.setAttribute("transfer_error", "You can't transfer to the same account");
      setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
      response.sendRedirect(response.encodeURL("Account"));

    } else {
      Currency BankCurrencySymbol = comptedao.getCurrency((String) session.getAttribute("localcurrency"));
      //withdraw
      comptedao.Withdraw(compteFrom.getId(), Amount);
      //convert if not same currency it stays the same amout if same cuurency
      BigDecimal ConvertedAmount = TransactionUtil.convert(compteFrom.getCurrency(), compteTo.getCurrency(), Amount);
      BigDecimal ConvertedAmountLocalCurrency = TransactionUtil.convert(compteFrom.getCurrency(), BankCurrencySymbol, Amount);
      //Deposit
      comptedao.Deposit(compteTo.getId(), ConvertedAmount);
      //setting transaction attributes
      transaction.setAccountFrom(compteFrom);
      transaction.setAccountTo(compteTo);
      transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
      transaction.setClientTo(clientdao.getClientByAccountId(compteTo.getId()));
      transaction.setClienttToName(FullName);
      transaction.setAmount(Amount);
      transaction.setConvertedAmount(ConvertedAmount);
      transaction.setType("Transfer(Internal)");
      transaction.setState("Completed");
      transaction.setDescription(description);
      transaction.setConvertedAmoutLocalCurrency(ConvertedAmountLocalCurrency);
      //Add transaction to DB
      transactiondao.AddTransaction(transaction);
      try {

        session.removeAttribute("transfer_error");
        setCookie(response, "SUCCES", "SUCCES", 15);
        //commit connection
        SingletonConnection.getConnection().commit();
        //go back to accounts page
        response.sendRedirect(response.encodeURL("Account"));

      } catch (SQLException ex) {

        Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }

  public void B2BTransfer(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession();

    if (compteFrom.getBalance().compareTo(Amount) == -1) {
      session.setAttribute("transfer_error", "Balance is less then amount");
      setCookie(response, "transfer_error", "Balance is less then amount", 1);

      response.sendRedirect(response.encodeURL("Account"));

    } else {
      //external transfer B2B
      comptedao.Withdraw(compteFrom.getId(), Amount);
      //create client to with only the name provided
      Client client = new Client();
      client.setFullName(FullName);

      compteTo = new Account();
      compteTo.setRIB(AccountTo);
      transaction.setAccountTo(compteTo);
      transaction.setClientTo(client);
      transaction.setClienttToName(FullName);

      transaction.setAccountFrom(compteFrom);
      transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
      transaction.setAmount(Amount);
      transaction.setConvertedAmount(Amount);
      transaction.setType("Transfer(external)");
      transaction.setState("Pending");
      transaction.setDescription(description);
      transaction.setAccountToRIB(compteTo.getRIB());
      //Add transaction to DB
      transactiondao.AddTransaction(transaction);
      try {

        session.removeAttribute("transfer_error");
        setCookie(response, "SUCCES", "SUCCES", 15);
        //commit connection
        SingletonConnection.getConnection().commit();
        //go back to accounts page
        response.sendRedirect(response.encodeURL("Account"));

      } catch (SQLException ex) {

        Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public void InternationalTransfer(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession();

    if (compteFrom.getBalance().compareTo(Amount) == -1) {
      session.setAttribute("transfer_error", "Balance is less then amount");
      setCookie(response, "transfer_error", "Balance is less then amount", 1);
      response.sendRedirect(response.encodeURL("Account"));
    } else {
      //international transfer
      comptedao.Withdraw(compteFrom.getId(), Amount);
      //
      Client client = new Client();
      client.setFullName(FullName);
      compteTo = new Account();
      compteTo.setIBAN(AccountTo);
      transaction.setAccountTo(compteTo);
      transaction.setClientTo(client);
      transaction.setClienttToName(FullName);

      transaction.setAccountFrom(compteFrom);
      transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
      transaction.setAmount(Amount);
      transaction.setConvertedAmount(Amount);
      transaction.setType("Transfer(International)");
      transaction.setState("Pending");
      transaction.setDescription(description);
      transaction.setAccountToRIB(AccountTo);

      //Add transaction to DB
      transactiondao.AddTransaction(transaction);
      try {

        session.removeAttribute("transfer_error");
        setCookie(response, "SUCCES", "SUCCES", 15);
        //commit connection
        SingletonConnection.getConnection().commit();
        //go back to accounts page
        response.sendRedirect(response.encodeURL("Account"));

      } catch (SQLException ex) {

        Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
