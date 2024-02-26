/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.confirm_email.controller;

import com.email_sender.bean.Email;
import com.email_sender.util.EmailUtility;
import com.register.bean.RegisterBean;
import com.register.util.RegisterUtil;
import connexion.util.SingletonConnection;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
import org.apache.commons.io.FileExistsException;

/**
 *
 * @author Samir
 */
@WebServlet(name = "Confirm_Email", urlPatterns = {"/Confirm Email"})
public class Confirm_Email extends HttpServlet {

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
    String code = (String) session.getAttribute("code");
    session.setAttribute("user", user);
    ResourceBundle bundle = ResourceBundle.getBundle("i18n.Bundle", new Locale((String) session.getAttribute("langue")));

    Email email = new Email();
    email.setRecipient(user.getEmail());

    MessageFormat fsubject = new MessageFormat(bundle.getString("email.subject"));
    Object[] var = {code};
    String strSubject = fsubject.format(var);

    String subject = strSubject;
    email.setSubject(subject);

    MessageFormat mf = new MessageFormat(bundle.getString("email"));
    Object[] vars = {user.getFullName(), code};
    String str = mf.format(vars);

    String content = str;
    email.setContent(content);
    Email oldemail = (Email) session.getAttribute("email");
    session.setAttribute("email", oldemail);

    rd = request.getRequestDispatcher("Views/email_confirmation.jsp");
    session.removeAttribute("codeError");

    rd.forward(request, response);
    session.setAttribute("email", email);

    try {
      EmailUtility.sendEmail(email);

    } catch (MessagingException ex) {
      Logger.getLogger(Confirm_Email.class.getName()).log(Level.SEVERE, null, ex);
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
    RequestDispatcher rd;

    HttpSession session = request.getSession();
    RegisterBean user = (RegisterBean) session.getAttribute("user");
    String uploadPath = (String) session.getAttribute("uploadPath");
    String code = (String) session.getAttribute("code");

    String confirmation_code = request.getParameter("1") + request.getParameter("2") + request.getParameter("3")
            + request.getParameter("4") + request.getParameter("5") + request.getParameter("6");
    if (code.equals(confirmation_code)) {
      try {
        session.removeAttribute("codeError");
        //commit
        SingletonConnection.getConnection().commit();

        // write files to the upload folder
        for (int i = 0; i < user.getFiles().size(); i++) {
          File file = new File(uploadPath, user.getFiles().get(i).getName());
          try {
            user.getFiles().get(i).write(file);
          } catch (FileExistsException ex) {
            Logger.getLogger(RegisterUtil.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
            Logger.getLogger(RegisterUtil.class.getName()).log(Level.SEVERE, null, ex);
          }
        }

      } catch (SQLException ex) {
        request.setAttribute("register_error", "An error has occured please try again");
        rd = request.getRequestDispatcher("Register");
        rd.forward(request, response);
        Logger.getLogger(Confirm_Email.class.getName()).log(Level.SEVERE, null, ex);
      }
      response.sendRedirect("EmailConfirmed");
    } else {
      session.setAttribute("codeError", "Code not correct");
      response.sendRedirect("Confirm Email");
    }

  }

}
