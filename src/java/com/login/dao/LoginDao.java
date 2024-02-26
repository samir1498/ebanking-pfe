/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.login.dao;

import com.login.bean.LoginBean;

/**
 *
 * @author Samir
 */
public interface LoginDao {

  public String authenticateUser(LoginBean login);
}
