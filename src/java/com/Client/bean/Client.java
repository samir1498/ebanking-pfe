/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Client.bean;

import com.Account.bean.Account;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Samir
 */
public class Client implements Serializable {

  private int id;
  private String username;
  private String registrationDate;
  private String phoneNumber;
  private String Email;
  private String Adress;
  private String clientState;
  private String ClientType;
  private String DocumentsPath;
  private String FullName;
  private String ProfilePicturePath;
  private List<Account> Accounts;

  public String getProfilePicturePath() {
    return ProfilePicturePath;
  }

  public void setProfilePicturePath(String ProfilePicturePath) {
    this.ProfilePicturePath = ProfilePicturePath;
  }

  public String getFullName() {
    return FullName;
  }

  public void setFullName(String FullName) {
    this.FullName = FullName;
  }

  public List<Account> getAccounts() {
    return Accounts;
  }

  public void setAccounts(List<Account> Accounts) {
    this.Accounts = Accounts;
  }

  public String getDocumentsPath() {
    return DocumentsPath;
  }

  public void setDocumentsPath(String DocumentsPath) {
    this.DocumentsPath = DocumentsPath;
  }

  public String getClientType() {
    return ClientType;
  }

  public void setClientType(String ClientType) {
    this.ClientType = ClientType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String Email) {
    this.Email = Email;
  }

  public String getAdress() {
    return Adress;
  }

  public void setAdress(String Adress) {
    this.Adress = Adress;
  }

  public String getClientState() {
    return clientState;
  }

  public void setClientState(String clientState) {
    this.clientState = clientState;
  }

  @Override
  public String toString() {
    return "Client{" + "id=" + id + ", username=" + username + ", registrationDate=" + registrationDate + ", phoneNumber=" + phoneNumber + ", Email=" + Email + ", Adress=" + Adress + ", clientState=" + clientState + ", ClientType=" + ClientType + ", DocumentsPath=" + DocumentsPath + ", FullName=" + FullName + ", ProfilePicturePath=" + ProfilePicturePath + ", Accounts=" + Accounts + '}';
  }



}
