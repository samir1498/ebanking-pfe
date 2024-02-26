/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.register.dao;

import com.register.bean.IndividualRegistrationBean;
import com.register.bean.OrganizationRegistrationBean;
import com.register.bean.RegisterBean;
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
public class RegisterDaoImpl implements RegisterDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public String AddIndividual(RegisterBean client) {
    int Id;

    String queryClient = "INSERT INTO public.client\n"
            + "(username, passwordclient,"
            + " registrationdate,"
            + " phonenumber,"
            + " email, adresse,"
            + " clientstate, clienttype, documentspath, fullname, birthdate, nationality)\n"
            + "VALUES(?, ?, now(), ?, ?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY/MM/DD'), ?) returning id;";

    String queryIndividu = "INSERT INTO public.individual\n"
            + "(id, lastname,"
            + " firstname,"
            + " gender,"
            + " placeofbirth) "
            + " VALUES(?, ?, ?, ?, ?);";
    try {
      //insert in client table
      PreparedStatement preparedStatementClient = connexion.prepareStatement(queryClient);

      preparedStatementClient.setString(1, client.getUserName());
      preparedStatementClient.setString(2, client.getPassword());
      preparedStatementClient.setLong(3, client.getPhone());

      preparedStatementClient.setString(4, client.getEmail());
      preparedStatementClient.setString(5, client.getAdress());
      preparedStatementClient.setString(6, client.getEtatClient());
      preparedStatementClient.setString(7, client.getTypeClient());
      preparedStatementClient.setString(8, client.getDocumentsPath());
      preparedStatementClient.setString(9, client.getFullName());
      preparedStatementClient.setString(10, client.getBirthDate());

      preparedStatementClient.setString(11, client.getNationality());

      ResultSet rsClient = preparedStatementClient.executeQuery();
      if (!rsClient.next()) {
        return "FAILED ";
      }
      Id = rsClient.getInt("id");
      client.setId(Id);

      //insert in individual table
      PreparedStatement preparedStatement = connexion.prepareStatement(queryIndividu);

      preparedStatement.setLong(1, client.getId());
      preparedStatement.setString(2, ((IndividualRegistrationBean) client).getLastName());
      preparedStatement.setString(3, ((IndividualRegistrationBean) client).getFirstName());
      preparedStatement.setString(4, ((IndividualRegistrationBean) client).getGender());

      preparedStatement.setString(5, ((IndividualRegistrationBean) client).getPlaceOfBirth());

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
          Logger.getLogger(RegisterDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        }
      } catch (SQLException e2) {
        Logger.getLogger(RegisterDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
      return "FAILED ";
    }
    return "SUCCES";
  }

  @Override
  public String AddOrganisation(RegisterBean client) {
    int Id;

    String queryClient = "INSERT INTO public.client\n"
            + "(username, passwordclient, registrationdate,"
            + " phonenumber, email, adresse,"
            + " clientstate, clienttype, documentspath, fullname, birthdate, nationality)\n"
            + "VALUES(?, ?, now(), ?, ?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY/MM/DD'), ?) returning id;";

    String queryOrganisation = "INSERT INTO public.organization\n"
            + "(id, \"name\","
            + " industrie)\n"
            + "VALUES(?, ?, ?);";
    try {
      //insert in user table
      PreparedStatement preparedStatementClient = connexion.prepareStatement(queryClient);

      preparedStatementClient.setString(1, client.getUserName());
      preparedStatementClient.setString(2, client.getPassword());
      preparedStatementClient.setLong(3, client.getPhone());

      preparedStatementClient.setString(4, client.getEmail());
      preparedStatementClient.setString(5, client.getAdress());
      preparedStatementClient.setString(6, client.getEtatClient());
      preparedStatementClient.setString(7, client.getTypeClient());
      preparedStatementClient.setString(8, client.getDocumentsPath());

      preparedStatementClient.setString(9, client.getFullName());

      preparedStatementClient.setString(10, client.getBirthDate());

      preparedStatementClient.setString(11, client.getNationality());

      ResultSet rsClient = preparedStatementClient.executeQuery();
      if (!rsClient.next()) {
        return "FAILED ";
      }
      Id = rsClient.getInt("id");
      client.setId(Id);

      //insert in organization table
      PreparedStatement preparedStatement = connexion.prepareStatement(queryOrganisation);

      preparedStatement.setLong(1, client.getId());

      preparedStatement.setString(2, ((OrganizationRegistrationBean) client).getName());

      preparedStatement.setString(3, ((OrganizationRegistrationBean) client).getIndustrie());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
          Logger.getLogger(RegisterDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        }
      } catch (SQLException e2) {
        Logger.getLogger(RegisterDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
      return "FAILED ";
    }
    return "SUCCES";
  }

}
