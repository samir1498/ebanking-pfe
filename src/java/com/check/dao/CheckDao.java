/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.check.dao;

import com.check.bean.Check;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface CheckDao {

  public String AddCheck(Check check);

  public Check getCheck(int checkNumber);

  public String UpdateCheckState(String state, String CheckNumber);

  public List<Check> getChecks(int idClient);

}
