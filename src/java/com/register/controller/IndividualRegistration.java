/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.register.controller;

import com.Account.bean.CurrentAccount;
import com.Account.bean.SavingsAccount;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.currency.bean.Currency;
import com.email_sender.util.EmailUtility;
import com.iban.bean.Iban;
import com.register.bean.IndividualRegistrationBean;
import com.register.bean.RegisterBean;
import com.register.dao.RegisterDao;
import com.register.dao.RegisterDaoImpl;
import com.register.util.RegisterUtil;
import com.rib.bean.RIB;
import component.util.LanguageComp;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Samir
 */
@WebServlet(name = "IndividualRegistration", urlPatterns = {"/IndividualRegistration"})
public class IndividualRegistration extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    RequestDispatcher rd;
    HttpSession session = request.getSession();
    //get langauge
    LanguageComp.Language(request, session);

    rd = request.getRequestDispatcher("Views/register.jsp");
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
    request.setCharacterEncoding("UTF-8");
    RequestDispatcher rd;
    HttpSession session = request.getSession();
    // create a user object
    RegisterBean user = new IndividualRegistrationBean();
    AccountDao comptedao = new AccountDaoImpl();
    String res;
    String bank = (String) session.getAttribute("bank");
    String branch = (String) session.getAttribute("branch");
    String country = (String) session.getAttribute("country");

    //(field, value) map to store the values from the form because request.getparameter not working in multipat form
    HashMap<String, String> ret = RegisterUtil.getParamerter(request, user);

    // set value of the user
    // set user attributs from registration form
    ((IndividualRegistrationBean) user).setLastName(ret.get("lastname"));
    ((IndividualRegistrationBean) user).setFirstName(ret.get("firstname"));
    user.setFullName(ret.get("firstname") + " " + ret.get("lastname"));
    ((IndividualRegistrationBean) user).setAdress(ret.get("adresse"));
    ((IndividualRegistrationBean) user).setGender(ret.get("gender"));
    ((IndividualRegistrationBean) user).setTypeClient("Individual");

    ((IndividualRegistrationBean) user).setBirthDate(ret.get("birthdate"));
    ((IndividualRegistrationBean) user).setPlaceOfBirth(ret.get("birthplace"));
    ((IndividualRegistrationBean) user).setNationality(ret.get("Nationalite"));

    // contact
    ((IndividualRegistrationBean) user).setPhone(Long.parseLong(ret.get("full_phone")));
    ((IndividualRegistrationBean) user).setEmail(ret.get("email"));

    // Authentification
    ((IndividualRegistrationBean) user).setUserName(ret.get("userName"));
    ((IndividualRegistrationBean) user).setEtatClient("new");
    ((IndividualRegistrationBean) user).setPassword(ret.get("repass"));
    user.setTypeClient("Individual");

    // checks if the request actually contains upload file
    if (!ServletFileUpload.isMultipartContent(request)) {
      request.setAttribute("register_error", "Request does not contain upload data");
      rd = request.getRequestDispatcher("/Views/register.jsp");
      rd.forward(request, response);
    } else {
      //Upload
      String uploadPath = RegisterUtil.Upload(user);
      session.setAttribute("uploadPath", uploadPath);

      //insert user
      RegisterDao InscriptionDao = new RegisterDaoImpl();
      // get inserted user id
      String result = InscriptionDao.AddIndividual(user);
      // Check if Individual is addes succesfully
      if (!result.equals("SUCCES")) {
        request.setAttribute("register_error", "An error has occured please try again");
        rd = request.getRequestDispatcher("Views/register.jsp");
        rd.forward(request, response);
      } else {

        SavingsAccount savings = new SavingsAccount();
        //set account attributes
        savings.setAccountState("new");
        savings.setName("Savings Account");
        savings.setAccountType("saving");
        savings.setBalance(new BigDecimal("0"));
        Currency currency = comptedao.getCurrency("DZD");
        savings.setCurrency(currency);
        savings.setOwnerID(user.getId());
        savings.setTransferLimit(new BigDecimal("50000"));

        savings.setSavingGoal(new BigDecimal("10000"));

        String AccountNumber = RIB.generateAccountNumber(11);

        Long key = RIB.getRIBKey(bank, branch, AccountNumber);
        savings.setRIB(AccountNumber + key);
        String iban = Iban.composeIBAN(country, bank);
        savings.setIBAN(iban);
        int id = comptedao.AddAccount(savings);
        savings.setId(id);
        comptedao.AddSavingsAccount(savings);

        CurrentAccount current = new CurrentAccount();
        //set account attributes
        current.setAccountState("new");
        current.setName("Checking Account");
        current.setAccountType("checking");
        current.setBalance(new BigDecimal("0"));
        current.setCurrency(currency);
        current.setOwnerID(user.getId());
        current.setTransferLimit(new BigDecimal("50000"));

        current.setBudget(new BigDecimal("20000"));
        current.setPeriod("Monthly");
        AccountNumber = RIB.generateAccountNumber(11);

        key = RIB.getRIBKey(bank, branch, AccountNumber);
        current.setRIB(AccountNumber + key);
        iban = Iban.composeIBAN(country, bank);
        current.setIBAN(iban);

        id = comptedao.AddAccount(current);
        current.setId(id);

        res = comptedao.AddCurrentAccount(current);

        if (res.equals("registred")) {

          //generate code and send email
          //genrate code
          String code = EmailUtility.getRandomNumberString();

          session.setAttribute("code", code);
          session.setAttribute("user", user);

          rd = request.getRequestDispatcher("Confirm Email");
          rd.forward(request, response);

        } else {
          request.setAttribute("error", "An error has occured please try again");
          rd = request.getRequestDispatcher("/Views/register.jsp");
          rd.forward(request, response);
        }
      }
    }

  }

}
