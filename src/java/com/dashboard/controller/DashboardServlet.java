/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dashboard.controller;

import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.dashboard.dao.DashboardDaoImpl;
import com.dashboard.dao.DashboardDao;
import com.login.util.LoginUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.HashMap;
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
@WebServlet(name = "DashboardServlet", urlPatterns = {"/Dashboard"})
public class DashboardServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    RequestDispatcher rd;
    HttpSession session = request.getSession();
    DashboardDao dashboarddao = new DashboardDaoImpl();
    Client client = (Client) session.getAttribute("client");
    AccountDao accountdao = new AccountDaoImpl();
    String BankCurrencySymbol = accountdao.getCurrency((String) session.getAttribute("localcurrency")).getSymbol();
    session.setAttribute("lc", BankCurrencySymbol);
    //
    if (client == null) {
      LoginUtil.LoadClientByUserName(request, response);
      
      String username = (String) session.getAttribute("username");
      if (username != null) {
        rd = request.getRequestDispatcher("Views/Client/dashboard.jsp");
        rd.forward(request, response);
      }
    } else {
//
      double per = dashboarddao.getTotalSavingsProgress(client.getId());
      session.setAttribute("data", per);
      HashMap map = new HashMap();

      client.getAccounts().forEach((n) -> {
        if (n.getAccountType().equals("saving")) {
          double account_per = dashboarddao.getSavingsProgress(n.getId());
          map.put(n.getName(), account_per);
        }
      });
      session.setAttribute("map", map);
//
//
      per = dashboarddao.getTotalBudgetProgress(client.getId());
      session.setAttribute("data2", per);
      //budget percentage left with account name
      HashMap checking_map = new HashMap();

      client.getAccounts().forEach((n) -> {
        if (n.getAccountType().equals("checking")) {
          double account_per = dashboarddao.getBudgetProgress(n.getId());
          checking_map.put(n.getName(), account_per);
        }
      });

      BigDecimal outcome = dashboarddao.getOutcome(client.getId());
      if (outcome == null) {
        outcome = BigDecimal.ZERO;
        session.setAttribute("outcome", outcome);
      } else {
        session.setAttribute("outcome", outcome);
      }

      BigDecimal income = dashboarddao.getIncome(client.getId());
      if (income == null) {
        income = BigDecimal.ZERO;
        session.setAttribute("income", income);
      } else {
        session.setAttribute("income", income);
      }

      BigDecimal total = income.add(outcome);
      BigDecimal income_per;

      if (total.compareTo(BigDecimal.ZERO) == 0) {
        income_per = BigDecimal.ZERO;
      } else {
        income_per = (income.divide(total, MathContext.DECIMAL64)).multiply(new BigDecimal("100"));
      }

      income_per = income_per.setScale(2, RoundingMode.FLOOR);

      session.setAttribute("income_per", income_per);
      session.setAttribute("checking_map", checking_map);

      rd = request.getRequestDispatcher("Views/Client/dashboard.jsp");
      rd.forward(request, response);
    }
  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
