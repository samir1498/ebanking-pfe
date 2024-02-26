/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.login.dao;

import com.login.bean.LoginBean;
import connexion.util.SingletonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class LoginDaoImpl implements LoginDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public String authenticateUser(LoginBean login) {

    String userName = login.getUsername();
    String password = login.getPassword();
    
    String query = "SELECT * FROM public.client where username=? and passwordclient=?";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, userName);
      preparedStatement.setString(2, password);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        return rs.getString("ClientState");
      }
    } catch (SQLException ex) {
      Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.login";
  }
}
