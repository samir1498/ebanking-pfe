/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transaction.util;

/**
 *
 * @author Samir
 */
public class RIBValidationMsgs {

  private String FrMessage;
  private String ENMessage;
  private String ARMessage;
  private boolean isError;
  private String RIBorIBAN;

  public String getRIBorIBAN() {
    return RIBorIBAN;
  }

  public void setRIBorIBAN(String RIBorIBAN) {
    this.RIBorIBAN = RIBorIBAN;
  }

  public boolean isIsError() {
    return isError;
  }

  public void setIsError(boolean isError) {
    this.isError = isError;
  }

  public String getFrMessage() {
    return FrMessage;
  }

  public void setFrMessage(String FrMessage) {
    this.FrMessage = FrMessage;
  }

  public String getENMessage() {
    return ENMessage;
  }

  public void setENMessage(String ENMessage) {
    this.ENMessage = ENMessage;
  }

  public String getARMessage() {
    return ARMessage;
  }

  public void setARMessage(String ARMessage) {
    this.ARMessage = ARMessage;
  }

  @Override
  public String toString() {
    return "RIBValidationMsgs{" + "FrMessage=" + FrMessage + ", ENMessage=" + ENMessage + ", ARMessage=" + ARMessage + ", isError=" + isError + '}';
  }

}
