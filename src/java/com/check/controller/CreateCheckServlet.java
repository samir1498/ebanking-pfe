/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.check.controller;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.check.bean.Check;
import com.check.dao.CheckDao;
import com.check.dao.CheckDaoImpl;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Random;
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
@WebServlet(name = "CreateCheckServlet", urlPatterns = {"/CreateCheck"})
public class CreateCheckServlet extends HttpServlet {

  /**
   * Handles the HTTP <code>GET</code>
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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

  }

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
    request.getSession().removeAttribute("check_creation_error");
    String accountRIB = request.getParameter("account_check");
    String payee = request.getParameter("Fullname");
    String date = request.getParameter("checkdate");
    String amount = request.getParameter("amount");

    AccountDao accountdao = new AccountDaoImpl();
    Account account = accountdao.getAccountById(Integer.parseInt(accountRIB));

    CheckDao checkdao = new CheckDaoImpl();

    Check check = new Check();
    String checknum = Check.generateCheckNumber(7);
    check.setCheckNumber(checknum);
    check.setAccount(accountRIB);
    check.setPayee(payee);
    check.setDate(date);
    check.setAmount(new BigDecimal(amount));
    check.setCurrency(account.getCurrency().getCode());
    check.setState("Pending");
    checkdao.AddCheck(check);
    try {
      SingletonConnection.getConnection().commit();
      response.sendRedirect("Checks");
    } catch (SQLException ex) {
      request.getSession().setAttribute("check_creation_error", "An error occured please try again");
      setCookie(response, "SUCCES", "FAIL", 15);
      response.sendRedirect("Checks");
      Logger.getLogger(CreateCheckServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
