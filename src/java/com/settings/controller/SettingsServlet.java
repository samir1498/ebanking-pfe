/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.settings.controller;

import com.Client.bean.Client;
import com.login.util.LoginUtil;
import component.util.LanguageComp;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samir
 */
@WebServlet(name = "SettingsServlet", urlPatterns = {"/Settings"})
public class SettingsServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    HttpSession session = request.getSession();
    LoginUtil.LoadClientByUserName(request, response);

    LanguageComp.Language(request, session);
    Client client = (Client) session.getAttribute("client");
    String username = (String) session.getAttribute("username");
    //
    if (client == null) {
      LoginUtil.LoadClientByUserName(request, response);

      if (username != null) {
        request.getRequestDispatcher("Views/Client/settings.jsp").forward(request, response);
      }
    } else {
      if (username != null) {
        request.getRequestDispatcher("Views/Client/settings.jsp").forward(request, response);
      }
    }

  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
