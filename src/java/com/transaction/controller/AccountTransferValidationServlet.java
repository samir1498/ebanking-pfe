/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.transaction.controller;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.google.gson.Gson;
import com.iban.bean.Iban;
import com.rib.bean.RIB;
import com.transaction.util.RIBValidationMsgs;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.garvelink.iban.CountryCodes;

/**
 *
 * @author Samir
 */
@WebServlet(name = "AccountTransferValidationServlet", urlPatterns = {"/AccountTransferValidation"})
public class AccountTransferValidationServlet extends HttpServlet {

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
    response.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession();
    //Getting bank code
    String bank = (String) session.getAttribute("bank");
    String country = (String) session.getAttribute("country");
    AccountDao accountdao = new AccountDaoImpl();
    //response writer
    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    //messages object
    RIBValidationMsgs msg = new RIBValidationMsgs();
    //get data from form to validate
    String data = request.getParameter("AccountTo");
    ResourceBundle bundle = ResourceBundle.getBundle("configuration.application");
    //IBAN test if accounto begins with a valid country code
    if (CountryCodes.getLengthForCountryCode(data.substring(0, 2)) != -1) {
      
      //test if iban valid
      if (Iban.isValid(data)) {
        //test if same country and same bank code
        Iban iban = Iban.toIBAN(data);
        String bankCode = iban.getBankCode();
        msg.setRIBorIBAN("IBAN");
        //test country code
        if (iban.getCountryCode().equals(country)) {
          msg.setIsError(false);
          //test bank code
          if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
            Account account = accountdao.getAccountByIBAN(data);
            if (account == null) {
              msg.setIsError(true);
              msg.setENMessage("Account does not exists");
            }
          }
          //check if international transfer feature is Active
        }else if (bundle.getString("International").equals("true")) {
          msg.setIsError(false);

        } else if (bundle.getString("International").equals("false")) {
            msg.setIsError(true);
            msg.setENMessage("International not supported");
        }

      } else {
        msg.setIsError(true);
        msg.setENMessage("IBAN not valid");
        msg.setFrMessage("IBAN non valid");
        msg.setARMessage("IBAN غير صالح");
      }
    } else {

      if (RIB.isValide(data)) {
        
        msg.setRIBorIBAN("RIB");
        RIB rib = RIB.toRIB(data);
        msg.setIsError(false);
        String bankCode = rib.getBankCode();

        if (bank.toUpperCase().equals(bankCode.toUpperCase())) {
          Account account = accountdao.getAccount(data);
          if (account == null) {
            msg.setIsError(true);
            msg.setENMessage("Account does not exists");
          }
        }
      } else {
        msg.setENMessage("RIB not valid");
        msg.setFrMessage("RIB non valid");
        msg.setARMessage("RIB غير صالح");
        msg.setIsError(true);
      }
    }

    Gson gson = new Gson();
    String json = gson.toJson(msg);

    out.print(json);
    out.flush();
  }
}
