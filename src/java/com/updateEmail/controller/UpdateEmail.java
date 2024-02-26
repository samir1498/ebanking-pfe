/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.updateEmail.controller;

import com.email_sender.bean.Email;
import com.register.bean.RegisterBean;
import com.updateEmail.dao.UpdateEmailDao;
import com.updateEmail.dao.UpdateEmailDaoImpl;
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
@WebServlet(name = "UpdateEmail", urlPatterns = {"/UpdateEmail"})
public class UpdateEmail extends HttpServlet {

  // <editor-fold defaultstate="" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    RegisterBean user = (RegisterBean) session.getAttribute("user");
    session.setAttribute("user", user);
    Email oldemail =  (Email) session.getAttribute("email");
    session.setAttribute("email", oldemail);

    rd = request.getRequestDispatcher("/Views/updateEmail.jsp");
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
    RequestDispatcher rd;
    HttpSession session = request.getSession();
    RegisterBean user = (RegisterBean) session.getAttribute("user");
    Email oldemail = (Email) session.getAttribute("email");
    String email = request.getParameter("UpdatedEmail");
    oldemail.setRecipient(email);
    session.setAttribute("user", user);

    UpdateEmailDao updateemaildao = new UpdateEmailDaoImpl();
    updateemaildao.UpdateEmail(email, user.getId());

    rd = request.getRequestDispatcher("Confirm Email");
    rd.forward(request, response);
  }


}