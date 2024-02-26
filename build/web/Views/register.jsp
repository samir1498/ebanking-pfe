<%-- 
    Document   : register
    Created on : 19 juin 2022, 11:41:35
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
      <title><fmt:message key="title.register" /></title>
    </fmt:bundle>


    <link rel="stylesheet" href="css/navbar-style.css">
    <link rel="stylesheet" href="css/register-style.css">
    <link rel="stylesheet" href="intel-tel-input/css/intlTelInput.min.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">


    <script src="js/jquery/jquery-3.6.0.min.js"></script>
    <script src="js/jquery/jquery.validate.min.js"></script>

    <script src="intel-tel-input/js/intlTelInput.js"></script>

    <c:if test="${langue != 'en'}">
      <script src="js/messages/messages_${langue}.js"></script>
    </c:if>

    <script defer src="js/register/register-script_${langue}.js"></script>
    <script defer src="js/register/register.js"></script>
    <script src="js/jquery/jquery.cookie.js"></script>


    <c:if test="${langue == 'ar'}">
      <style>
        *{
          direction: rtl;
        }
        .toggle-button{
          left: 1rem;
          right: auto
        }
        .iti__flag-container{
          right: auto
        }
        form .iti__country-list{
          text-align: right;
          max-width: 21rem
        }
        form .iti__flag-container{
          right: 100%;
        }
        td.phone-td .iti__flag-container {
          direction: ltr;
        }
        #phone, #ophone {
          width: 100%;
          direction: ltr
        }
        form label{
          padding-left: auto;
          padding-right: 1.5rem;
          font-size: 1.25rem
        }
      </style>
    </c:if>


  </head>

  <body>
    <fmt:bundle basename="i18n.Bundle">
      <nav class="navbar">
        <div class="brand-title"><a href="Home"><i class="fa-solid fa-building-columns"></i> <label> <fmt:message key="logo" /> </label></a></div>
        <a href="#" class="toggle-button">
          <span class="bar"></span>
          <span class="bar"></span>
          <span class="bar"></span>
        </a>
        <div class="navbar-links">
          <ul>
            <li><a href="Home"><fmt:message key="navbar.home" /></a></li>
            <li><a href="Login"><fmt:message key="navbar.login" /></a></li>
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

      <div class="register-forms-container">

        <div class="btn-cotainer">
          <c:if test="${langue == 'ar'}">
            <button type="button" class="organisation-btn"><fmt:message key="button.organisation"/></button>
            <button type="button" class="individual-btn"><fmt:message key="button.individual"/></button>
          </c:if>
          <c:if test="${langue != 'ar'}">
            <button type="button" class="individual-btn"><fmt:message key="button.individual"/></button>
            <button type="button" class="organisation-btn"><fmt:message key="button.organisation"/></button>
          </c:if>

        </div>



        <%@ include file="../includes/OrganisationForm.jsp"%>
        <%@ include file="../includes/IndividualForm.jsp"%>


      </div>

      <script>

        function submitform() {
          $('#lang').submit();
        }

        $(document).ready(function () {
          var langue = '${langue}';
          $('#lang_select').val(langue);
        });


      </script>

    </fmt:bundle>
  </body>

</html>
