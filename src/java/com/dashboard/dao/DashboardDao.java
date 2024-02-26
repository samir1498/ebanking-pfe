/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dashboard.dao;

import java.math.BigDecimal;

/**
 *
 * @author Samir
 */
public interface DashboardDao {

  public double getTotalSavingsProgress(int id);

  public double getSavingsProgress(int id);

  public double getTotalBudgetProgress(int id);

  public double getBudgetProgress(int id);
  
  public BigDecimal getOutcome(int id);
  
  public BigDecimal getIncome(int id);
}
