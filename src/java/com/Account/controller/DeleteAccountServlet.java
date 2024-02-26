/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Account.controller;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.login.util.LoginUtil;
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
@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccount"})
public class DeleteAccountServlet extends HttpServlet {

  // <editor-fold defaultstate="" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    System.out.println(1);
    String id = request.getParameter("account_to_delete");
    String MoveTo = request.getParameter("accounts_lists");
    Client client = (Client) request.getSession().getAttribute("client");

    if (client == null) {
      LoginUtil.LoadClientByUserName(request, response);
    }
    client = (Client) request.getSession().getAttribute("client");
    if (isNumeric(id) && isNumeric(MoveTo)) {
      try {
        AccountDao accountdao = new AccountDaoImpl();
        Transaction transaction = new Transaction();
        TransactionDao transactiondao = new TransactionDaoImpl();
        //transfer balance if greater then 0
        Account compteTo = new Account();
        compteTo.setId(Integer.parseInt(MoveTo));
        compteTo = accountdao.getAccountById(Integer.parseInt(MoveTo));
        Account compteFrom = new Account();
        compteFrom.setId(Integer.parseInt(id));
        compteFrom = accountdao.getAccountById(Integer.parseInt(id));

        BigDecimal Amount = compteFrom.getBalance();
        if (Amount.compareTo(BigDecimal.ZERO) == 0) {
          //delete account
          accountdao.DeleteAccount(Integer.parseInt(id));

          SingletonConnection.getConnection().commit();
          setCookie(response, "SUCCES", "SUCCES", 15);
          response.sendRedirect(response.encodeRedirectURL("Account"));
        } else {
          accountdao.Withdraw(compteFrom.getId(), Amount);
          BigDecimal ConvertedAmount = TransactionUtil.convert(compteFrom.getCurrency(), compteTo.getCurrency(), Amount);
          accountdao.Deposit(compteTo.getId(), ConvertedAmount);

          transaction.setAccountFrom(compteFrom);
          transaction.setAccountTo(compteTo);
          transaction.setAmount(Amount);
          transaction.setClientFrom(client);
          transaction.setClientTo(client);
          transaction.setType("AccountDeletion");
          transaction.setState("Completed");
          transaction.setConvertedAmount(ConvertedAmount);
          transaction.setDescription("AccountDelete");
          transactiondao.AddTransaction(transaction);

          accountdao.DeleteAccount(Integer.parseInt(id));

          SingletonConnection.getConnection().commit();

          setCookie(response, "SUCCES", "SUCCES", 15);

          response.sendRedirect(response.encodeRedirectURL("Account"));
        }

      } catch (SQLException ex) {
        Logger.getLogger(DeleteAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Returns a short description of the
   * servlet.
   *
   * @return a String containing servlet
   * description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

  public static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    try {
      Integer.parseInt(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

}
