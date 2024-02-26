/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.factures.controller;

import com.Account.bean.Account;
import com.Client.bean.Client;
import com.factures.bean.Bill;
import com.factures.dao.BillDao;
import com.factures.dao.BillDaoImpl;
import java.io.IOException;
import java.util.LinkedList;
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
@WebServlet(name="BillsServlet", urlPatterns={"/Bills"})
public class BillsServlet extends HttpServlet {
   
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
      BillDao billdao = new BillDaoImpl();
      List<Bill> l = new LinkedList();
      Client client = (Client) request.getSession().getAttribute("client");
      
      for(Account account : client.getAccounts()){
        l.addAll(billdao.getBills(account));
      }
      
    request.getSession().setAttribute("Bills", l);
      
     request.getRequestDispatcher("Views/Client/factures.jsp").forward(request, response);
    } 
}
