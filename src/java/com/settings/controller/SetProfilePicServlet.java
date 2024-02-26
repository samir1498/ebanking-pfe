/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.settings.controller;

import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.login.util.LoginUtil;
import com.register.util.RegisterUtil;
import connexion.util.SingletonConnection;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileExistsException;

/**
 *
 * @author Samir
 */
@WebServlet(name = "SetProfilePicServlet", urlPatterns = {"/SetProfilePic"})
public class SetProfilePicServlet extends HttpServlet {

  public final static int TAILLE_TAMPON = 10240;
  public final static String UPLOAD_DIRECTORY = "D:" + File.separator + "fichiers";
  private final static int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
  private final static int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
  private final static int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

  /**
   * Handles the HTTP <code>POST</code>
   * method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a
   * servlet-specific error occurs
   * @throws IOException if an I/O error
   * occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Client user = (Client) request.getSession().getAttribute("client");
    ClientDao clientdao = new ClientDaoImpl();
    if (user == null) {
      LoginUtil.LoadClientByUserName(request, response);
      user = (Client) request.getSession().getAttribute("client");
    }
    String username = (String) request.getSession().getAttribute("username");

    FileItem pic = null;
    // checks if the request actually contains upload file
    if (!ServletFileUpload.isMultipartContent(request)) {
      System.out.println("error multipart");
    } else {
      try {

        List<FileItem> items = new ServletFileUpload(
                new DiskFileItemFactory()).parseRequest(request);
        // get values from form
        for (FileItem item : items) {
          if (!item.isFormField()) {
            pic = item;
          }
        }
      } catch (FileUploadException ex) {
        Logger.getLogger(RegisterUtil.class.getName()).log(Level.SEVERE, null, ex);
      }
      /////////////////////////////////////////////////////////
      //////////////////////// Upload   //////////////////////
      /////////////////////////////////////////////////////////
      // configures upload settings
      DiskFileItemFactory factory = new DiskFileItemFactory();
      factory.setSizeThreshold(THRESHOLD_SIZE);
      factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

      ServletFileUpload upload = new ServletFileUpload(factory);
      upload.setFileSizeMax(MAX_FILE_SIZE);
      upload.setSizeMax(MAX_REQUEST_SIZE);
      // constructs the directory path to store upload file
      String uploadPath = UPLOAD_DIRECTORY + File.separator
              + user.getClientType() + File.separator
              + user.getUsername() + File.separator + "profile_pic" + File.separator;
      String loadingpath = "/fichiers" + File.separator
              + user.getClientType() + File.separator
              + user.getUsername() + File.separator + "profile_pic" + File.separator;

      // creates the directory if it does not exist
      File uploadDir = new File(uploadPath);
      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }
      deleteFolder(uploadDir);
      // Process form file field (input type="file").

      // write files to the upload folder
      if (pic != null) {
        File file = new File(uploadPath, pic.getName());
        if (!file.exists()) {
          try {
            pic.write(file);
            clientdao.setProfilePic(user.getId(), loadingpath + pic.getName());
            SingletonConnection.getConnection().commit();
            user.setProfilePicturePath(loadingpath + pic.getName());

          } catch (FileExistsException | SQLException ex) {
            Logger.getLogger(RegisterUtil.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
            Logger.getLogger(SetProfilePicServlet.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          try {
            clientdao.setProfilePic(user.getId(), loadingpath + pic.getName());
            SingletonConnection.getConnection().commit();
            user.setProfilePicturePath(loadingpath + pic.getName());

          } catch (SQLException ex) {
            Logger.getLogger(RegisterUtil.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
      if (username != null) {
        response.sendRedirect("Settings");
      }
    }
  }

  public static void deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if (files != null) { //some JVMs return null for empty dirs
      for (File f : files) {
        if (f.isDirectory()) {
          deleteFolder(f);
        } else {
          f.delete();
        }
      }
    }
    folder.delete();
  }

}
