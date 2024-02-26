/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.email_sender.util;

import com.email_sender.bean.Email;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A utility class for sending e-mail
 * messages
 *
 * @author www.codejava.net
 *
 */
public class EmailUtility {

  public static void sendEmail(String host, String port, final String userName, final String password, String toAddress, String subject, String message) throws AddressException,
          MessagingException {

    // sets SMTP server properties
    Properties properties = new Properties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.sendpartial", "true");

    // creates a new session with an authenticator
    Authenticator auth = new Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
      }
    };

    Session session = Session.getInstance(properties, auth);

    // creates a new e-mail message
    MimeMessage msg = new MimeMessage(session);

    msg.setFrom(new InternetAddress(userName));
    InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
    msg.setRecipients(Message.RecipientType.TO, toAddresses);
    msg.setSubject(subject, "UTF-8");
    msg.setSentDate(new Date());
    msg.setText(message, "UTF-8");

    // sends the e-mail
    Transport.send(msg);

  }

  public static void sendEmail(Email email) throws AddressException, MessagingException {

    // sets SMTP server properties
    Properties properties = new Properties();
    properties.put("mail.smtp.host", email.getHost());
    properties.put("mail.smtp.port", email.getPort());
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.sendpartial", "true");

    // creates a new session with an authenticator
    Authenticator auth = new Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(email.getUser(), email.getPass());
      }
    };

    Session session = Session.getInstance(properties, auth);

    // creates a new e-mail message
    MimeMessage msg = new MimeMessage(session);

    msg.setFrom(new InternetAddress(email.getUser()));
    InternetAddress[] toAddresses = {new InternetAddress(email.getRecipient())};
    msg.setRecipients(Message.RecipientType.TO, toAddresses);
    msg.setSubject(email.getSubject(), "UTF-8");
    msg.setSentDate(new Date());
    msg.setText(email.getContent(), "UTF-8");

    // sends the e-mail
    Transport.send(msg);

  }

  public static String getRandomNumberString() {
    // It will generate 6 digit random Number.
    // from 0 to 999999
    Random rnd = new Random();
    int number = rnd.nextInt(999999);

    // this will convert any number sequence into 6 character.
    return String.format("%06d", number);
  }
}
