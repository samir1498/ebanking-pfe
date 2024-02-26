/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.factures.controller;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.factures.bean.Bill;
import com.factures.dao.BillDao;
import com.factures.dao.BillDaoImpl;
import com.iban.bean.Iban;
import com.rib.bean.RIB;
import com.transaction.bean.Transaction;
import com.transaction.controller.TransactionServlet;
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
@WebServlet(name = "PayBillServlet", urlPatterns = {"/PayBill"})
public class PayBillServlet extends HttpServlet {

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

    String AccountFrom = request.getParameter("PayFrom");
    String BillerAccount = request.getParameter("BillerAccount");
    String FullName = request.getParameter("BillerName");
    BigDecimal Amount = new BigDecimal(request.getParameter("Amount"));
    String description = request.getParameter("description");
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

    BillDao billdao = new BillDaoImpl();

    //Getting submited parameter
    String RIBorIBAN = request.getParameter("RIBorIBAN");

    //create account from object
    Account compteFrom = new Account();
    //set id
    compteFrom.setId(Integer.parseInt(AccountFrom));
    //getting the account from DB
    compteFrom = comptedao.getAccountById(compteFrom.getId());
    //create account to object
    Account compteTo;
    Transaction transaction = new Transaction();

    ResourceBundle bundle = ResourceBundle.getBundle("configuration.application");

    if (bundle.getString("International").equals("true")) {
      //test if iban
      if (RIBorIBAN.equals("IBAN")) {
        //test if same country and same bank code
        Iban iban = Iban.toIBAN(BillerAccount);
        String bankCode = iban.getBankCode();
        //test country code
        if (iban.getCountryCode().equals(country)) {
          if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
            //internal transfer
            compteTo = comptedao.getAccountByIBAN(BillerAccount);

            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Bills"));

            } else if (compteFrom.getId() == compteTo.getId()) {
              session.setAttribute("transfer_error", "You can't transfer to the same account");
              setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
              response.sendRedirect(response.encodeURL("Bills"));

            } else {

              //Add bill
              Bill bill = new Bill();
              bill.setAmount(Amount);
              bill.setBillerAccountNumber(BillerAccount);
              bill.setBillerName(FullName);
              bill.setPayFrom(compteFrom.getRIB());
              bill.setDescription(description);
bill.setCurrency(compteFrom.getCurrency().getSymbol());
              billdao.AddBill(bill);
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
              transaction.setType("Bill(Internal)");
              transaction.setState("Completed");
              transaction.setDescription(description);
              //Add transaction to DB
              transactiondao.AddTransaction(transaction);
              try {

                session.removeAttribute("transfer_error");
                setCookie(response, "SUCCES", "SUCCES", 15);
                //commit connection
                SingletonConnection.getConnection().commit();
                //go back to accounts page
                response.sendRedirect(response.encodeURL("Bills"));

              } catch (SQLException ex) {
                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }

          } else {

            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Bills"));

            } else {
              //Add bill
              Bill bill = new Bill();
              bill.setAmount(Amount);
              bill.setBillerAccountNumber(BillerAccount);
              bill.setBillerName(FullName);
              bill.setPayFrom(compteFrom.getRIB());
              bill.setDescription(description);
bill.setCurrency(compteFrom.getCurrency().getSymbol());
              billdao.AddBill(bill);
              //external transfer B2B
              compteTo = new Account();
              compteTo.setRIB(BillerAccount);
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
              transaction.setType("Bill(external)");
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
                response.sendRedirect(response.encodeURL("Bills"));

              } catch (SQLException ex) {
                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
          }
        } else {
          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Bills"));

          } else {
            //Add bill
            Bill bill = new Bill();
            bill.setAmount(Amount);
            bill.setBillerAccountNumber(BillerAccount);
            bill.setBillerName(FullName);
            bill.setPayFrom(compteFrom.getRIB());
            bill.setDescription(description);
bill.setCurrency(compteFrom.getCurrency().getSymbol());
            billdao.AddBill(bill);
            //international transfer
            comptedao.Withdraw(compteFrom.getId(), Amount);

            //
            Client client = new Client();
            client.setFullName(FullName);
            compteTo = new Account();
            compteTo.setIBAN(BillerAccount);
            transaction.setAccountTo(compteTo);
            transaction.setClientTo(client);
            transaction.setClienttToName(FullName);

            transaction.setAccountFrom(compteFrom);
            transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
            transaction.setAmount(Amount);
            transaction.setConvertedAmount(Amount);
            transaction.setType("Bill(International)");
            transaction.setState("Pending");
            transaction.setDescription(description);
            transaction.setAccountToRIB(BillerAccount);

            //Add transaction to DB
            transactiondao.AddTransaction(transaction);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Bills"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }

      } else if (RIBorIBAN.equals("RIB")) {
        RIB rib = RIB.toRIB(BillerAccount);
        String bankCode = rib.getBankCode();
        compteTo = comptedao.getAccount(BillerAccount);
        //test if same bank
        if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Bills"));

          } else if (compteFrom.getId() == compteTo.getId()) {
            session.setAttribute("transfer_error", "You can't transfer to the same account");
            setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
            response.sendRedirect(response.encodeURL("Bills"));

          } else {
            //Add bill
            Bill bill = new Bill();
            bill.setAmount(Amount);
            bill.setBillerAccountNumber(BillerAccount);
            bill.setBillerName(FullName);
            bill.setPayFrom(compteFrom.getRIB());
            bill.setDescription(description);
bill.setCurrency(compteFrom.getCurrency().getSymbol());
            billdao.AddBill(bill);
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
            transaction.setType("Bill(Internal)");
            transaction.setState("Completed");
            transaction.setDescription(description);

            //Add transaction to DB
            transactiondao.AddTransaction(transaction);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Bills"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        } else {

          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Bills"));

          } else {
            //Add bill
            Bill bill = new Bill();
            bill.setAmount(Amount);
            bill.setBillerAccountNumber(BillerAccount);
            bill.setBillerName(FullName);
            bill.setPayFrom(compteFrom.getRIB());
            bill.setDescription(description);
bill.setCurrency(compteFrom.getCurrency().getSymbol());
            billdao.AddBill(bill);
            //B2B transfer
            compteTo = new Account();
            compteTo.setRIB(BillerAccount);
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
            transaction.setType("Bill(external)");
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
              response.sendRedirect(response.encodeURL("Bills"));

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
        Iban iban = Iban.toIBAN(BillerAccount);
        String bankCode = iban.getBankCode();
        //test country code
        if (iban.getCountryCode().equals(country)) {
          //test if account to is in the same bank
          if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
            //internal transfer
            compteTo = comptedao.getAccountByIBAN(BillerAccount);

            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Bills"));

            } else if (compteFrom.getId() == compteTo.getId()) {
              session.setAttribute("transfer_error", "You can't transfer to the same account");
              setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
              response.sendRedirect(response.encodeURL("Bills"));

            } else {
              //Add bill
              Bill bill = new Bill();
              bill.setAmount(Amount);
              bill.setBillerAccountNumber(BillerAccount);
              bill.setBillerName(FullName);
              bill.setPayFrom(compteFrom.getRIB());
              bill.setDescription(description);
bill.setCurrency(compteFrom.getCurrency().getSymbol());
              billdao.AddBill(bill);
              //withdraw 
              //internal transfer
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
              transaction.setType("Bill(Internal)");
              transaction.setState("Completed");
              transaction.setDescription(description);
              //Add transaction to DB
              transactiondao.AddTransaction(transaction);
              try {

                session.removeAttribute("transfer_error");
                setCookie(response, "SUCCES", "SUCCES", 15);
                //commit connection
                SingletonConnection.getConnection().commit();
                //go back to accounts page
                response.sendRedirect(response.encodeURL("Bills"));

              } catch (SQLException ex) {

                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
          } else {
            if (compteFrom.getBalance().compareTo(Amount) == -1) {
              session.setAttribute("transfer_error", "Balance is less then amount");
              setCookie(response, "transfer_error", "Balance is less then amount", 1);

              response.sendRedirect(response.encodeURL("Bills"));

            } else {
              //Add bill
              Bill bill = new Bill();
              bill.setAmount(Amount);
              bill.setBillerAccountNumber(BillerAccount);
              bill.setBillerName(FullName);
              bill.setPayFrom(compteFrom.getRIB());
              bill.setDescription(description);
bill.setCurrency(compteFrom.getCurrency().getSymbol());
              billdao.AddBill(bill);
              //external transfer B2B
              comptedao.Withdraw(compteFrom.getId(), Amount);
              //create client to with only the name provided
              Client client = new Client();
              client.setFullName(FullName);

              compteTo = new Account();
              compteTo.setIBAN(BillerAccount);
              transaction.setAccountTo(compteTo);
              transaction.setClientTo(client);
              transaction.setClienttToName(FullName);

              transaction.setAccountFrom(compteFrom);
              transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
              transaction.setAmount(Amount);
              transaction.setConvertedAmount(Amount);
              transaction.setType("Bill(external)");
              transaction.setState("Pending");
              transaction.setDescription(description);
              transaction.setAccountToRIB(compteTo.getIBAN());
              //Add transaction to DB
              transactiondao.AddTransaction(transaction);
              try {

                session.removeAttribute("transfer_error");
                setCookie(response, "SUCCES", "SUCCES", 15);
                //commit connection
                SingletonConnection.getConnection().commit();
                //go back to accounts page
                response.sendRedirect(response.encodeURL("Bills"));

              } catch (SQLException ex) {

                Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
          }
        }
        /////////////////////////////////////////////////////////////////////////////
        //////////////////////////////
      } else if (RIBorIBAN.equals("RIB")) {
        RIB rib = RIB.toRIB(BillerAccount);
        String bankCode = rib.getBankCode();
        //test if same bank
        if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
          compteTo = comptedao.getAccount(BillerAccount);

          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Bills"));

          } else if (compteFrom.getRIB().equals(compteTo.getRIB())) {
            session.setAttribute("transfer_error", "You can't transfer to the same account");
            setCookie(response, "transfer_error", "You can't transfer to the same account", 1);
            response.sendRedirect(response.encodeURL("Bills"));

          } else {
            //Add bill
            Bill bill = new Bill();
            bill.setAmount(Amount);
            bill.setBillerAccountNumber(BillerAccount);
            bill.setBillerName(FullName);
            bill.setPayFrom(compteFrom.getRIB());
            bill.setDescription(description);
            bill.setCurrency(compteFrom.getCurrency().getSymbol());

            billdao.AddBill(bill);
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
            transaction.setType("Bill(Internal)");
            transaction.setState("Completed");
            transaction.setDescription(description);

            //Add transaction to DB
            transactiondao.AddTransaction(transaction);
            try {

              session.removeAttribute("transfer_error");
              setCookie(response, "SUCCES", "SUCCES", 15);
              //commit connection
              SingletonConnection.getConnection().commit();
              //go back to accounts page
              response.sendRedirect(response.encodeURL("Bills"));

            } catch (SQLException ex) {

              Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        } else {
          //B2B transfer
          compteTo = new Account();
          compteTo.setRIB(BillerAccount);
          if (compteFrom.getBalance().compareTo(Amount) == -1) {
            session.setAttribute("transfer_error", "Balance is less then amount");
            setCookie(response, "transfer_error", "Balance is less then amount", 1);

            response.sendRedirect(response.encodeURL("Bills"));

          } else {
            //Add bill
            Bill bill = new Bill();
            bill.setAmount(Amount);
            bill.setBillerAccountNumber(BillerAccount);
            bill.setBillerName(FullName);
            bill.setPayFrom(compteFrom.getRIB());
            bill.setDescription(description);
            bill.setCurrency(compteFrom.getCurrency().getSymbol());
            billdao.AddBill(bill);
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
            transaction.setType("Bill(external)");
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
              response.sendRedirect(response.encodeURL("Bills"));

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
