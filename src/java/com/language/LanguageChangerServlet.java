/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.language;

import java.io.IOException;
import java.util.Locale;

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
@WebServlet(name = "LanguageChangerServlet", urlPatterns = {"/LanguageChangerServlet"})
public class LanguageChangerServlet extends HttpServlet {

  // <editor-fold defaultstate="" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

    HttpSession session = request.getSession();
    String language = request.getParameter("language_list");
    session.setAttribute("langue", language);
    String[] url = request.getHeader("referer").split("/");

    if (request.getContextPath().endsWith(url[url.length - 1])) {
      response.sendRedirect("Home");
    } else {
      response.sendRedirect(url[url.length - 1]);
    }

  }

  /**
   * Returns a short description of the
   * servlet.
   *
   * @return a String containing servlet
   * description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
