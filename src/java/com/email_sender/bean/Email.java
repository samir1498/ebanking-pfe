/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.email_sender.bean;

/**
 *
 * @author Samir
 */
public class Email {

  private final String host = "outlook.office365.com";
  private final String port = "587";
  private final String user = "samirsbx2@outlook.com";
  private final String pass = "master2pfeemailsender";

  private String recipient;
  private String subject;
  private String content;

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

  public String getUser() {
    return user;
  }

  public String getPass() {
    return pass;
  }

  @Override
  public String toString() {
    return "Email{" + "host=" + host + ", port=" + port + ", user=" + user + ", pass=" + pass + ", recipient=" + recipient + ", subject=" + subject + ", content=" + content + '}';
  }
  
  

}
