/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.register.util;

import com.register.bean.RegisterBean;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Samir
 */
public class RegisterUtil {

  public final static int TAILLE_TAMPON = 10240;
  public final static String UPLOAD_DIRECTORY = "D:" + File.separator + "fichiers";
  private final static int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
  private final static int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
  private final static int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

  public static HashMap<String, String> getParamerter(HttpServletRequest request, RegisterBean user) throws UnsupportedEncodingException {
    request.setCharacterEncoding("UTF-8");
    //(field, value) map to store the values from the form because request.getparameter not working in multipat form
    HashMap<String, String> ret = new HashMap<>();
    try {

      List<FileItem> items = new ServletFileUpload(
              new DiskFileItemFactory()).parseRequest(request);
      // get values from form
      for (FileItem item : items) {
        if (item.isFormField()) {
          // Process regular form field (input type="text|radio|checkbox|etc", select,
          // etc).
          String fieldname = item.getFieldName();
          String fieldvalue = item.getString("UTF-8");
          ret.put(fieldname, fieldvalue);

        } else {
          user.getFiles().add(item);
        }
      }
    } catch (FileUploadException ex) {
      Logger.getLogger(RegisterUtil.class.getName()).log(Level.SEVERE, null, ex);
    }

    return ret;
  }

  /**
   *
   * @param user
   * @return
   */
  public static String Upload(RegisterBean user) {

    // configures upload settings
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(THRESHOLD_SIZE);
    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setFileSizeMax(MAX_FILE_SIZE);
    upload.setSizeMax(MAX_REQUEST_SIZE);
    // constructs the directory path to store upload file
    String uploadPath = UPLOAD_DIRECTORY + File.separator + user.getTypeClient() + File.separator + user.getUserName() + File.separator;
    String loadingpath = "/fichiers" + File.separator + user.getTypeClient() + File.separator + user.getUserName() + File.separator;

    // creates the directory if it does not exist
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
      uploadDir.mkdir();
    }
    // Process form file field (input type="file").
    user.setDocumentsPath(loadingpath);

    return uploadPath;
  }

}
