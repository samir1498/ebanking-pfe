<%-- 
    Document   : email_confirmed
    Created on : 6 juil. 2022, 22:46:25
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Email Confirmed</title>

    <link rel="stylesheet" href="css/navbar-style.css">
    <link rel="stylesheet" href="css/email_confirmation.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">

    <script src="js/jquery/jquery-3.6.0.min.js"></script>
    <script src="js/jquery/jquery.cookie.js"></script>
    <script defer src="js/navbar.js"></script>

  </head>
  <body>
    <fmt:bundle basename="i18n.Bundle">
      <nav class="navbar">
        <div class="brand-title"><a href="Home"><i class="fa-solid fa-building-columns"></i><label> <fmt:message key="logo"/> </label></a></div>

        <a href="#" class="toggle-button">
          <span class="bar"></span>
          <span class="bar"></span>
          <span class="bar"></span>
        </a>
        <div class="navbar-links">
          <ul>
            <li><a href="Home"><fmt:message key="navbar.home"/></a></li>
            <li><a href="services/contact.html"><fmt:message key="link.contact"/></a></li>
              <c:if test="${langues.size()!=1 and langues.size()!=0}">
              <li>
                <form action="LanguageChangerServlet" method="post" id="lang">
                  <select id="lang_select" name="language_list" onchange="submitform()">
                    <c:forEach  items="${langues}" var="lang">
                      <option value="${lang}"><fmt:message key="lang.${lang}"/></option>
                    </c:forEach>
                  </select>
                </form>
              </li>
            </c:if>

          </ul>
        </div>
      </nav>
      <div class="main">
        <div class="container">
          <h2>Email Has Been Verified</h2>

          <p> Thanks for choosing our Bank.<br/> <br/>You will recive an update once your request has been processed.</p>

        </div>
      </div>
    </fmt:bundle>
    <script>
      const toggleButton = document.getElementsByClassName('toggle-button')[0];
      const navbarLinks = document.getElementsByClassName('navbar-links')[0];

      toggleButton.addEventListener('click', () => {
        navbarLinks.classList.toggle('active');
      });
      function submitform() {
        $('#lang').submit();
      }
      $(document).ready(function () {
        var langue = '${langue}';
        $('#lang_select').val(langue);
      });

    </script>
  </body>
</html>
