/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Account.controller;

import com.Account.bean.CurrentAccount;
import com.Account.bean.SavingsAccount;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.currency.bean.Currency;
import com.iban.bean.Iban;
import com.login.util.LoginUtil;
import com.rib.bean.RIB;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
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
@WebServlet(name = "RequestAccountServlet", urlPatterns = {"/RequestAccount"})
public class RequestAccountServlet extends HttpServlet {

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
    HttpSession session = request.getSession();
    Client client = (Client) session.getAttribute("client");
    AccountDao comptedao = new AccountDaoImpl();
    String bank = (String) session.getAttribute("bank");
    String branch = (String) session.getAttribute("branch");
    String country = (String) session.getAttribute("country");
    if (request.getParameter("AccountTypeRequest").equals("saving")) {
      SavingsAccount savings = new SavingsAccount();
      //set account attributes
      //active if create account feature is true
      savings.setAccountState("active");
      //new if create account feature is false and request account is true
      savings.setName(request.getParameter("accountnamerequest"));
      savings.setAccountType(request.getParameter("AccountTypeRequest"));
      savings.setBalance(new BigDecimal("0"));

      Currency currency = comptedao.getCurrency(request.getParameter("currencyrequest"));
      savings.setCurrency(currency);

      if (client != null) {
        savings.setOwnerID(client.getId());
      } else if (client == null) {
        LoginUtil.LoadClientByUserName(request, response);
        client = (Client) session.getAttribute("client");
        savings.setOwnerID(client.getId());
      }
      savings.setTransferLimit(new BigDecimal("2000"));

      savings.setSavingGoal(new BigDecimal(request.getParameter("saving_goal_request")));
      String AccountNumber = RIB.generateAccountNumber(11);
      String rib = RIB.composeRIB(bank, branch, AccountNumber);
      String iban = Iban.composeIBAN(country, bank);
      savings.setIBAN(iban);
      savings.setRIB(rib);
      int id = comptedao.AddAccount(savings);
      savings.setId(id);

      comptedao.AddSavingsAccount(savings);
      client.getAccounts().add(savings);

    } else if (request.getParameter("AccountTypeRequest").equals("checking")) {

      CurrentAccount current = new CurrentAccount();
      //set account attributes
      current.setAccountState("active");
      current.setName(request.getParameter("accountnamerequest"));
      current.setAccountType(request.getParameter("AccountTypeRequest"));
      current.setBalance(new BigDecimal("0"));

      Currency currency = comptedao.getCurrency(request.getParameter("currencyrequest"));
      current.setCurrency(currency);
      if (client != null) {
        current.setOwnerID(client.getId());
      } else if (client == null) {
        LoginUtil.LoadClientByUserName(request, response);
        client = (Client) session.getAttribute("client");
        current.setOwnerID(client.getId());
      }
      current.setTransferLimit(new BigDecimal("2000"));

      current.setBudget(new BigDecimal(request.getParameter("budgetamountrequest")));
      current.setPeriod(request.getParameter("budget_period_request"));

      String AccountNumber = RIB.generateAccountNumber(11);
      String rib = RIB.composeRIB(bank, branch, AccountNumber);
      current.setRIB(rib);
      String iban = Iban.composeIBAN(country, bank);
      current.setIBAN(iban);
      int id = comptedao.AddAccount(current);
      current.setId(id);

      comptedao.AddCurrentAccount(current);
      client.getAccounts().add(current);
    }

    try {
      SingletonConnection.getConnection().commit();
      setCookie(response, "SUCCES", "SUCCES", 15);

      response.sendRedirect(response.encodeRedirectURL("Account"));

    } catch (SQLException ex) {

      response.sendRedirect(response.encodeRedirectURL("Account"));

      Logger.getLogger(RequestAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
