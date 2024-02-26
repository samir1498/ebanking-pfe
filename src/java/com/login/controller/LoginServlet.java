/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.login.controller;

import com.Client.bean.Client;
import com.login.bean.LoginBean;
import com.login.dao.LoginDao;
import com.login.dao.LoginDaoImpl;
import com.login.util.LoginUtil;
import component.util.LanguageComp;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "Login", urlPatterns = {"/Login"})

public class LoginServlet extends HttpServlet {

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

    RequestDispatcher rd;
    HttpSession session = request.getSession();
    /// check laguage comp wich languages are choosed
    LanguageComp.Language(request, session);

    if (session.getAttribute("login_error") != "") {
      session.removeAttribute("login_error");
    }
    rd = request.getRequestDispatcher("Views/login.jsp");
    rd.forward(request, response);
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
    request.setCharacterEncoding("UTF-8");

    RequestDispatcher rd;
    HttpSession session = request.getSession();

    String username = request.getParameter("username");
    String pass = request.getParameter("pass");
    LoginBean loginbean = new LoginBean();

    loginbean.setUsername(username);
    loginbean.setPassword(pass);

    LoginDao logindao = new LoginDaoImpl();
    String result = logindao.authenticateUser(loginbean);

    switch (result) {
      case "active":
        //set session attribute tohave access to client username
        session.setAttribute("username", username);
        /*rd = request.getRequestDispatcher("Account");
        rd.forward(request, response);*/
        LoginUtil.LoadClientByUserName(request, response);
        Client client= (Client) session.getAttribute("client");
        session.setAttribute("client", client);
        response.sendRedirect(response.encodeRedirectURL("Account"));

        break;
      case "new":
        rd = request.getRequestDispatcher("Views/login.jsp");
        session.setAttribute("login_error", "error.login.new");
        rd.forward(request, response);
        break;
      case "Blocked":
        rd = request.getRequestDispatcher("Views/login.jsp");
        session.setAttribute("login_error", "error.login.blocked");
        rd.forward(request, response);
        break;
      case "error.login":
        rd = request.getRequestDispatcher("Views/login.jsp");
        session.setAttribute("login_error", result);
        rd.forward(request, response);
        break;
      default:
        rd = request.getRequestDispatcher("Views/login.jsp");
        rd.forward(request, response);
        break;
    }

  }

}
