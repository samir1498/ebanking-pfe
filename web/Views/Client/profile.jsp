<%-- 
    Document   : profile
    Created on : 9 juil. 2022, 08:36:37
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../includes/taglibs.jsp"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/profile.css">
    <link rel="stylesheet" href="css/sidebar-style.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">

    <script src="js/jquery-3.6.0.min.js"></script>

    <script src="js/jquery.validate.min.js"></script>

    <script src="js/jquery.cookie.js"></script>

    <script defer src="js/sidebar-script.js"></script>

    <title>Profile</title>
  </head>
  <body>
    <div class="main">
      <%@ include file="../../includes/sidebar.jsp"%>

      <div class="popup_div"></div>

      <div class="home_content">
        <div class="profile-container">
          <div class="black-card"></div>
          <div class="img_container">
            <c:if test="${client.getProfilePicturePath()!=null}"><img src="${client.getProfilePicturePath()}" alt="profile picture" class="profile_pic" style="filter:none; box-shadow:  none;object-fit: cover; object-position:0 100%;"></c:if>

            <c:if test="${client.getProfilePicturePath()==null}"><img src="images/default_profile_picture.png" alt="profile picture" class="profile_pic" style="filter:none; box-shadow:  none;object-fit: cover; object-position:0 100%;"></c:if>
              <button type="button" name="upload" id="upload_pic"><i class="fa-solid fa-camera"></i></button>
            </div>
            <h1>${client.getFullName()}</h1>
          <h3>@${client.getUsername()}</h3>
          <div class="contact">
            <span><i class="fa-solid fa-envelope"></i>  ${client.getEmail()}</span>
            <span><i class="fa-solid fa-phone"></i>  +${client.getPhoneNumber()}</span>
          </div>
          
          <button type="button" class="edit_profile">Edit Profile</button>

        </div>

      </div>

    </div>

  </body>
</html>
