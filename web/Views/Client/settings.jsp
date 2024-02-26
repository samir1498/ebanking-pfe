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

    <fmt:bundle basename="i18n.Bundle"><title><fmt:message key="text.settings"/></title></fmt:bundle>

      <link rel="stylesheet" href="css/settings.css">
      <link rel="stylesheet" href="css/sidebar-style.css">
      <link rel="stylesheet" href="fontawsome/css/all.css">

      <script src="js/jquery/jquery-3.6.0.min.js"></script>

      <script src="js/jquery/jquery.validate.min.js"></script>

      <script src="js/jquery/jquery.cookie.js"></script>

    <c:if test="${langue != 'en'}">
      <script src="js/messages/messages_${langue}.js"></script>
    </c:if>

    <script defer src="js/settings/UpdateProfileValidation_${langue}.js"></script>

    <c:if test="${langue == 'ar'}">
      <style>
        *{
          direction: rtl;
        }

        .home_content {
          padding-left: 1rem;
          padding-right: 5.5rem;

        }
        .sidebar.open {
          left: auto;
          right: 0;
        }
        .logo{
          text-align: right
        }

        .sidebar.open .nav_list li a {
          text-align: right;
        }
        /*.profile_content.open .profile {
          flex-direction: row-reverse;
        }
        .profile_content.open .profile .profile_details{
          flex-direction: row-reverse;
          text-align: left;
        }*/
        #log_out{
          transform: rotateY(180deg);
        }
        .sidebar {
          left: auto;
          right: 0;
        }

        .transactions>a label.link-text:hover {
          margin-left: 1rem;
          margin-right: auto;
        }

        .transactions>a label.arrow:hover {
          margin-left: auto;
          margin-right: 1rem;
        }

        @media (max-width: 800px) {
          .sidebar {
            left: auto;
            right: 0;
          }

        }
        .profile-container {

          border-top-right-radius: 16px;
          border-bottom-right-radius: 16px;

          border-top-left-radius: 0;
          border-bottom-left-radius: 0;

          border-left: solid 1px gray;
        }

        .profile-container .black-card{
          border-top-right-radius: 16px;
          border-top-left-radius: 0;
        }

        #upload_pic{
          right: auto;
          left: 1.5rem;
        }

        button.language_settings:last-of-type{
          border-bottom-right-radius: 16px;
          border-bottom-left-radius: 0;
        }

      </style>
    </c:if>


  </head>

  <body>
    <fmt:bundle basename="i18n.Bundle">
      <div class="main">
        <%@ include file="../../includes/sidebar.jsp"%>

        <div class="popup_div"></div>

        <div class="home_content">

          <div class="settings_div">

            <div class="profile-container">
              <div class="black-card"></div>
              <div class="img_container">
                <c:if test="${client.getProfilePicturePath()!=null}"><img src="${client.getProfilePicturePath()}" alt="profile picture" class="profile_pic" style="filter:none; box-shadow:  none;object-fit: cover; object-position:0 100%;"></c:if>

                <c:if test="${client.getProfilePicturePath()==null}"><img src="images/default_profile_picture.png" alt="profile picture" class="profile_pic" style="filter:none; box-shadow:  none;object-fit: cover; object-position:0 100%;"></c:if>
                  <form method="POST" enctype="multipart/form-data" action="SetProfilePic">
                    <button type="button" name="upload" id="upload_pic">
                      <i class="fa-solid fa-camera"></i>
                    </button>
                    <input type="file" id="profile_pic_file" name="profile_pic_file" accept="image/png,image/jpeg" onchange="javascript:this.form.submit();">
                    <input type="submit" value="Submit" id="submit_profile_pic">
                  </form>
                </div>
                <h1>${client.getFullName()}</h1>
              <div class="buttons">
                <button class="language_settings" id="profile" onclick="showProfile(this)">
                  <span>
                    <i class="fa-solid fa-id-card"></i><fmt:message key="text.profile"/>
                  </span>
                </button>
                <c:if test="${langues.size()!=1 and langues.size()!=0}">
                  <button class="language_settings" id="language" onclick="showLanguage(this)">
                    <span>
                      <i class="fa-solid fa-globe"></i><fmt:message key="text.language"/>
                    </span>
                  </button>
                </c:if>
                <button class="language_settings" id="security" onclick="showSecurity(this)">
                  <span>
                    <i class="fa-solid fa-lock"></i><fmt:message key="text.security"/>
                  </span>
                </button>
              </div>
            </div>

            <div class="profile_settigns">

              <form name="profile_info" method="POST" action="EditProfile" id="profile_info">
                <h2><fmt:message key="title.h2.updateprofile"/></h2>
                <table>
                  <tbody>
                    <tr>
                      <td><fmt:message key="text.username"/></td>
                      <td>
                        <input type="text" name="username" placeholder="${client.getUsername()}" id="username" minlength="5">
                      </td>
                    </tr>
                    <tr>
                      <td><fmt:message key="text.email"/></td>
                      <td>
                        <input type="email" name="email" placeholder="${client.getEmail()}" id="email">
                      </td>
                    </tr>
                    <tr>
                      <td><fmt:message key="text.phone"/></td>
                      <td>
                        <input type="text" name="phonenumber" placeholder="${client.getPhoneNumber()}" id="phonenumber" minlength="9">
                      </td>
                    </tr>
                    <tr>
                      <td><fmt:message key="text.address"/></td>
                      <td>
                        <textarea id="address" name="address" rows="5" placeholder="${client.getAdress()}"></textarea>
                      </td>
                    </tr>
                    <tr>
                      <td></td>
                      <td><input type="submit" name="edit_profile" value="<fmt:message key="text.update"/>" class="edit_profile"></td>
                    </tr>
                  </tbody>
                </table>

              </form>
            </div>

            <div class="language_settigns" style="display: none">
              <c:if test="${langues.size()!=1 and langues.size()!=0}">

                <form action="LanguageChangerServlet" method="post" id="lang" class="language_form">
                  <h2><fmt:message key="title.h2.changeLanguage"/></h2>
                  <table>
                    <tbody>
                      <tr>
                        <td><fmt:message key="text.SelectLangue"/></td>
                        <td>
                          <select id="lang_select" name="language_list" onchange="submitform()">
                            <c:forEach  items="${langues}" var="lang">
                              <option value="${lang}"><fmt:message key="lang.${lang}"/></option>
                            </c:forEach>
                          </select>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </form>
              </c:if>
            </div>

            <div class="security_settings" style="display: none">
              <form action="ChangePassword" id="changePass" method="post">
                <h2><fmt:message key="title.h2.changepass"/></h2>
                <table>
                  <tbody>
                    <tr>
                      <td><fmt:message key="text.currentpass"/></td>
                      <td>
                        <input type="password" name="cpass" id="cpass" autofocus>
                        <input type="hidden" name="cid" id="cid" value="${client.getId()}">
                      </td>
                    </tr>
                    <tr>
                      <td><fmt:message key="text.newpass"/></td>
                      <td><input type="password" name="pass" id="pass"></td>
                    </tr>
                    <tr>
                      <td><fmt:message key="text.cnewpass"/></td>
                      <td><input type="password" name="repass" id="repass">
                      </td>
                    </tr>

                    <tr>
                      <td></td>
                      <td><input type="submit" name="edit_password" value="<fmt:message key="text.change"/>" class="edit_profile"></td>
                    </tr>
                  </tbody>
                </table>

              </form>
            </div>
          </div>
        </div>

      </div>

      <script src="js/sidebar-script.js"></script>
      <script src="js/settings/settings.js"></script>
      <script>
                            function submitform() {
                              $('#lang').submit();
                            }
                            $(document).ready(function () {
                              var langue = '${langue}';
                              $('#lang_select').val(langue);
                              $.cookie('langue', langue, {path: '/'});
                              $('#lang_select').val(langue);


                            });

      </script>
    </fmt:bundle>
  </body>
</html>
