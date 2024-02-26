/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.cards.controller;

import com.cards.dao.CardDao;
import com.cards.dao.CardDaoImpl;
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
@WebServlet(name="EditCardPINServlet", urlPatterns={"/EditCardPIN"})
public class EditCardPINServlet extends HttpServlet {
   
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    } 

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
      String CardNumber = request.getParameter("CardNumber");
      String PIN = request.getParameter("PIN");
      String CPIN = request.getParameter("CPIN");
      
      CardDao carddao = new CardDaoImpl();
      carddao.EditPIN(Integer.parseInt(CPIN), CardNumber);
      try {
        SingletonConnection.getConnection().commit();
        response.sendRedirect("Cards");
      } catch (SQLException ex) {
        Logger.getLogger(EditCardPINServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }


}
