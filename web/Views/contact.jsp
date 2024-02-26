<%-- 
    Document   : login
    Created on : 17 juin 2022, 21:12:32
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <fmt:bundle basename="i18n.Bundle">
      <title>Contact Us</title>
    </fmt:bundle>

    <link rel="stylesheet" href="css/navbar-style.css">
    <link rel="stylesheet" href="css/contact.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">

    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="js/jquery.cookie.js"></script>


    <c:if test="${langue == 'ar'}">
      <style>
        *{
          direction: rtl;
        }
        .toggle-button{
          left: 1rem;
          right: auto
        }
      </style>
    </c:if>

  </head>

  <body>
    <c:if test="${langue !=null}">
      <fmt:setLocale value="${langue}"></fmt:setLocale>
    </c:if>
    <fmt:bundle basename="i18n.Bundle">
      <nav class="navbar">


        <div class="brand-title"><a href="Home"><i class="fa-solid fa-building-columns"></i> <label> <fmt:message key="logo"/> </label></a></div>

        <a href="#" class="toggle-button">
          <span class="bar"></span>
          <span class="bar"></span>
          <span class="bar"></span>
        </a>
        <div class="navbar-links">
          <ul>
            <li><a href="Home"><fmt:message key="navbar.home"/></a></li>
            <li><a href="Login"><fmt:message key="navbar.login"/></a></li>

            <li><a href="Register"><fmt:message key="navbar.register"/></a></li>
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
      <div class="login-form-container">

        <form action="Contact" method="post" id="contact">
          <h1>Contact Us</h1>
          <table>

            <tr>
              <td><input id="Name" type="text" name="username" placeholder="Name" required autofocus></td>
            </tr>
            <tr>
              <td><input id="email" type="email" name="email" placeholder="Email" required ></td>
            </tr>
            <tr>
              <td><input id="email" type="text" name="subject" placeholder="Subject" required ></td>
            </tr>
            <tr>
              <td><textarea id="message" name="message" form="contact" rows="5" placeholder="Message" required ></textarea></td>
            </tr>

            <tr>
              <td><input type="submit" value="Send"></td>
            </tr>

          </table>

        </form>


      </div>


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

          $.removeCookie('active_popup', {path: '/'});
          $.removeCookie("transfer_error", {path: '/'});
          localStorage.clear();
        });

      </script>


    </fmt:bundle>
  </body>

</html>
