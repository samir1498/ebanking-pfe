/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dashboard.dao;

import connexion.util.SingletonConnection;
import java.math.BigDecimal;
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
public class DashboardDaoImpl implements DashboardDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public double getTotalSavingsProgress(int id) {
    double per = 0;
    String query = "select"
            + " Round(SUM(percentage)/count(ownerid),2) as per"
            + " from savings_stats where ownerid=? group by ownerid"
            + " ";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        per = res.getDouble("per");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return per;
  }

  @Override
  public double getSavingsProgress(int id) {
    double per = 0;

    String query = "select id,"
            + " percentage"
            + " from savings_stats where id=?";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        per = res.getDouble("percentage");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return per;
  }

  @Override
  public double getTotalBudgetProgress(int id) {
    double per = 100;
    String query = "select"
            + " Round(SUM(budgetleftper)/count(ownerid),2) as per"
            + " from expenses_stats where ownerid=? group by ownerid"
            + " ";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        per = res.getDouble("per");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return per;
  }

  @Override
  public double getBudgetProgress(int id) {
    double per = 100;

    String query = "select id,"
            + " budgetleftper"
            + " from expenses_stats where id=?";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        per = res.getDouble("budgetleftper");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return per;
  }

  @Override
  public BigDecimal getOutcome(int id) {
    BigDecimal per = BigDecimal.ZERO;
    String query = "select sum(convertedamoutlocalcurrency) as outcome from transactions t\n"
            + "where  date_transaction >= date_trunc('month', current_timestamp)\n"
            + "  and date_transaction < date_trunc('month', current_timestamp) + interval '1 month'\n"
            + "and t.clientfrom = ? and t.clientto <> t.clientfrom";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        per = res.getBigDecimal("outcome");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return per;
  }

  @Override
  public BigDecimal getIncome(int id) {
    BigDecimal per = BigDecimal.ZERO;
    String query = "select sum(convertedamoutlocalcurrency)  as income from transactions t\n"
            + "where  date_transaction >= date_trunc('month', current_timestamp)\n"
            + "  and date_transaction < date_trunc('month', current_timestamp) + interval '1 month'\n"
            + "and t.clientto = ? and t.clientto <> t.clientfrom";
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        per = res.getBigDecimal("income");
      }
    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return per;
  }

}
