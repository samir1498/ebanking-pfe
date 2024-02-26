<%-- 
    Document   : account
    Created on : 22 juin 2022, 14:40:52
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
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <fmt:bundle basename="i18n.Bundle">
      <title><fmt:message key="title.client"/></title></fmt:bundle>
      <link rel="stylesheet" href="css/account.css">
      <link rel="stylesheet" href="css/sidebar-style.css">
      <link rel="stylesheet" href="fontawsome/css/all.css">

      <script src="js/jquery/jquery-3.6.0.min.js"></script>

      <script src="js/jquery/jquery.validate.min.js"></script>

      <script src="js/jquery/jquery.cookie.js"></script>
    <c:if test="${langue != 'en'}">
      <script src="js/messages/messages_${langue}.js"></script>
    </c:if>

    <script defer src="js/transferValidation/AccountTransferValidation_${langue}.js"></script>

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
        .accounts>h2 {
          width: 50%;
          margin-left: auto;
          margin-right: 2rem;
        }

        .accounts input {
          padding-right: 2rem;
          padding-left: .5rem;

        }

        .accounts .fa-magnifying-glass {
          margin-left: auto;
          margin-right: .5rem;
        }
        .transafer_button{
          left: 1rem;
          right: auto;
        }
        .card-container h2 {
          margin-left: auto;
          margin-right: 2rem;
        }

        .transactions h2 {
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
      </style>
    </c:if>

  </head>

  <body>
    <fmt:bundle basename="i18n.Bundle">
      <div class="main">
        <%@ include file="../../includes/sidebar.jsp"%>

        <!-- account request div -->
        <div class="popup_div"></div>

        <div>

          <%@ include file="../../includes/RequestAccountPopup.jsp"%>
          <%@ include file="../../includes/EditAccountPopup.jsp"%>
          <%@ include file="../../includes/AccountTransferPopup.jsp"%>
          <%@ include file="../../includes/RequestCardPopup.jsp"%>
          
          <div class="delete_account_div popup" style="display: none;">
            <form action="DeleteAccount" method="post">
              <h2 style="text-align: center;"><fmt:message key="title.h2.deleteAccount"/></h2>
              <table>
                <tr>
                  <td colspan="2" style="border-radius: 12px; background: #ff9999; display: flex;
                      justify-content: center; box-shadow: 0 0 4px red; align-items: center;">
                    <i class="fa-solid fa-circle-exclamation" style="font-size:1.3rem; color: #ff3333;"></i>

                    <p style="white-space: normal; text-align: center; color: #ff1111; max-width: 35ch; padding: 0">

                      <span style="font-size: 1rem; font-weight: 900"><fmt:message key="text.warning"/></span>

                      <span style="font-size: 0.85rem; font-weight: 600">
                        <fmt:message key="text.warning.text"/>
                      </span>
                    </p>
                  </td>
                </tr>
                <tr>
                  <td><span><b><fmt:message key="text.movebalance"/></b></span></td>
                  <td><select name="accounts_lists" id="accounts_lists_account_delete" required>
                      <option value=""><fmt:message key="option.chooseAccount"/></option>
                      <c:forEach items="${client.getAccounts()}" var="account">
                        <option value="${account.getId()}">${account.getName()}</option>
                      </c:forEach>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td><input type="submit" name="" value="Request Delete">
                    <input type="hidden" id="account_to_delete" name="account_to_delete"></td>
                  <td>
                    <button type="button" onclick="colseAccountDelete()">
                      <fmt:message key="text.cancel"/>
                    </button>
                  </td>
                </tr>
              </table>
            </form>
          </div>

          <div class=" succes_transfer " style="display: none; max-width: 15rem!important">
            <i class="fa-solid fa-circle-check"></i>
            <button type="button" onclick="closeSuccesDiv()">Ok</button>
          </div>

          <div class="statement_download popup" style="display: none;">
            <form action="DownloadSatement" method="post" id="download_pdf">
              <h2 style="text-align: center;"><fmt:message key="title.accountStm"/></h2>

              <table>
                <tr>
                  <td><label for="start-date"><fmt:message key="text.start"/></label></td>
                  <td><input type="date" name="start-date" id="start-date" required></td>
                </tr>
                <tr>
                  <td><label id="end-date"><fmt:message key="text.end"/></label></td>
                  <td><input type="date" name="end-date" id="end-date" required></td>
                </tr>

                <tr>
                  <td>
                    <input type="submit" value="Download" id="download_btn">
                  <td>
                    <button type="button" class=".statement_cancel_btn" onclick="colseStatementDownload()">
                      <fmt:message key="text.cancel"/>
                    </button>
                  </td>

                </tr>
              </table>
            </form>
          </div>

        </div>

        <div class="home_content">

          <div class="accounts">
            <h2><fmt:message key="title.h2.myAccounts"/></h2>
            <div class="search">
              <i class="fa-solid fa-magnifying-glass"></i>

              <input type="text" placeholder="<fmt:message key="placeholder.search"/>" id="search_account" onkeyup="searchAccount(this.value)">
            </div>
            <div class="cards">
              <c:forEach  items="${client.getAccounts()}" var="account">

                <div class="account <c:if test='${account.getAccountType()=="checking"}'>checking</c:if>" id="${account.getId()}">
                    <table>
                      <tr>
                        <td>
                          <h3 class="account_name">
                          <c:if test="${account.getAccountType()=='saving'}">
                            <i class="fa-solid fa-sack-dollar" title="Savings Account"></i>
                          </c:if> 
                          <c:if test="${account.getAccountType()=='checking'}">
                            <i class="fa-solid fa-credit-card" title="Checking Account"></i>
                          </c:if>${account.getName()}</h3>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <h4 class="account_number" title="RIB:click to copy">
                          <c:if test="${account.getRIB()!=null}">
                            ${account.getRIB()}
                          </c:if>
                          <c:if test="${account.getRIB()==null}">${account.getId()}</c:if>
                          <c:if test="${account.getAccountState()=='blocked'}">
                            <i class="fa-solid fa-circle-xmark" title="Frozen Account"></i>
                          </c:if>
                          <c:if test="${account.getAccountState()=='active'}">
                            <i class="fa-solid fa-circle-check" title="Active Account"></i>
                          </c:if>
                        </h4>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <h2 class="account_balance">${account.getBalance().setScale(2, RoundingMode.FLOOR)} ${account.getCurrency().getSymbol()} </h2>
                      </td>
                    </tr>
                  </table>

                  <button type="button" class="account_menu_btn"><i class="fa-solid fa-ellipsis-vertical"></i>
                    <div class="account_menu" id="${account.getId()}">
                      <ul>
                        <c:if test="${client.getAccounts().size() > 1}">
                          <li class=".delete_account_btn" onclick="openAccountDelete(${account.getId()})">Delete</li>
                          </c:if>
                        <li 
                          onclick="openAccountEdit(${account.getId()},
                                      '${account.getName()}',
                                      '${account.getAccountType()}',
                                      '${account.getCurrency().getCode()}'
                          <c:if test="${account.getAccountType()=='saving'}">, '${account.getSavingGoal()}'</c:if>
                          <c:if test="${account.getAccountType()=='checking'}">, '${account.getBudget()}', '${account.getPeriod()}'</c:if>)">Edit</li>
                        </ul>
                      </div></button>
                    <button type="button" class="transafer_button" title="Transfer" onclick="openTransferDiv('${account.getRIB()}')"><i class="fa-solid fa-money-bill-transfer"></i></button>
                </div>

              </c:forEach>

              <div class="add_account" title="add account" id="add">
                <table>
                  <tr>
                    <td>
                      <h3><i class="fa-solid fa-plus"></i></h3>
                    </td>

                </table>
              </div>

            </div>
            <c:if test="${langue != 'ar'}">
              <button type="button" id="accounts_scroll" onclick="cardsScrollRight()">
                <i class="fa-solid fa-angle-right"></i>
              </button>
              <button type="button" id="accounts_scroll_left" onclick="cardsScrollLeft()">
                <i class="fa-solid fa-angle-left"></i>
              </button>
            </c:if>
            <c:if test="${langue == 'ar'}">
              <button type="button" id="accounts_scroll" onclick="cardsScrollLeft()">
                <i class="fa-solid fa-angle-right"></i>
              </button>
              <button type="button" id="accounts_scroll_left" onclick="cardsScrollRightAR()">
                <i class="fa-solid fa-angle-left"></i>
              </button>
            </c:if>


          </div>

          <div class="transactions">
            <h2><fmt:message key="title.h2.latestTransactions"/></h2>


            <div id="table-scroll" class="table-scroll">

              <table id="main-table" class="main-table">

                <tbody>
                  <c:forEach  items="${client.getAccounts()}" var="account" >
                    <c:forEach  items="${account.getTransactions()}" var="transaction" end="6">
                      <tr class="${account.getId()}">
                        <!--<!-- date -->
                        <td>
                          <h4>${transaction.getDate()}</h4>
                        </td>
                        <!-- client from -->
                        <td>
                          <c:choose>
                            <c:when test="${account.getId()== transaction.getAccountFrom().getId() and transaction.getClientTo().getProfilePicturePath()!=null}">
                              <img src="${transaction.getClientTo().getProfilePicturePath()}" width="40" height="40" alt="" style="border-radius: 50%; object-fit: cover; object-position:0 100%;">
                            </c:when>

                            <c:when test="${account.getId()== transaction.getAccountTo().getId() and transaction.getClientFrom().getProfilePicturePath()!=null}">
                              <img src="${transaction.getClientFrom().getProfilePicturePath()}" width="40" height="40" alt="" style="border-radius: 50%; object-fit: cover; object-position:0 100%;">
                            </c:when>

                            <c:otherwise>
                              <img src="images/default_profile_picture.png" width="40" alt="" style="border-radius: 50%;">
                            </c:otherwise>
                          </c:choose>
                        </td>

                        <td>
                          <span style="font-weight: bolder">
                            <c:if test="${transaction.getClientFrom().getId()==0}">
                              <c:out value="${transaction.getClienttToName()}"/>
                            </c:if>
                            <c:if test="${transaction.getClientFrom().getId()!=0}">
                              <c:if test="${transaction.getClientTo().getId()== transaction.getClientFrom().getId()}">
                                <c:out value="You"/>
                              </c:if>
                              <c:if test="${transaction.getClientTo().getId()!= transaction.getClientFrom().getId()}">
                                <c:out value="${transaction.getClientFrom().getFullName()}"/>
                              </c:if>
                            </c:if>
                          </span>
                        </td>
                        <!--Account From RIB -->
                        <td>
                          <c:choose>
                            <c:when test="${account.getId()== transaction.getAccountFrom().getId()}">
                              <c:out value="${transaction.getAccountTo().getRIB()}"/>
                            </c:when>

                            <c:when test="${account.getId()== transaction.getAccountTo().getId()}">
                              <c:out value="${transaction.getAccountFrom().getRIB()}"/>
                            </c:when>

                          </c:choose>
                        </td>
                        <td 
                          <c:if test="${account.getId()== transaction.getAccountFrom().getId()}">
                            style="color: red; font-weight:bold"
                          </c:if>
                          <c:if test="${account.getId()== transaction.getAccountTo().getId()}">
                            style="color: green; font-weight:bold"
                          </c:if>>
                          <c:choose>
                            <c:when test="${account.getId()== transaction.getAccountFrom().getId()}">
                              <c:out value="${transaction.getAmount().setScale(2, RoundingMode.FLOOR)} ${account.getCurrency().getSymbol()}"/>
                            </c:when>

                            <c:when test="${account.getId()== transaction.getAccountTo().getId()}">
                              <c:out value="${transaction.getConvertedAmount().setScale(2, RoundingMode.FLOOR)} ${account.getCurrency().getSymbol()}"/>
                            </c:when>

                          </c:choose>


                        </td>
                      </tr>
                    </c:forEach>
                  </c:forEach>

                </tbody>


              </table>

            </div>

            <a href="Transaction"><label class="link-text"><fmt:message key="text.seeAll"/> </label>
              <label class="arrow">

                <c:if test="${langue != 'ar'}"><i class="fa-solid fa-arrow-right"></i></c:if>
                <c:if test="${langue == 'ar'}"><i class="fa-solid fa-arrow-left"></i></c:if>
                </label></a>

              <button type="button" class="account_menu_btn"><i class="fa-solid fa-ellipsis-vertical"></i>
                <div class="account_menu">
                  <ul>
                    <li class="statement_download_btn" style="white-space: nowrap;">Download Account Statement</li>
                  </ul>
                </div></button>
            </div>

            <div class="card-container">
              <h2><fmt:message key="title.linkedCards"/></h2>
            <div class="cards_container">
              <c:forEach  items="${client.getAccounts()}" var="account" >
                <c:forEach  items="${account.getCards()}" var="card" varStatus="i">  

                  <div class="card_div ${card.getIdAccount()}">
                    <div class="front">

                      <img src="images/map.png" class="map-img">

                      <div class="row">
                        <img src="images/chip.png" width="45rem">
                        <div class="logo">
                          <h2><i class="fa-solid fa-building-columns"></i> Bank</h2>
                        </div>
                      </div>
                      <div class="row card-no">
                        <p>${fn:substring(card.getCardNumber(), 0, 4)}</p>
                        <p>${fn:substring(card.getCardNumber(), 4, 8)}</p>
                        <p>${fn:substring(card.getCardNumber(), 8, 12)}</p>
                        <p>${fn:substring(card.getCardNumber(), 12, 16)}</p>
                      </div>
                      <div class="row card-holder">
                        <p><fmt:message key="text.cardHolder"/></p>
                        <p><fmt:message key="text.valid"/></p>
                      </div>
                      <div class="row name">
                        <p>${client.getFullName()}</p>
                        <p>${card.getExpirationDate()}</p>
                      </div>
                    </div>

                    <div class="back">
                      <img src="images/map.png" class="map-img">
                      <div class="bar"></div>
                      <div class="row card-cvv">
                        <div>
                          <img src="images/pattern.png" alt="">
                        </div>
                        <p title="CVV">${card.getCVV()}</p>
                      </div>

                      <div class="row card-text">
                        <p></p>
                      </div>
                      <div class="row signature">
                        <p>CUSTOMER SIGNATURE</p>
                      </div>
                    </div>
                  </div>

                </c:forEach>
              </c:forEach>
              <div class="empty_card_div" title="Link a card " onclick="openRequestCard()">
                <table>
                  <tr>
                    <td>
                      <h3><i class="fa-solid fa-plus "></i></h3>
                    </td>

                </table>
              </div>

            </div>

          </div>
        </div>

      </div>
      <script>

        $(document).ready(function () {
          var langue = '${langue}';
          $.cookie('langue', langue, {path: '/'});
          $('#lang_select').val(langue);
        });

      </script>
      <script src="js/account.js"></script>
      <script defer src="js/sidebar-script.js"></script>


    </fmt:bundle>
  </body>

</html>
