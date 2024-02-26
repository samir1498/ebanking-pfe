/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Account.controller;

import com.Account.bean.Account;
import com.Account.bean.CurrentAccount;
import com.Account.bean.SavingsAccount;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.currency.bean.Currency;
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
@WebServlet(name = "EditAccountServlet", urlPatterns = {"/EditAccount"})
public class EditAccountServlet extends HttpServlet {

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
    try {
      String id = request.getParameter("account_to_edit");
      String name = request.getParameter("accountnameedit");
      String type = request.getParameter("AccountTypeedit");

      AccountDao accountdao = new AccountDaoImpl();

      Currency currency = accountdao.getCurrency(request.getParameter("currencyedit"));

      Account account = accountdao.getAccountById(Integer.parseInt(id));

      if (isNumeric(id)) {
        //if the account type is saving
        if (account.getAccountType().equals("saving") && type.equals("saving")) {

          String savinggoal = request.getParameter("saving_goal_edit");
          SavingsAccount saving = new SavingsAccount();
          saving.setId(account.getId());
          saving.setAccountType(type);
          saving.setCurrency(currency);
          saving.setName(name);
          saving.setSavingGoal(new BigDecimal(savinggoal));

          accountdao.EditAccount(saving);
          // if type not changed
          accountdao.EditSavingsAccount(saving);
          //if type changed
        } else if (account.getAccountType().equals("saving") && type.equals("checking")) {

          String budgetPeriod = request.getParameter("budget_period");
          String budgetAmount = request.getParameter("budget_amount");

          CurrentAccount current = new CurrentAccount();

          current.setId(account.getId());
          current.setAccountType(type);
          current.setCurrency(currency);
          current.setName(name);
          current.setPeriod(budgetPeriod);
          current.setBudget(new BigDecimal(budgetAmount));

          accountdao.EditAccount(current);

          accountdao.DeleteSavingsAccount(account.getId());
          accountdao.AddCurrentAccount(current);
        } //if account is checking and type not changed
        else if (account.getAccountType().equals("checking") && type.equals("checking")) {

          String budgetPeriod = request.getParameter("budget_period");
          String budgetAmount = request.getParameter("budget_amount");
          //new current account object
          CurrentAccount current = new CurrentAccount();
          current.setId(account.getId());

          current.setAccountType(type);

          current.setCurrency(currency);
          current.setName(name);

          current.setPeriod(budgetPeriod);
          current.setBudget(new BigDecimal(budgetAmount));

          String res = accountdao.EditAccount(current);
          accountdao.EditCurrentAccount(current);
          //if type changes
        } else if (account.getAccountType().equals("checking") && type.equals("saving")) {

          String savinggoal = request.getParameter("saving_goal_edit");
          SavingsAccount saving = new SavingsAccount();

          saving.setId(account.getId());
          saving.setAccountType(type);
          saving.setCurrency(currency);
          saving.setName(name);
          saving.setSavingGoal(new BigDecimal(savinggoal));

          accountdao.EditAccount(saving);

          accountdao.DeleteCurrentAccount(account.getId());
          accountdao.AddSavingsAccount(saving);
        }

        SingletonConnection.getConnection().commit();
        setCookie(response, "SUCCES", "SUCCES", 15);
        response.sendRedirect("Account");
      }
    } catch (SQLException ex) {
      Logger.getLogger(EditAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
  }

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
  }// </editor-fold>

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
