/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Account.controller;

import com.login.util.LoginUtil;
import component.util.LanguageComp;
import java.io.IOException;
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
@WebServlet(name = "AccountServlet", urlPatterns = {"/Account"})
public class AccountServlet extends HttpServlet {

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
    HttpSession session = request.getSession();
    LanguageComp.Language(request, session);
    LoginUtil.LoadClientByUserName(request, response);
    String username = (String) session.getAttribute("username");
    if (username != null) {
      request.getRequestDispatcher("Views/Client/account.jsp").forward(request, response);
    } else {
      response.sendRedirect("Logout");
    }

  }

}
