/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.register.bean;

/**
 *
 * @author Samir
 */
public class IndividualRegistrationBean extends RegisterBean {

  private String LastName;
  private String FirstName;
  private String Gender;
  private String BirthDate;
  private String PlaceOfBirth;

  public String getLastName() {
    return LastName;
  }

  public void setLastName(String LastName) {
    this.LastName = LastName;
  }

  public String getFirstName() {
    return FirstName;
  }

  public void setFirstName(String FirstName) {
    this.FirstName = FirstName;
  }

  public String getGender() {
    return Gender;
  }

  public void setGender(String Gender) {
    this.Gender = Gender;
  }

  public String getBirthDate() {
    return BirthDate;
  }

  public void setBirthDate(String BirthDate) {
    this.BirthDate = BirthDate;
  }

  public String getPlaceOfBirth() {
    return PlaceOfBirth;
  }

  public void setPlaceOfBirth(String PlaceOfBirth) {
    this.PlaceOfBirth = PlaceOfBirth;
  }

}
