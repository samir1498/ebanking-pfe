<%-- 
    Document   : registered
    Created on : 21 juin 2022, 11:38:27
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
    <title>Email Confiramtion</title>

    <link rel="stylesheet" href="css/navbar-style.css">
    <link rel="stylesheet" href="css/email_confirmation.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">

    <script src="js/jquery/jquery-3.6.0.min.js"></script>
    <script src="js/jquery/jquery.cookie.js"></script>
    <script defer src="js/navbar.js"></script>
    <c:if test="${codeError=='Code not correct'}">
      <style>
        .code{
          border: 1px solid red;
        }
      </style>
    </c:if>
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
          <h2>Verify Your Email</h2>
          <p>We emailed you the six digit code to <strong>${user.getEmail()}</strong> <br/> Enter the code below to confirm your email address.</p>
          <form action="RestPassword_Email_Confirmed" method="post">
            <div class="code-container">
              <input type="number" name ="1" class="code" placeholder="0" min="0" max="9" required>
              <input type="number" name ="2" class="code" placeholder="0" min="0" max="9" required>
              <input type="number" name ="3" class="code" placeholder="0" min="0" max="9" required>
              <input type="number" name ="4" class="code" placeholder="0" min="0" max="9" required>
              <input type="number" name ="5" class="code" placeholder="0" min="0" max="9" required>
              <input type="number" name ="6" class="code" placeholder="0" min="0" max="9" required>
            </div>
            <input type="submit" name="name" style="display: none">
          </form>

          <p><a href="Confirm Email">Didn't receive the code?</a></p>
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

      const codes = document.querySelectorAll('.code');

      codes[0].focus();

      codes.forEach((code, idx) => {
        code.addEventListener('keydown', (e) => {
          if (e.key >= 0 && e.key <= 9) {
            codes[idx].value = '';

            if (idx + 1 < 6) {
              setTimeout(() => codes[idx + 1].focus(), 10);
            }

          } else if (e.key === 'Backspace') {
            if (idx - 1 !== -1) {
              setTimeout(() => codes[idx - 1].focus(), 10);
            }
          }
        });
      });
    </script>
  </body>
</html>
