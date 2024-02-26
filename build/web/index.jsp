<%-- 
    Document   : index
    Created on : 17 juin 2022, 20:29:17
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="includes/taglibs.jsp"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<html>

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <fmt:bundle basename="i18n.Bundle">
      <title><fmt:message key="navbar.home"/></title>
    </fmt:bundle>
    <link rel="stylesheet" href="css/navbar-style.css">
    <link rel="stylesheet" href="css/home-style.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">

    <script src="js/jquery/jquery-3.6.0.min.js"></script>
    <script src="js/jquery/jquery.cookie.js"></script>
    <script defer src="js/navbar.js"></script>

    <c:if test="${langue == 'ar'}">
      <style>
        *{
          direction: rtl;
        }
        .toggle-button{
          left: 1rem;
          right: auto
        }

        .accounts-types div a label.link-text:hover {
          margin-right: 0rem;
          margin-left: 1rem;
          transition: .5s ease-out;
          cursor: pointer
        }

        .accounts-types div a label.arrow:hover {
          margin-right: 1rem;
          margin-left: 0rem;
          transition: .5s ease-out;
          cursor: pointer
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
            <li><a href="Home" class="active"><fmt:message key="navbar.home"/></a></li>

            <li><a href="Contact"><fmt:message key="link.contact"/></a></li>
            <li><a href="Register"> <fmt:message key="navbar.register"/></a></li>
            <li><a href="Login"><fmt:message key="navbar.login"/></a></li>
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
        <div class="left">
          <div>
            <h3><fmt:message key="home.main.h3"/></h3>
            <h1><fmt:message key="home.main.h1"/></h1>

            <p><fmt:message key="home.main.p"/></p>
            <div class="links">
              <a href="Register" class="Open-account"><fmt:message key="home.main.a.open"/></a>
              <a href="" class="Get-in-touch"><fmt:message key="home.main.a.get"/></a>
            </div>
          </div>
        </div>
        <img src="images/Inkedfreelancer-working-home-with-cat_6280-871_LI-removebg-preview.png" alt="">

      </div>

      <div class="services">

        <div class="Notifications">

          <div class="left">
            <div>
              <h3><fmt:message key="home.notification.h3"/></h3>
              <h1><fmt:message key="home.notification.h1"/></h1>
              <p><fmt:message key="home.notification.p"/></p>
            </div>
          </div>
          <img src="images/services/feature-item-1.png" alt="">
        </div>
        <div class="safe-saving">
          <img src="images/services/feature-item-2.png" alt="">
          <div class="left">
            <div>
              <h3><fmt:message key="home.services.h3"/></h3>
              <h1><fmt:message key="home.services.h1"/></h1>
              <p><fmt:message key="home.services.p"/></p>
            </div>
          </div>

        </div>

        <div class="accounts">

          <div class="left">
            <div>
              <h3><fmt:message key="home.accounts.h3"/></h3>
              <h1><fmt:message key="home.accounts.h1"/></h1>
              <p><fmt:message key="home.accounts.p"/></p>
            </div></div>

          <div class="accounts-types">
            <div class="cheking">
              <span><img src="images/services/checking.png" alt=""></span>
              <h3><fmt:message key="home.account.type1.h3"/></h3>
              <p><fmt:message key="home.account.type1.p"/>
              </p>
              <a href="Register"><label class="link-text"><fmt:message key="home.account.type1.a"/></label>


                <label class="arrow">
                  <c:if test="${langue != 'ar'}"><i class="fa-solid fa-arrow-right"></i></c:if>
                  <c:if test="${langue == 'ar'}"><i class="fa-solid fa-arrow-left"></i></c:if>
                  </label></a>
              </div>

              <div class="saving">
                <span><img src="images/services/savings.png" alt=""></span>
                <h3><fmt:message key="home.account.type2.h3"/></h3>
              <p><fmt:message key="home.account.type2.p"/></p>
              <c:if test="${langue == 'ar'}"></c:if>
              <c:if test="${langue != 'ar'}"></c:if>
                <a href="Register">
                  <label class="link-text">
                  <fmt:message key="home.account.type2.a"/> 
                </label> 
                <label class="arrow">
                  <c:if test="${langue != 'ar'}"><i class="fa-solid fa-arrow-right"></i></c:if>
                  <c:if test="${langue == 'ar'}"><i class="fa-solid fa-arrow-left"></i></c:if>
                  </label></a>
              </div>

            </div>
          </div>
          <div class="card">
            <div class="left">
              <div>
                <h3><fmt:message key="home.card.h3"/> </h3>
              <h1><fmt:message key="home.card.h1"/> </h1>
              <p><fmt:message key="home.card.p"/> </p>
            </div>
          </div>
          <div class="right"><img src="images/card-img-1.png" alt="">
            <a href="Register"><fmt:message key="home.card.a"/> </a></div>
        </div>
        <!--  Loan service -->
        <div class="Loan">
          <div class="left">
            <div>
              <h3><fmt:message key="home.loan.h3"/></h3>
              <h1><fmt:message key="home.loan.h1"/></h1>
              <p><fmt:message key="home.loan.p"/></p>
              <a href="Register"><fmt:message key="home.loan.a"/></a>
            </div></div>
          <div class="Loan-types">
            <a href="home-loan.html">
              <div class="home">
                <span><img src="images/loan/loan-11.png" alt=""></span>
                <h3><fmt:message key="home.loan.type1.h2"/></h3>
                <ul>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type1.ul.li1"/> </li>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type1.ul.li2"/> </li>
                </ul>
              </div>
            </a>
            <a href="car-loan.html">
              <div class="car">
                <span><img src="images/loan/loan-22.png" alt=""></span>
                <h3><fmt:message key="home.loan.type2.h2"/></h3>
                <ul>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type2.ul.li1"/> </li>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type2.ul.li2"/> </li>
                </ul>
              </div>
            </a>
            <a href="education-loan.html">
              <div class="education">
                <span><img src="images/loan/loan-33.png" alt="loan"></span>
                <h3><fmt:message key="home.loan.type3.h2"/></h3>
                <ul>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type3.ul.li1"/> </li>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type3.ul.li2"/></li>
                </ul>

              </div>
            </a>
            <a href="business-loan.html">
              <div class="business-loan">
                <span><img src="images/loan/loan-44.png" alt="loan"></span>
                <h3><fmt:message key="home.loan.type4.h2"/></h3>
                <ul>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type4.ul.li1"/> </li>
                  <li><i class="fa-solid fa-circle-check"></i> <fmt:message key="home.loan.type4.ul.li2"/> </li>
                </ul>
              </div>
            </a>
          </div>

        </div>

        <div class="get-started">
          <div class="left">
            <div>
              <h1><fmt:message key="home.getstarted.h1"/></h1>
              <p><fmt:message key="home.getstarted.p"/></p>
              <a href="Register"><fmt:message key="home.getstarted.a"/></a>
            </div>
          </div>

          <img src="images/get-start.png" alt="">
        </div>
      </div>



      <footer>
        <div class="bank-logo">
          <div class="brand-title">
            <a href=""><label><i class="fa-solid fa-building-columns"></i>  <fmt:message key="logo"/> </label></a>
            <p><fmt:message key="footer.p"/></p>
          </div>
        </div>

        <ul class="social">
          <li><a href=""><i class="fa-brands fa-facebook-f"></i></a></li>
          <li><a href=""><i class="fa-brands fa-twitter"></i></a></li>
          <li><a href=""><i class="fa-brands fa-linkedin-in"></i></a></li>
          <li><a href=""><i class="fa-brands fa-instagram"></i></a></li>
        </ul>
        <ul class="links">
          <li><a href="Home"><fmt:message key="navbar.home"/></a></li>
          <li><a href=""><fmt:message key="link.about"/> </a></li>
          <li><a href=""><fmt:message key="link.contact"/></a></li>
        </ul>

        <div class="black-bar">
          <p>Copyright © 2022</p>
        </div>
      </footer>

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

