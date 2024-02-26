/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.settings.controller;

import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.login.util.LoginUtil;
import component.util.LanguageComp;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samir
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePassword"})
public class ChangePasswordServlet extends HttpServlet {

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
    String pass = request.getParameter("pass");
    String repass = request.getParameter("repass");

    HttpSession session = request.getSession();
    LoginUtil.LoadClientByUserName(request, response);
    Client client = (Client) request.getSession().getAttribute("client");

    LanguageComp.Language(request, session);
    String username = (String) session.getAttribute("username");
    //
    if (client.getId() == 0) {
      LoginUtil.LoadClientByUserName(request, response);
    }
    ClientDao clientdao = new ClientDaoImpl();
    if (pass.equals(repass)) {
      try {
        clientdao.ChangePassword(client.getId(), repass);
        SingletonConnection.getConnection().commit();
      } catch (SQLException ex) {
        Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    if (username != null) {
      request.getRequestDispatcher("Settings").forward(request, response);
    }
  }

}
