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
import com.cards.dao.CardDao;
import com.cards.dao.CardDaoImpl;
import com.iban.bean.Iban;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "TransactionByCardServlet", urlPatterns = {"/TransactionByCard"})
public class TransactionByCardServlet extends HttpServlet {

  /**
   * Handles the HTTP <code>POST</code>
   * method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a
   * servlet-specific error occurs
   * @throws IOException if an I/O error
   * occurs
   */
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

    //Create a data access object for transaction and account 
    TransactionDao transactiondao = new TransactionDaoImpl();
    AccountDao comptedao = new AccountDaoImpl();
    ClientDao clientdao = new ClientDaoImpl();
    CardDao carddao = new CardDaoImpl();

    //Getting submited parameter
    String AccountTo = request.getParameter("AccountTo");
    String AccountFrom = request.getParameter("AccountFrom");
    BigDecimal Amount = new BigDecimal(request.getParameter("transferAmount"));
    String description = request.getParameter("description_area");

    String RIBorIBAN = request.getParameter("RIBorIBAN");

    String FullName = request.getParameter("FullName");

    String CardFrom = request.getParameter("CardFrom");

    //create account from object
    Account compteFrom = new Account();
    //set id
    compteFrom.setRIB(AccountFrom);
    //getting the account from DB
    compteFrom = comptedao.getAccountById(Integer.parseInt(AccountFrom));
    //create account to object
    Account compteTo;
    Transaction transaction = new Transaction();

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
            //internal transfer
            compteTo = comptedao.getAccountByIBAN(AccountTo);

            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Cards"));

            } else if (compteFrom.getId() == compteTo.getId()) {
              session.setAttribute("transfer_error", "You can't transfer to the same account");
              setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
              response.sendRedirect(response.encodeURL("Cards"));

            } else {
              //withdraw
              comptedao.Withdraw(compteFrom.getId(), Amount);
              //convert if not same currency it stays the same amout if same cuurency
              BigDecimal ConvertedAmount = TransactionUtil.convert(compteFrom.getCurrency(), compteTo.getCurrency(), Amount);
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
              transaction.setType("Card(Internal)");
              transaction.setState("Completed");
              transaction.setDescription(description);
              //Add transaction to DB
              int id = transactiondao.AddTransaction(transaction);
              carddao.AddCardTransaction(id, CardFrom);
              try {

                session.removeAttribute("transfer_error");
                setCookie(response, "SUCCES", "SUCCES", 15);
                //commit connection
                SingletonConnection.getConnection().commit();
                //go back to accounts page
                response.sendRedirect(response.encodeURL("Cards"));

              } catch (SQLException ex) {
                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }

          } else {

            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Cards"));

            } else {
              //external transfer B2B
              compteTo = new Account();
              compteTo.setRIB(AccountTo);
              comptedao.Withdraw(compteFrom.getId(), Amount);
              //
              Client client = new Client();
              client.setFullName(FullName);

              transaction.setAccountTo(compteTo);
              transaction.setClientTo(client);
              transaction.setClienttToName(FullName);
              transaction.setAccountFrom(compteFrom);
              transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
              transaction.setAmount(Amount);
              transaction.setConvertedAmount(Amount);
              transaction.setType("Card(external)");
              transaction.setState("Pending");
              transaction.setDescription(description);
              transaction.setAccountToRIB(compteTo.getRIB());

              //Add transaction to DB
              int id = transactiondao.AddTransaction(transaction);
              carddao.AddCardTransaction(id, CardFrom);
              try {

                session.removeAttribute("transfer_error");
                setCookie(response, "SUCCES", "SUCCES", 15);
                //commit connection
                SingletonConnection.getConnection().commit();
                //go back to accounts page
                response.sendRedirect(response.encodeURL("Cards"));

              } catch (SQLException ex) {
                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
          }
        } else {
          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Cards"));

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
            transaction.setType("Card(International)");
            transaction.setState("Pending");
            transaction.setDescription(description);
            transaction.setAccountToRIB(AccountTo);

            //Add transaction to DB
            int id = transactiondao.AddTransaction(transaction);
            carddao.AddCardTransaction(id, CardFrom);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Cards"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }

      } else if (RIBorIBAN.equals("RIB")) {
        RIB rib = RIB.toRIB(AccountTo);
        String bankCode = rib.getBankCode();
        compteTo = comptedao.getAccount(AccountTo);
        //test if same bank
        if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Cards"));

          } else if (compteFrom.getId() == (compteTo.getId())) {
            session.setAttribute("transfer_error", "You can't transfer to the same account");
            setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
            response.sendRedirect(response.encodeURL("Cards"));

          } else {
            //iternal transfer

            comptedao.Withdraw(compteFrom.getId(), Amount);
            BigDecimal ConvertedAmount = TransactionUtil.convert(compteFrom.getCurrency(), compteTo.getCurrency(), Amount);

            comptedao.Deposit(compteTo.getId(), ConvertedAmount);
            transaction.setAccountFrom(compteFrom);
            transaction.setAccountTo(compteTo);
            transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
            transaction.setClientTo(clientdao.getClientByAccountId(compteTo.getId()));
            transaction.setClienttToName(FullName);
            transaction.setAmount(Amount);
            transaction.setConvertedAmount(ConvertedAmount);
            transaction.setType("Card(Internal)");
            transaction.setState("Completed");
            transaction.setDescription(description);

            //Add transaction to DB
            int id = transactiondao.AddTransaction(transaction);
            carddao.AddCardTransaction(id, CardFrom);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Cards"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        } else {

          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Cards"));

          } else {
            //B2B transfer
            compteTo = new Account();
            compteTo.setRIB(AccountTo);
            comptedao.Withdraw(compteFrom.getId(), Amount);

            Client client = new Client();
            client.setFullName(FullName);

            transaction.setAccountTo(compteTo);
            transaction.setClientTo(client);
            transaction.setClienttToName(FullName);

            transaction.setAccountFrom(compteFrom);
            transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
            transaction.setAmount(Amount);
            transaction.setConvertedAmount(Amount);
            transaction.setType("Card(external)");
            transaction.setState("Pending");
            transaction.setDescription(description);
            transaction.setAccountToRIB(compteTo.getRIB());

            //Add transaction to DB
            int id = transactiondao.AddTransaction(transaction);
            carddao.AddCardTransaction(id, CardFrom);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Cards"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
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
            //internal transfer
            compteTo = comptedao.getAccountByIBAN(AccountTo);

            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Cards"));

            } else if (compteFrom.getId() == compteTo.getId()) {
              session.setAttribute("transfer_error", "You can't transfer to the same account");
              setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
              response.sendRedirect(response.encodeURL("Cards"));

            } else {
              //withdraw
              comptedao.Withdraw(compteFrom.getId(), Amount);
              //convert if not same currency it stays the same amout if same cuurency
              BigDecimal ConvertedAmount = TransactionUtil.convert(compteFrom.getCurrency(), compteTo.getCurrency(), Amount);
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
              transaction.setType("Card(Internal)");
              transaction.setState("Completed");
              transaction.setDescription(description);
              //Add transaction to DB
              int id = transactiondao.AddTransaction(transaction);
              carddao.AddCardTransaction(id, CardFrom);
              try {

                session.removeAttribute("transfer_error");
                setCookie(response, "SUCCES", "SUCCES", 15);
                //commit connection
                SingletonConnection.getConnection().commit();
                //go back to accounts page
                response.sendRedirect(response.encodeURL("Cards"));

              } catch (SQLException ex) {

                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
          } else {
            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Cards"));

            } else {

              //external transfer B2B
              comptedao.Withdraw(compteFrom.getId(), Amount);
              //create client to with only the name provided
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
              transaction.setType("Card(external)");
              transaction.setState("Pending");
              transaction.setDescription(description);
              transaction.setAccountToRIB(compteTo.getIBAN());
              //Add transaction to DB
              int id = transactiondao.AddTransaction(transaction);
              carddao.AddCardTransaction(id, CardFrom);
              try {

                session.removeAttribute("transfer_error");
                setCookie(response, "SUCCES", "SUCCES", 15);
                //commit connection
                SingletonConnection.getConnection().commit();
                //go back to accounts page
                response.sendRedirect(response.encodeURL("Cards"));

              } catch (SQLException ex) {

                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
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

          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Cards"));

          } else if (compteFrom.getRIB().equals(compteTo.getRIB())) {
            session.setAttribute("transfer_error", "You can't transfer to the same account");
            setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
            response.sendRedirect(response.encodeURL("Cards"));

          } else {
            //iternal transfer
            comptedao.Withdraw(compteFrom.getId(), Amount);
            BigDecimal ConvertedAmount = TransactionUtil.convert(compteFrom.getCurrency(), compteTo.getCurrency(), Amount);

            comptedao.Deposit(compteTo.getId(), ConvertedAmount);
            transaction.setAccountFrom(compteFrom);
            transaction.setAccountTo(compteTo);
            transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
            transaction.setClientTo(clientdao.getClientByAccountId(compteTo.getId()));
            transaction.setClienttToName(FullName);
            transaction.setAmount(Amount);
            transaction.setConvertedAmount(ConvertedAmount);
            transaction.setType("Card(Internal)");
            transaction.setState("Completed");
            transaction.setDescription(description);

            //Add transaction to DB
            int id = transactiondao.AddTransaction(transaction);
            carddao.AddCardTransaction(id, CardFrom);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Cards"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        } else {
          //B2B transfer
          compteTo = new Account();
          compteTo.setRIB(AccountTo);
          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Cards"));

          } else {
            comptedao.Withdraw(compteFrom.getId(), Amount);

            //
            Client client = new Client();
            client.setFullName(FullName);

            transaction.setAccountTo(compteTo);
            transaction.setClientTo(client);
            transaction.setClienttToName(FullName);

            transaction.setAccountFrom(compteFrom);
            transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
            transaction.setAmount(Amount);
            transaction.setConvertedAmount(Amount);
            transaction.setType("Card(external)");
            transaction.setState("Pending");
            transaction.setDescription(description);
            transaction.setAccountToRIB(compteTo.getRIB());

            //Add transaction to DB
            int id = transactiondao.AddTransaction(transaction);
            carddao.AddCardTransaction(id, CardFrom);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Cards"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
      }
    }
  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

}
