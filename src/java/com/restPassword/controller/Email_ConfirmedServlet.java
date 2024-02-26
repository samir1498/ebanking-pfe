/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.restPassword.controller;

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
@WebServlet(name = "Email_ConfirmedServlet", urlPatterns = {"/RestPassword_Email_Confirmed"})
public class Email_ConfirmedServlet extends HttpServlet {

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
    request.getRequestDispatcher("Views/ResetPassWord.jsp").forward(request, response);

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
    RequestDispatcher rd;

    HttpSession session = request.getSession();

    String code = (String) session.getAttribute("code");

    String confirmation_code = request.getParameter("1") + request.getParameter("2") + request.getParameter("3")
            + request.getParameter("4") + request.getParameter("5") + request.getParameter("6");
    if (code.equals(confirmation_code)) {

      session.removeAttribute("codeError");
      request.getRequestDispatcher("Views/ResetPassWord.jsp").forward(request, response);
    } else {
      session.setAttribute("codeError", "Code not correct");
      request.getRequestDispatcher("Views/email_confirmation_forgotPass.jsp").forward(request, response);
      session.removeAttribute("codeError");
    }
  }

}
