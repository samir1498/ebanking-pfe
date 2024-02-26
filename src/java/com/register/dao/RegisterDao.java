/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.register.dao;

import com.register.bean.RegisterBean;

/**
 *
 * @author Samir
 */
public interface RegisterDao {

  public String AddIndividual(RegisterBean inscription);

  public String AddOrganisation(RegisterBean inscription);
}
