/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.restPassword.controller;

import com.confirm_email.controller.Confirm_Email;
import com.email_sender.bean.Email;
import com.email_sender.util.EmailUtility;
import com.register.bean.RegisterBean;
import com.rib.bean.RIB;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
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
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/ResetPassword"})
public class ResetPasswordServlet extends HttpServlet {

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
    request.getRequestDispatcher("Views/ForgotPassword.jsp").forward(request, response);
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
    String emailAddress = request.getParameter("Email");

    RequestDispatcher rd;
    HttpSession session = request.getSession();
    //RegisterBean user = (RegisterBean) session.getAttribute("user");
    String code = RIB.generateAccountNumber(6);
    session.setAttribute("code", code);
    session.setAttribute("emailAddress", emailAddress);
    ResourceBundle bundle = ResourceBundle.getBundle("i18n.Bundle", new Locale((String) session.getAttribute("langue")));

    Email email = new Email();
    email.setRecipient(emailAddress);

    MessageFormat fsubject = new MessageFormat(bundle.getString("restpass.email.subject"));
    Object[] var = {code};
    String strSubject = fsubject.format(var);

    String subject = strSubject;
    email.setSubject(subject);

    MessageFormat mf = new MessageFormat(bundle.getString("restpass.email"));
    Object[] vars = {code};
    String str = mf.format(vars);

    String content = str;
    email.setContent(content);


    try {
      EmailUtility.sendEmail(email);

    } catch (MessagingException ex) {
      Logger.getLogger(Confirm_Email.class.getName()).log(Level.SEVERE, null, ex);
    }
    request.getRequestDispatcher("Views/email_confirmation_forgotPass.jsp").forward(request, response);
  }

}
