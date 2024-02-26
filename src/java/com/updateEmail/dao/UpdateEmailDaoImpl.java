/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.updateEmail.dao;

import connexion.util.SingletonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class UpdateEmailDaoImpl implements UpdateEmailDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public String UpdateEmail(String email, int id) {
    try {
      String query = "update client set email=? where id=?";
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1,email);
      preparedStatement.setInt(2, id);
      
      preparedStatement.executeUpdate();
      
    } catch (SQLException ex) {
      Logger.getLogger(UpdateEmailDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

}
