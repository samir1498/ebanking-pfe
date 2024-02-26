/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stratup.listner;

/**
 *
 * @author Samir
 */
import component.util.LanguageComp;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebListener
public class ebankingContextListener implements ServletRequestListener {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public void requestInitialized(ServletRequestEvent sre) {
    HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    HttpSession session = request.getSession();
    String bank = "";
    String branch = "";
    String country = null;
    String currency = "";
    try {
      String query = "SELECT ba.code, bank, countrycode, localcurrency \n"
              + "              FROM public.bank ba join public.branch br\n"
              + "              on ba.code = br.bank;";
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        bank = rs.getString("bank");
        branch = rs.getString("code");
        country = rs.getString("countrycode");
        currency = rs.getString("localcurrency");
      }
      session.setAttribute("bank", bank);
      session.setAttribute("branch", branch);
      session.setAttribute("country", country);
      session.setAttribute("localcurrency", currency);
      /// check laguage comp wich languages are choosed
      LanguageComp.Language(request, session);

    } catch (IOException | SQLException ex) {
      Logger.getLogger(ebankingContextListener.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    ServletRequestListener.super.requestDestroyed(sre); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
  }

}
