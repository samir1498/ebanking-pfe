/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Client.dao;

import com.Client.bean.Client;
import com.transaction.bean.Transaction;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface ClientDao {

  public Client getClientById(int id);

  public Client getClientByUserName(Client client);

  public Client getClientByAccountId(int id);

  public List<Transaction> getTransactions(int idClient);

  public String setProfilePic(int id, String Path);

  public String UpdateUsername(int id, String username);

  public String UpdateEmail(int id, String username);

  public String UpdatePhoneNumber(int id, String username);

  public String UpdateAddress(int id, String username);

  public String ChangePassword(int id, String password);
  
  public String UpdatePassword(String Email, String password);

}
