/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.documents.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.transaction.bean.Transaction;
import com.transaction.dao.TransactionDao;
import com.transaction.dao.TransactionDaoImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samir
 */
@WebServlet(name = "DownloadSatement", urlPatterns = {"/DownloadSatement"})
public class DownloadSatement extends HttpServlet {

  /**
   * Handles the HTTP <code>GET</code>
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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
  }

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
    response.setContentType("application/pdf");
    try {

      String dateFrom = request.getParameter("start-date");
      String dateTo = request.getParameter("end-date");

      TransactionDao transactiondao = new TransactionDaoImpl();
      List<Transaction> l = transactiondao.getTransactions(dateFrom, dateTo);

      Document document = new Document();
      File file = new File("D:/test.pdf");
      if (!file.exists()) {
        file.getParentFile().mkdirs(); // Will create parent directories if not exists
      }
      //
      file.createNewFile();
      FileOutputStream s = new FileOutputStream(file, false);

      PdfWriter.getInstance(document, s);
      document.open();
      Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.WHITE);

      // Creating a table       
      PdfPTable table = new PdfPTable(6);

      String[] header = {"Date", "From", "To", "Account From", "Account To", "Amount"};
      for (String col : header) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(1);
        cell.setNoWrap(true);
        table.addCell(cell);
      }

      l.forEach((transaction) -> {
        table.addCell(transaction.getDate());
        table.addCell(transaction.getClientFrom().getFullName());
        table.addCell(transaction.getClientTo().getFullName());
        table.addCell(String.valueOf(transaction.getAccountFrom().getId()));
        table.addCell(String.valueOf(transaction.getAccountTo().getId()));
        table.addCell(transaction.getAmount().toPlainString());

      });

      // Adding Table to document        
      document.add(table);

      document.close();

      response.setHeader("Content-disposition", "attachment; filename=samir.pdf"); //use your file name to be displayed when downloaded

      try ( InputStream in = new FileInputStream(file); // location of file 
                OutputStream out = response.getOutputStream()) {

        byte[] buffer = new byte[4096];

        int numBytesRead;
        while ((numBytesRead = in.read(buffer)) > 0) {
          out.write(buffer, 0, numBytesRead);
        }
      }
    } catch (DocumentException ex) {
      Logger.getLogger(DownloadSatement.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private static void setCookie(HttpServletResponse response, String nom, String valeur, int maxAge) throws IOException {
    Cookie cookie = new Cookie(nom, URLEncoder.encode(valeur, "UTF-8"));
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
}
