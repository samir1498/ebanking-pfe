<%-- 
    Document   : cards
    Created on : 5 juil. 2022, 11:55:57
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../includes/taglibs.jsp"%>
<%@page import="java.math.RoundingMode"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <fmt:bundle basename="i18n.Bundle"><title>Cards</title></fmt:bundle>

      <link rel="stylesheet" href="css/cards.css">
      <link rel="stylesheet" href="css/sidebar-style.css">
      <link rel="stylesheet" href="fontawsome/css/all.css">

      <script src="js/jquery/jquery-3.6.0.min.js"></script>

      <script src="js/jquery/jquery.validate.min.js"></script>

      <script src="js/jquery/jquery.cookie.js"></script>
      <script defer src="js/transferValidation/CardTransferValidation_${langue}.js"></script>

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
        .sidebar .logo{
          text-align: right
        }

        .sidebar.open .nav_list li a {
          text-align: right;
        }
        #log_out{
          transform: rotateY(180deg);
        }
        .sidebar {
          left: auto;
          right: 0;
        }


        @media (max-width: 800px) {
          .sidebar {
            left: auto;
            right: 0;
          }

        }
        .background_div{
          top: 0;
          left: auto;
          right: 0;
          border-top-left-radius: 80px;
          border-top-right-radius: 0;
        }
        .transafer_button{
          left: 1rem;
          right: auto;
        }
        .card-container h2 {
          margin-left: auto;
          margin-right: 2rem;
        }

        .account_menu_btn {
          right: auto;
          left: 0;
        }

        .account_menu {
          right: auto;
          left: .5rem;
        }

        .row:first-of-type{
          display: flex;
          flex-direction: row-reverse;
          align-items: center;
          justify-content: space-between;
        }

        .front div.logo h2{
          margin: 0
        }
      </style>
    </c:if>
  </head>

  <body>
    <fmt:bundle basename="i18n.Bundle">
      <div class="main">
        <%@ include file="../../includes/sidebar.jsp"%>
        <!-- account request div -->
        <div class="popup_div"></div>

        <!-- transfer popup -->
        <%@ include file="../../includes/CardTransferPopup.jsp"%>
        <%@ include file="../../includes/EditCardPINPopup.jsp"%>


        <div class="succes_transfer popup" style="display: none">
          <i class="fa-solid fa-circle-check"></i>
          <button type="button" onclick="closeSuccesDiv()"><fmt:message key="text.ok"/></button>
        </div>

        <div class="home_content">

          <div class="cards_management">

            <div class="cards_list">

              <div class="card-container">
                <div class="background_div"></div>
                <h2><fmt:message key="text.myCards"/></h2>

                <div class="active_card">
                  <c:forEach  items="${client.getAccounts()}" var="account" >
                    <c:forEach  items="${account.getCards()}" var="card" varStatus="i">  

                      <div class="card_div ${card.getIdAccount()}" id="${card.getCardNumber()}"  style="display: none;">
                        <div class="front">

                          <img src="images/map.png" class="map-img">

                          <div class="row">
                            <img src="images/chip.png" width="35" class="chip">
                            <div class="logo">
                              <h2>
                                <label>
                                  <i class="fa-solid fa-building-columns"></i>
                                  <span> <fmt:message key="logo"/></span>
                                </label>
                              </h2>
                            </div>
                          </div>
                          <div class="row card-no">
                            <p>${fn:substring(card.getCardNumber(), 0, 4)}</p>
                            <p>${fn:substring(card.getCardNumber(), 4, 8)}</p>
                            <p>${fn:substring(card.getCardNumber(), 8, 12)}</p>
                            <p>${fn:substring(card.getCardNumber(), 12, 16)}</p>
                          </div>

                          <div class="row card-holder">
                            <p><fmt:message key="text.balance"/></p>
                          </div>
                          <div class="row balance">
                            <p>${account.getBalance().setScale(2, RoundingMode.FLOOR)}${account.getCurrency().getSymbol()}</p>
                          </div>
                          <button type="button" class="transafer_button" title="Transfer" onclick="openTransferDiv(${account.getId()}, ${card.getCardNumber()})">
                            <i class="fa-solid fa-money-bill-transfer"></i>
                          </button>
                        </div>


                      </div>

                    </c:forEach>
                  </c:forEach>

                </div>

                <div class="cards_container">
                  <c:forEach  items="${client.getAccounts()}" var="account" >
                    <c:forEach  items="${account.getCards()}" var="card" varStatus="i">  

                      <div class="card_div ${card.getIdAccount()}" id="${card.getCardNumber()}"  style="display: none;">
                        <div class="front">

                          <img src="images/map.png" class="map-img">

                          <div class="row">
                            <img src="images/chip.png" width="35" class="chip">
                            <div class="logo">
                              <h2>
                                <label>
                                  <i class="fa-solid fa-building-columns"></i>
                                  <span> <fmt:message key="logo"/></span>
                                </label>
                              </h2>
                            </div>
                          </div>
                          <div class="row card-no">
                            <p>${fn:substring(card.getCardNumber(), 0, 4)}</p>
                            <p>${fn:substring(card.getCardNumber(), 4, 8)}</p>
                            <p>${fn:substring(card.getCardNumber(), 8, 12)}</p>
                            <p>${fn:substring(card.getCardNumber(), 12, 16)}</p>
                          </div>
                          <div class="row card-holder">
                            <p><fmt:message key="text.balance"/></p>
                          </div>
                          <div class="row balance">
                            <p>${account.getBalance().setScale(2, RoundingMode.FLOOR)}${account.getCurrency().getSymbol()}</p>
                          </div>
                        </div>

                      </div>

                    </c:forEach>
                  </c:forEach>

                </div>

              </div>
            </div>

            <div class="card_infos">
              <h2><fmt:message key="text.cardinfo"/></h2>
              <c:forEach  items="${client.getAccounts()}" var="account" >
                <c:forEach  items="${account.getCards()}" var="card" varStatus="i">
                  <div class="table_info_container ${card.getIdAccount()}" id="${card.getCardNumber()}"  style="display: none;">
                    <button type="button" class="account_menu_btn"><i class="fa-solid fa-ellipsis-vertical"></i>
                      <div class="account_menu">
                        <ul>
                          <li onclick="OpenEditPIN('${card.getCardNumber()}')"><fmt:message key="text.Edit"/></li>
                        </ul>
                      </div>
                    </button>
                    <table>
                      <tbody>

                        <tr>
                          <td><fmt:message key="text.account"/></td>
                          <td>${account.getName()}</td>
                        </tr>
                        <tr>
                          <td>CVV</td>
                          <td>${card.getCVV()}</td>
                        </tr>
                        <tr>
                          <td><fmt:message key="text.expiration"/></td>
                          <td>${card.getExpirationDate()}</td>
                        </tr>
                        <tr>
                          <td><fmt:message key="text.state"/></td>
                          <td><fmt:message key="text.${card.getCardState()}"/></td>
                        </tr>

                      </tbody>

                    </table>

                  </div>
                </c:forEach>

              </c:forEach>
            </div>

            <div class="card_history">
              <h2><fmt:message key="title.h2.history"/></h2>
              <div id="table-scroll" class="table-scroll">

                <table id="main-table" class="main-table">

                  <tbody>
                    <c:forEach  items="${client.getAccounts()}" var="account" >
                      <c:forEach  items="${account.getCards()}" var="card" end="10">
                        <c:forEach  items="${card.getTransactions()}" var="transaction">
                          
                          <tr class="${card.getCardNumber()}">
                            <td>
                              <h4>${transaction.getDate()}</h4>
                            </td>

                            <td>

                              <!-- client to -->
                            <td>
                              <c:choose>
                                <c:when test="${transaction.getClientTo().getProfilePicturePath()!=null}">
                                  <label> 
                                    <img src="${transaction.getClientTo().getProfilePicturePath()}">
                                    <span>

                                      <c:if test="${transaction.getClientTo().getId()==0}">
                                        <c:out value="${transaction.getClienttToName()}"/>
                                      </c:if>
                                      <c:if test="${transaction.getClientTo().getId()!=0}">
                                        <c:if test="${transaction.getClientTo().getId()==client.getId()}">
                                          <c:out value="You"/>
                                        </c:if>
                                        <c:if test="${transaction.getClientTo().getId()!=client.getId()}">
                                          <c:out value="${transaction.getClientTo().getFullName()}"/>
                                        </c:if>

                                      </c:if>    

                                    </span>
                                  </label>

                                </c:when>

                                <c:otherwise>
                                  <label>                            
                                    <img src="images/default_profile_picture.png" width="40" alt="" style="border-radius: 50%;">
                                    <span>
                                      <c:if test="${transaction.getClientTo().getId()==0}">
                                        <c:out value="${transaction.getClienttToName()}"/>
                                      </c:if>
                                      <c:if test="${transaction.getClientTo().getId()!=0}">
                                        <c:if test="${transaction.getClientTo().getId()==transaction.getClientFrom().getId()}">
                                          <c:out value="You"/>
                                        </c:if>
                                        <c:if test="${transaction.getClientTo().getId()!=transaction.getClientFrom().getId()}">
                                          <c:out value="${transaction.getClientTo().getFullName()}"/>
                                        </c:if>

                                      </c:if>                             
                                    </span>
                                  </label>

                                </c:otherwise>
                              </c:choose>
                            </td>
                            <td>
                              <c:if test="${transaction.getAccountTo().getRIB()!=null}">${transaction.getAccountTo().getRIB()}</c:if>
                              <c:if test="${transaction.getAccountTo().getRIB()==null}">

                                <c:if test="${transaction.getAccountTo().getId()==0}">
                                  <c:out value="${transaction.getAccountToRIB()}"/>
                                </c:if>
                                <c:if test="${transaction.getAccountTo().getId()!=0}">
                                  <c:out value="${transaction.getAccountTo().getId()}"/>
                                </c:if>
                              </c:if>

                            </td>
                            <td 
                              <c:if test="${account.getId()== transaction.getAccountFrom().getId()}">style="color: red; font-weight:bold"</c:if>
                              <c:if test="${account.getId()== transaction.getAccountTo().getId()}"> style="color: green; font-weight:bold"</c:if>>
                              ${transaction.getAmount()}${account.getCurrency().getSymbol()}
                            </td>
                          </tr>
                        </c:forEach>
                      </c:forEach>
                    </c:forEach>
                  </tbody>


                </table>

              </div>

            </div>
          </div>
        </div>
      </div>  
    </fmt:bundle>
    <script src="js/sidebar-script.js"></script>
    <script src="js/cards.js"></script>

  </body>
</html>
