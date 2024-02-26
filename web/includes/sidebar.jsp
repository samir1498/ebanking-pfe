<%-- 
    Document   : sidebar
    Created on : 4 juil. 2022, 18:32:32
    Author     : Samir
--%>
<%@ include file="taglibs.jsp"%>

<div class="sidebar">
  <div class="logo_content">

    <div class="logo">
      <label>
        <i class="fa-solid fa-building-columns" style="display: none"></i>
        <span> <fmt:message key="logo"/></span>
      </label>

      <i class="fa-solid fa-bars"></i>


      <i class="fa-solid fa-xmark" id="sidebar-btn"></i>
    </div>
  </div>

  <ul class="nav_list">
    <li>
      <a href="Dashboard" class="dashboard">
        <i class="fa-solid fa-gauge"></i>
        <span class="links_name"><fmt:message key="text.dashboard"/></span>
      </a>
    </li>
    <li>
      <a href="Account" class="myaccount">
        <i class="fa-solid fa-user"></i>
        <span class="links_name"><fmt:message key="text.myAccounts"/></span>
      </a>
    </li>

    <li>
      <a href="Transaction" class="payments">
        <i class="fa-solid fa-money-bill-transfer"></i>
        <span class="links_name"><fmt:message key="text.transactions"/></span>
      </a>
    </li>
    <c:if test="${cl.size()!=0}">
      <li>
        <a href="Cards" class="mycards">
          <i class="fa-solid fa-credit-card"></i>
          <span class="links_name"><fmt:message key="text.myCards"/></span>
        </a>
      </li>
    </c:if>
    <li>
      <a href="Checks" class="mychecks">
        <i class="fa-solid fa-money-check"></i>
        <span class="links_name"><fmt:message key="text.myChecks"/></span>
      </a>
    </li>

    <li>
      <a href="Bills" class="bills">
        <i class="fa-solid fa-file-invoice-dollar"></i>
        <span class="links_name"><fmt:message key="title.bills"/></span>
      </a>
    </li>

    <li>
      <a href="Settings" class="settings">
        <i class="fa-solid fa-gear"></i>
        <span class="links_name"><fmt:message key="text.settings"/></span>
      </a>
    </li>
  </ul>

</div>
<div class="profile_content">

  <div class="profile">
    <div class="profile_details">
      <c:if test="${client.getProfilePicturePath()!=null}"><img src="${client.getProfilePicturePath()}" alt="profile picture" class="profile_pic" style="filter:none; box-shadow:  none;object-fit: cover; object-position:0 100%;"></c:if>

      <c:if test="${client.getProfilePicturePath()==null}"><img src="images/default_profile_picture.png" alt="profile picture" class="profile_pic" style="filter:none; box-shadow:  none;object-fit: cover; object-position:0 100%;"></c:if>

        <div class="name_username">
          <div class="name">${client.getFullName()}</div>
        <div class="username">
          <span>
            <c:if test="${langue == 'ar'}"> ${client.getUsername()}@</c:if>
            <c:if test="${langue != 'ar'}"> @${client.getUsername()}</c:if>
          </span></div>
      </div>
    </div>

    <a href="Logout" id="log_out"><i  class="fa-solid fa-arrow-right-from-bracket"></i></a>

  </div>
</div>
<div class="popup_div"></div>


