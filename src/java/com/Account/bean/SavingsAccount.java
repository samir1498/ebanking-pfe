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
public class SavingsAccount extends Account{
  protected BigDecimal savingGoal;

  public BigDecimal getSavingGoal() {
    return savingGoal;
  }

  public void setSavingGoal(BigDecimal savingGoal) {
    this.savingGoal = savingGoal;
  }

  @Override
  public String toString() {
    return "SavingsAccount{" + "savingGoal=" + savingGoal + '}';
  }

 
  
  
  
}
