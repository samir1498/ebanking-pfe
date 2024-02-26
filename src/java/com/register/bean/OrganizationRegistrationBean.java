/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.register.bean;

/**
 *
 * @author Samir
 */
public class OrganizationRegistrationBean extends RegisterBean {

  private String Name;
  private String CreationDate;
  private String Nationality;
  private String DocumentsPath;
  private String Industrie;


  public String getName() {
    return Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

  public String getCreationDate() {
    return CreationDate;
  }

  public void setCreationDate(String CreationDate) {
    this.CreationDate = CreationDate;
  }

  public String getNationality() {
    return Nationality;
  }

  public void setNationality(String Nationality) {
    this.Nationality = Nationality;
  }

  @Override
  public String getDocumentsPath() {
    return DocumentsPath;
  }

  @Override
  public void setDocumentsPath(String DocumentsPath) {
    this.DocumentsPath = DocumentsPath;
  }

  public String getIndustrie() {
    return Industrie;
  }

  public void setIndustrie(String Industrie) {
    this.Industrie = Industrie;
  }

}
