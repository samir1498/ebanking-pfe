<%-- 
    Document   : cards
    Created on : 5 juil. 2022, 11:55:57
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../includes/taglibs.jsp"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <fmt:bundle basename="i18n.Bundle"><title>Notifications</title></fmt:bundle>

      <link rel="stylesheet" href="css/notifications.css">
      <link rel="stylesheet" href="css/sidebar-style.css">
      <link rel="stylesheet" href="fontawsome/css/all.css">

      <script src="js/jquery/jquery-3.6.0.min.js"></script>

      <script src="js/jquery/jquery.validate.min.js"></script>

      <script src="js/jquery/jquery.cookie.js"></script>

    </head>

    <body>
    <fmt:bundle basename="i18n.Bundle">
      <div class="main">

        <%@ include file="../../includes/sidebar.jsp"%>
        <!-- account request div -->
        <div class="popup_div"></div>

        <div class="home_content">
          <div class="notifications_div">
            <h2>Notifications </h2><i class="fa-solid fa-bell"></i>
            <ul>
              <c:forEach begin="0" end="10" step="1">

                <li><i class="fa-solid fa-bell"></i><p>details details details</p><strong>2022-07-26</strong></li>
                </c:forEach>


            </ul>

          </div>
        </div>
      </div>     
      <script src="js/sidebar-script.js"></script>
      <script src="js/notifications.js"></script>      
    </fmt:bundle>
  </body>
</html>
