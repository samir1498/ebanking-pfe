/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cards.controller;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.cards.bean.Card;
import com.cards.dao.CardDao;
import com.cards.dao.CardDaoImpl;
import com.login.util.LoginUtil;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "CardsServlet", urlPatterns = {"/Cards"})
public class CardsServlet extends HttpServlet {

  private static final String SOURCES
          = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String RESULTS
          = "123456789123456789234567890123456789";

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    RequestDispatcher rd;
    LoginUtil.LoadClientByUserName(request, response);
    rd = request.getRequestDispatcher("Views/Client/cards.jsp");
    rd.forward(request, response);

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    Random rnd = new Random();
    String first11 = generateCardNumber(11);
    String bank = (String) request.getSession().getAttribute("bank");
    String PIN = request.getParameter("CPIN");
    String account = request.getParameter("id_account");
    Client client = (Client) request.getSession().getAttribute("client");
    int cvv = rnd.nextInt(900)+100;
    Card card = new Card();

    final StringBuilder buffer = new StringBuilder(bank.toUpperCase());

    for (int i = 0; i < bank.length(); i++) {

      buffer.setCharAt(i, RESULTS.charAt(SOURCES.indexOf(
              buffer.charAt(i))));
    }

    bank = buffer.toString();
    card.setCardNumber(bank.concat(first11));
    card.setCVV(cvv);
    card.setPIN(PIN);
    card.setIdClient(String.valueOf(client.getId()));
    card.setIdAccount(account);
    card.setHolderName(client.getFullName());
    card.setWithdrawLimit(new BigDecimal("7000"));

    CardDao carddao = new CardDaoImpl();
    String res = carddao.AddCard(card);
    if (res.equals("SUCCES")) {
      try {
        SingletonConnection.getConnection().commit();

        setCookie(response, "SUCCES", "SUCCES", 15);
        response.sendRedirect(response.encodeRedirectURL("Account"));
      } catch (SQLException ex) {
        Logger.getLogger(CardsServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  // function to generate a random string of length n
  private static String generateCardNumber(int n) {
    // chose a Character random from this String
    String AlphaNumericString = "0123456789";

    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(n);

    for (int i = 0; i < n; i++) {

      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index = (int) (AlphaNumericString.length() * Math.random());

      // add Character one by one in end of sb
      sb.append(AlphaNumericString.charAt(index));
    }

    return sb.toString();
  }
}
