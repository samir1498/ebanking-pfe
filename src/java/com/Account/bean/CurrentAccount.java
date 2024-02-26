/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Account.bean;

import java.math.BigDecimal;

/**
 *
 * @author Samir
 */
public class CurrentAccount extends Account {
  private BigDecimal Budget;
  private String period;

  public BigDecimal getBudget() {
    return Budget;
  }

  public void setBudget(BigDecimal Budget) {
    this.Budget = Budget;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  @Override
  public String toString() {
    return "CurrentAccount{" + "Budget=" + Budget + ", period=" + period + '}';
  }
  
  
}
