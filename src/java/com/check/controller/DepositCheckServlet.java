/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.check.controller;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.check.bean.Check;
import com.check.dao.CheckDao;
import com.check.dao.CheckDaoImpl;
import com.transaction.bean.Transaction;
import com.transaction.dao.TransactionDao;
import com.transaction.dao.TransactionDaoImpl;
import com.transaction.util.TransactionUtil;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samir
 */
@WebServlet(name = "DepositCheckServlet", urlPatterns = {"/DepositCheck"})
public class DepositCheckServlet extends HttpServlet {

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
    request.getSession().removeAttribute("check_error");
    //Create a data access object for transaction, account , client and check
    TransactionDao transactiondao = new TransactionDaoImpl();
    AccountDao comptedao = new AccountDaoImpl();

    CheckDao checkdao = new CheckDaoImpl();
    ClientDao clientdao = new ClientDaoImpl();

    //Create a transaction object
    Transaction transaction = new Transaction();
    String AccountTo = request.getParameter("accountto");
    String CheckNo = request.getParameter("checknumber");
    //get check
    Check check = checkdao.getCheck(Integer.parseInt(CheckNo));
    if (check.getCheckNumber() != null) {
      if (check.getState() != null && "Pending".equals(check.getState())) {

        //get account from with rib from check
        Account compteFrom = comptedao.getAccount(check.getAccount());
        //getAccount to from deposit form
        Account compteTo = comptedao.getAccountById(Integer.parseInt(AccountTo));

        //Amount
        BigDecimal Amount = check.getAmount();
        comptedao.Withdraw(compteFrom.getId(), Amount);

        BigDecimal ConvertedAmount = TransactionUtil.convert(compteFrom.getCurrency(), compteTo.getCurrency(), Amount);
        comptedao.Deposit(compteTo.getId(), ConvertedAmount);
        transaction.setAccountFrom(compteFrom);
        transaction.setAccountTo(compteTo);
        transaction.setClientFrom(clientdao.getClientByAccountId(compteFrom.getId()));
        transaction.setClientTo(clientdao.getClientByAccountId(compteTo.getId()));
        transaction.setAmount(Amount);
        transaction.setConvertedAmount(ConvertedAmount);
        transaction.setType("Check");
        transaction.setState("Completed");
        transaction.setDescription("Check Deposit "+check.getCheckNumber());
        checkdao.UpdateCheckState("Used", check.getCheckNumber());
        try {
          transactiondao.AddTransaction(transaction);
          SingletonConnection.getConnection().commit();
          request.getSession().removeAttribute("check_error");
          setCookie(response, "SUCCES", "SUCCES", 15);
          response.sendRedirect("Checks");
        } catch (SQLException ex) {
          Logger.getLogger(DepositCheckServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

      } else if ("Used".equals(check.getState())) {
        request.getSession().setAttribute("check_error", "Check Used");
        setCookie(response, "SUCCES", "FAIL", 15);
        response.sendRedirect("Checks");
      } else {
        request.getSession().setAttribute("check_error", "Invalid check");
        setCookie(response, "SUCCES", "FAIL", 15);
        response.sendRedirect("Checks");
      }
    } else {
      request.getSession().setAttribute("check_error", "Check Invalid");
      setCookie(response, "SUCCES", "FAIL", 15);
      response.sendRedirect("Checks");
    }
  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
