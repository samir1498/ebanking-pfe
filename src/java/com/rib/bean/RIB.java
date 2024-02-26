/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rib.bean;

/**
 *
 * @author Samir
 */
public class RIB {

  private String BankCode;
  private String BrancheCode;
  private String AccountNumber;
  private String Key;

  public String getBankCode() {
    return BankCode;
  }

  public void setBankCode(String BankCode) {
    this.BankCode = BankCode;
  }

  public String getBrancheCode() {
    return BrancheCode;
  }

  public void setBrancheCode(String BrancheCode) {
    this.BrancheCode = BrancheCode;
  }

  public String getAccountNumber() {
    return AccountNumber;
  }

  public void setAccountNumber(String AccountNumber) {
    this.AccountNumber = AccountNumber;
  }

  public String getKey() {
    return Key;
  }

  public void setKey(String Key) {
    this.Key = Key;
  }

  @Override
  public String toString() {
    return BankCode + " " + BrancheCode + " " + AccountNumber + " " + Key;
  }

  public String toPlainString() {
    return BankCode + BrancheCode + AccountNumber + Key;
  }

  private static final String SOURCES
          = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String RESULTS
          = "123456789123456789234567890123456789";

  public static final boolean isValide(String RIB) {
    assert RIB != null;
    if (RIB.length() != 23) {
      return false;
    }
    final StringBuilder buffer = new StringBuilder(RIB.toUpperCase());
    for (int i = 0; i < 21; i++) {

      buffer.setCharAt(i, RESULTS.charAt(SOURCES.indexOf(
              buffer.charAt(i))));
    }
    int n1 = Integer.parseInt(buffer.substring(0, 5));
    int n2 = Integer.parseInt(buffer.substring(5, 10));
    Long n3 = Long.parseLong(buffer.substring(10, 21));
    int n4 = Integer.parseInt(buffer.substring(21));
    return (62 * n1 + 34 * n2 + 3 * n3 + n4) % 97 == 0;
  }

  public static final Long getRIBKey(String BankCode, String BranchCode, String AccountNumber) {
    String compte = BankCode + BranchCode + AccountNumber;
    assert compte != null;
    if (compte.length() != 21) {
      return -1L;
    }
    final StringBuilder buffer = new StringBuilder(compte.toUpperCase());
    for (int i = 0; i < 21; i++) {

      buffer.setCharAt(i, RESULTS.charAt(SOURCES.indexOf(
              buffer.charAt(i))));
    }
    int n1 = Integer.parseInt(buffer.substring(0, 5));
    int n2 = Integer.parseInt(buffer.substring(5, 10));
    Long n3 = Long.parseLong(buffer.substring(10, 21));
    Long RIB = 97 - ((62 * n1 + 34 * n2 + 3 * n3) % 97);

    return RIB;
  }

  public static final Long getRIBKey(String compte) {
    assert compte != null;
    if (compte.length() != 21) {
      return -1L;
    }
    final StringBuilder buffer = new StringBuilder(compte.toUpperCase());
    for (int i = 0; i < 21; i++) {

      buffer.setCharAt(i, RESULTS.charAt(SOURCES.indexOf(
              buffer.charAt(i))));
    }
    int n1 = Integer.parseInt(buffer.substring(0, 5));
    int n2 = Integer.parseInt(buffer.substring(5, 10));
    Long n3 = Long.parseLong(buffer.substring(10, 21));
    Long RIB = 97 - ((62 * n1 + 34 * n2 + 3 * n3) % 97);

    return RIB;
  }

  public static final String composeRIB(String BankCode, String BranchCode, String AccountNumber) {
    String BBAN = BankCode + BranchCode + AccountNumber;
    String key = String.valueOf(getRIBKey(BankCode, BranchCode, AccountNumber));
    BBAN = BBAN.concat(key);
    return BBAN;

  }

  public static RIB toRIB(String RIB) {

    if (RIB.length() != 23 || !isValide(RIB)) {
      return null;
    }
    String n1 = RIB.substring(0, 5);
    String n2 = RIB.substring(5, 10);
    String n3 = RIB.substring(10, 21);
    String n4 = RIB.substring(21);

    RIB rib = new RIB();
    rib.setBankCode(n1);
    rib.setBrancheCode(n2);
    rib.setAccountNumber(n3);
    rib.setKey(n4);

    return rib;
  }

  // function to generate a random string of length n
  public static String generateAccountNumber(int n) {
    // chose a Character random from this String
    String AlphaNumericString = "0123456789";

    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(n);

    for (int i = 0; i < n; i++) {

      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index = (int) (AlphaNumericString.length() * Math.random());

      // add Character one by one in end of sb
      sb.append(AlphaNumericString.charAt(index));
    }

    return sb.toString();
  }

  public static void main(String[] args) {
    String bank = "54545";
    String branch = "55454";
    String AccountNumber = generateAccountNumber(11);
    String rib = composeRIB(bank, branch, AccountNumber);
    System.out.println(isValide(rib));

  }
}
