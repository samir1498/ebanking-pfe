/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.register.bean;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Samir
 */
public class RegisterBean {

  private int Id;
  private String Email;
  private long Phone;
  private String Adress;
  private String UserName;
  private String Password;
  private String EtatClient;
  private String TypeClient;
  private String DocumentsPath;
  private String FullName;
  private String Nationality;
  private String BirthDate;

  public String getBirthDate() {
    return BirthDate;
  }

  public void setBirthDate(String BirthDate) {
    this.BirthDate = BirthDate;
  }

  public String getNationality() {
    return Nationality;
  }

  public void setNationality(String Nationality) {
    this.Nationality = Nationality;
  }

  public String getFullName() {
    return FullName;
  }

  public void setFullName(String FullName) {
    this.FullName = FullName;
  }

  public String getDocumentsPath() {
    return DocumentsPath;
  }

  public void setDocumentsPath(String DocumentsPath) {
    this.DocumentsPath = DocumentsPath;
  }

  private List<FileItem> files = new LinkedList<>();

  public int getId() {
    return Id;
  }

  public void setId(int IdUser) {
    this.Id = IdUser;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String Email) {
    this.Email = Email;
  }

  public long getPhone() {
    return Phone;
  }

  public void setPhone(long Phone) {
    this.Phone = Phone;
  }

  public String getAdress() {
    return Adress;
  }

  public void setAdress(String Adress) {
    this.Adress = Adress;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String UserName) {
    this.UserName = UserName;
  }

  public String getPassword() {
    return Password;
  }

  public void setPassword(String Password) {
    this.Password = Password;
  }

  public List<FileItem> getFiles() {
    return files;
  }

  public void setFiles(List<FileItem> files) {
    this.files = files;
  }

  public String getEtatClient() {
    return EtatClient;
  }

  public void setEtatClient(String EtatClient) {
    this.EtatClient = EtatClient;
  }

  public String getTypeClient() {
    return TypeClient;
  }

  public void setTypeClient(String TypeClient) {
    this.TypeClient = TypeClient;
  }

}
