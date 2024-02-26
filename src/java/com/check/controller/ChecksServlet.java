/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.check.controller;

import com.Client.bean.Client;
import com.check.bean.Check;
import com.check.dao.CheckDao;
import com.check.dao.CheckDaoImpl;
import com.login.util.LoginUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samir
 */
@WebServlet(name = "ChecksServlet", urlPatterns = {"/Checks"})
public class ChecksServlet extends HttpServlet {

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
    String username = (String) request.getSession().getAttribute("username");
    request.getSession().removeAttribute("check_error");
    request.getSession().removeAttribute("check_creation_error");
    LoginUtil.LoadClientByUserName(request, response);
    CheckDao checkdao = new CheckDaoImpl();
    Client client = (Client) request.getSession().getAttribute("client");
    if (client == null) {
      LoginUtil.LoadClientByUserName(request, response);
      client = (Client) request.getSession().getAttribute("client");
    }

    if (username != null) {
      List<Check> checks = checkdao.getChecks(client.getId());
      request.getSession().setAttribute("checks", checks);
      request.getRequestDispatcher("Views/Client/checks.jsp").forward(request, response);
    } else {
      response.sendRedirect("Logout");
    }
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

  }

}
