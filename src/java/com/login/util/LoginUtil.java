/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.login.util;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.cards.bean.Card;
import com.cards.dao.CardDao;
import com.cards.dao.CardDaoImpl;
import com.transaction.dao.TransactionDao;
import com.transaction.dao.TransactionDaoImpl;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samir
 */
public class LoginUtil {

  public static void LoadClientByUserName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession session = request.getSession();

    String username = (String) session.getAttribute("username");
    session.setAttribute("username", username);
    CardDao carddao = new CardDaoImpl();

    if (username != null) {
      //create new client object
      Client client = new Client();
      client.setUsername(username);
      //get the client by username from database
      ClientDao clientdao = new ClientDaoImpl();
      clientdao.getClientByUserName(client);

      request.getSession().setAttribute("client", client);

      //get client accounts
      AccountDao accountdao = new AccountDaoImpl();
      request.getSession().setAttribute("currencies", accountdao.getCurrencies());

      List<Account> l = accountdao.listAccounts(client);
      //sort account by id
      Collections.sort(l, (Account o1, Account o2) -> o1.getId() - o2.getId());
      //add accounts list to client object
      client.setAccounts(l);
      //getTransactions
      TransactionDao transactiondao = new TransactionDaoImpl();
      List<Card> cards = new LinkedList<>();
      for (Account account : client.getAccounts()) {
        List<Card> cl = accountdao.listCards(account.getId());
        cards.addAll(cl);
        for (Card c : cl) {
          c.setTransactions(carddao.listCardTransactions(c.getCardNumber()));
        }
        account.setCards(cl);
        account.setTransactions(transactiondao.getTransactions(account));
      }
      session.setAttribute("cl", cards);

    }
  }
}
