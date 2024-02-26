/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.restPassword.controller;

import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
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
@WebServlet(name="UpdatePasswordServlet", urlPatterns={"/UpdatePassword"})
public class UpdatePasswordServlet extends HttpServlet {
   

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      String pass = request.getParameter("pass");
      ClientDao clientdao = new ClientDaoImpl();
      String Email = (String) request.getSession().getAttribute("emailAddress");
            
      clientdao.UpdatePassword(Email, pass);
      try {
        SingletonConnection.getConnection().commit();
        request.getRequestDispatcher("Login").forward(request, response);
      } catch (SQLException ex) {
        Logger.getLogger(UpdatePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }


}
