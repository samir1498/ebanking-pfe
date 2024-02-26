/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samir
 */
public class LanguageComp {

  public static void Language(HttpServletRequest request, HttpSession session) throws FileNotFoundException, IOException {

    ResourceBundle bundle = ResourceBundle.getBundle("configuration.application");
    List<String> langues = new LinkedList<>();
    if (bundle.getString("FR").equals("true")) {
      langues.add("fr");
    }
    if (bundle.getString("EN").equals("true")) {
      langues.add("en");
    }
    if (bundle.getString("AR").equals("true")) {
      langues.add("ar");
    }
    session.setAttribute("langues", langues);

    if (langues.isEmpty() && session.getAttribute("langue") == null) {
      Locale locale = request.getLocale();
      String langue = locale.getLanguage();
      session.setAttribute("langue", langue);
    } else if (langues.size() == 1 && session.getAttribute("langue") == null) {
      session.setAttribute("langue", langues.get(0));
    } else if ((!langues.isEmpty()) && langues.size() > 1 && session.getAttribute("langue") == null) {
      Locale locale = request.getLocale();
      String langue = locale.getLanguage();
      session.setAttribute("langue", langue);
    }
  }

}
