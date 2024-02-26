/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.settings.controller;

import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.login.util.LoginUtil;
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

/**
 *
 * @author Samir
 */
@WebServlet(name = "EditProfileServlet", urlPatterns = {"/EditProfile"})
public class EditProfileServlet extends HttpServlet {

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
    try {
      String username = request.getParameter("username");
      String email = request.getParameter("email");
      String PhoneNumber = request.getParameter("phonenumber");
      String Address = request.getParameter("address");

      Client client = (Client) request.getSession().getAttribute("client");
      if (client == null) {
        LoginUtil.LoadClientByUserName(request, response);
        client = (Client) request.getSession().getAttribute("client");
      }
      
      

      ClientDao clientdao = new ClientDaoImpl();

      if (!username.isEmpty()) {
        clientdao.UpdateUsername(client.getId(), username);
        request.getSession().setAttribute("username", username);
      }
      if (!email.isEmpty()) {
        clientdao.UpdateEmail(client.getId(), email);
      }
      if (!PhoneNumber.isEmpty()) {
        clientdao.UpdatePhoneNumber(client.getId(), PhoneNumber);
      }
      if (!Address.isEmpty()) {
        clientdao.UpdateAddress(client.getId(), Address);
      }
      SingletonConnection.getConnection().commit();

    } catch (SQLException ex) {
      Logger.getLogger(EditProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
    response.sendRedirect("Settings");
  }

}
