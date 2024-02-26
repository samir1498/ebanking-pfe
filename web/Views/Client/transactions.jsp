<%-- 
    Document   : transactions
    Created on : 4 juil. 2022, 17:55:05
    Author     : Samir
--%>

<%@page import="com.transaction.bean.Transaction"%>
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

    <fmt:bundle basename="i18n.Bundle"><title><fmt:message key="title.transactions"/></title></fmt:bundle>

      <link rel="stylesheet" href="css/transactions.css">
      <link rel="stylesheet" href="css/sidebar-style.css">
      <link rel="stylesheet" href="fontawsome/css/all.css">
      <link rel="stylesheet" href="css/buttons.dataTables.min.css" />

      <link rel="stylesheet" href="css/responsive.dataTables.min.css">
      <link rel="stylesheet" href="css/jquery.dataTables.min.css">
      <link rel="stylesheet" href="css/dataTables.dateTime.min.css">
      <link rel="stylesheet" href="css/searchBuilder.dataTables.min.css">


      <script src="js/jquery/jquery-3.6.0.min.js"></script>

      <script src="js/jquery/jquery.validate.min.js"></script>

      <script src="js/jquery/jquery.cookie.js"></script>

      <script src="js/jquery/jquery.dataTables.min.js"></script>
      <script src="js/dataTables/dataTables.dateTime.min.js"></script>
      <script src="js/dataTables/dataTables.searchBuilder.min.js"></script>

      <link  rel="stylesheet" href="css/jquery-ui-1.10.0.custom.min.css" />
      <script src="js/jquery/jquery-ui.js"></script>

      <script src="js/dataTables/dataTables.buttons.min.js"></script>
      <script src="js/dataTables/jszip.min.js"></script>
      <script src="js/dataTables/pdfmake.min.js"></script>
      <script src="js/dataTables/vfs_fonts.js"></script>

      <script src="js/dataTables/buttons.html5.min.js"></script>
      <script src="js/dataTables/html2canvas.js" 
      ></script>
    <c:if test="${langue == 'ar'}">
      <style>
        *{
          direction: rtl;
        }

        .home_content {
          padding-left: .5rem;
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

        input#search_account {
          padding-right: 2rem;
          padding-left: .5rem;

        }

        .fa-magnifying-glass {
          margin-left: auto;
          margin-right: .5rem;
        }

        .transactions h2 {
          margin-left: auto;
          margin-right: 2rem;
        }

      </style>
    </c:if>

  </head>

  <body>
    <fmt:bundle basename="i18n.Bundle">
      <div class="main">

        <%@ include file="../../includes/sidebar.jsp"%>
        <div class="transaction_details popup">
          <c:forEach  items="${client_transactions}" var="transaction" >
            <form action="action" class="${transaction.getId()}" style="display:none">
              <table>

                <tbody>
                  <tr>
                    <td><fmt:message key="th.date"/></td>
                    <td>${transaction.getDate()}</td>
                  </tr>
                  <tr>
                    <td><fmt:message key="th.from"/></td>
                    <td><c:out value="${transaction.getClientFrom().getFullName()}"/></td>
                  </tr>
                  <tr>
                    <td><fmt:message key="th.to"/></td>
                    <td><c:out value="${transaction.getClientTo().getFullName()}"/></td>
                  </tr>
                  <tr>
                    <td><fmt:message key="th.accountFrom"/></td>
                    <td>
                      <c:if test="${transaction.getAccountFrom().getRIB()!=null}">${transaction.getAccountFrom().getRIB()}</c:if>
                      <c:if test="${transaction.getAccountFrom().getRIB()==null}">${transaction.getAccountFrom().getId()}</c:if>
                      </td>
                    </tr>
                    <tr>
                      <td><fmt:message key="th.accountTo"/></td>
                      <td>
                      <c:if test="${transaction.getAccountTo().getRIB()!=null}">${transaction.getAccountTo().getRIB()}</c:if>
                      <c:if test="${transaction.getAccountTo().getRIB()==null}">${transaction.getAccountTo().getId()}</c:if>
                      </td>
                    </tr>
                    <tr>
                      <td><fmt:message key="th.state"/></td>
                      <td>
                      ${transaction.getState()}
                    </td>
                  </tr>
                  <tr>
                    <td><fmt:message key="th.amount"/></td>
                      <c:choose>
                        <c:when test="${client.getId()== transaction.getClientFrom().getId()}"> 
                        <td style="color: red; font-weight:bold; width:100%">
                          ${transaction.getAmount()}${transaction.getAccountFrom().getCurrency().getSymbol()}
                        </td>
                      </c:when>
                      <c:when test="${client.getId()== transaction.getClientTo().getId()}">

                        <td style="color: green; font-weight:bold; width:100%">
                          ${transaction.getAmount()}${transaction.getAccountFrom().getCurrency().getSymbol()}
                        </td>
                      </c:when>
                    </c:choose> 
                  </tr> 
                  <tr>
                    <td><fmt:message key="text.description"/></td>
                    <td class="description">
                      ${transaction.getDescription()}
                    </td>
                  </tr>
                </tbody>
              </table>

            </form>
          </c:forEach>
          <button class="close_btn" onclick="closeTransactionsDetails()">x</button>
        </div>

        <div class="home_content">

          <div class="transactions">
            <h2><fmt:message key="title.h2.transactions"/></h2>
            <div class="filter">

              <p id="date_filter">
                <span id="date-label-from" class="date-label">
                  <fmt:message key="text.from"/>
                </span>
                <input class="date_range_filter date" type="text" id="datepicker_from" />
                <span id="date-label-to" class="date-label">
                  <fmt:message key="text.to"/>
                </span>
                <input class="date_range_filter date" type="text" id="datepicker_to" />
              </p>
              <div class="search">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" placeholder="<fmt:message key="placeholder.search"/>" id="search_account">
              </div>


            </div>
            <div id="table-scroll" class="table-scroll">

              <table id="main-table" class="main-table compact stripe order-column responsive">
                <thead>
                  <tr>
                    <th id="th1"><fmt:message key="th.date"/></th>
                    <th id="th2"><fmt:message key="th.from"/></th>
                    <th id="th3"><fmt:message key="th.to"/></th>
                    <th id="th4"><fmt:message key="th.accountFrom"/></th>
                    <th id="th5"><fmt:message key="th.accountTo"/></th>
                    <th id="th6"><fmt:message key="text.type"/></th>
                    <th id="th7"><fmt:message key="th.state"/></th>
                    <th id="th8"><fmt:message key="th.amount"/></th>
                    <th id="th9"></th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach  items="${client_transactions}" var="transaction" >

                    <tr>
                      <!-- date -->
                      <td headers="th1" id="date_col">
                        <h4>${transaction.getDate()}</h4>
                      </td>
                      <!-- client from -->
                      <td headers="th2"  class="client_to">
                        <c:choose>
                          <c:when test="${transaction.getClientFrom().getProfilePicturePath()!=null}">
                            <label>
                              <img src="${transaction.getClientFrom().getProfilePicturePath()}">
                              <span>                         

                                <c:if test="${transaction.getClientFrom().getId()== client.getId()}">
                                  <c:out value="You"/>
                                </c:if>
                                <c:if test="${transaction.getClientFrom().getId()!= client.getId()}">
                                  <c:out value="${transaction.getClientFrom().getFullName()}"/>
                                </c:if>
                              </span>
                            </label>

                          </c:when>

                          <c:otherwise>
                            <label>  
                              <img src="images/default_profile_picture.png">
                                                            <span>                         

                                <c:if test="${transaction.getClientFrom().getId()== client.getId()}">
                                  <c:out value="You"/>
                                </c:if>
                                <c:if test="${transaction.getClientFrom().getId()!= client.getId()}">
                                  <c:out value="${transaction.getClientFrom().getFullName()}"/>
                                </c:if>
                              </span>

                          </c:otherwise>
                        </c:choose>
                      </td>
                      <!-- client to -->
                      <td headers="th3" align="center" class="client_to">
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
                      <!-- account from -->
                      <td headers="th4">
                        <c:if test="${transaction.getAccountFrom().getRIB()!=null}">${transaction.getAccountFrom().getRIB()}</c:if>
                        <c:if test="${transaction.getAccountFrom().getRIB()==null}">${transaction.getAccountFrom().getId()}</c:if>
                        </td>
                        <!-- account to -->
                        <td headers="th5">
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
                       <!-- Type -->
                       <td>${transaction.getType()}</td>
                      <!-- State -->
                      <c:choose>
                        <c:when test="${transaction.getState()=='Completed'}">
                          <td headers="th6"><i class="fa-solid fa-circle-check" style="color: green"></i></td>
                          </c:when>
                          <c:when test="${transaction.getState()=='Pending'}">
                          <td headers="th6"><i class="fa-solid fa-clock" style="color: orange"></i></td>
                          </c:when>
                          <c:when test="${transaction.getState()=='Incomplete'}">
                          <td headers="th6"><i class="fa-solid fa-circle-xmark" style="color: red"></i></td>
                          </c:when>
                        </c:choose>

                      <!-- Amount -->
                      <c:choose>

                        <c:when test="${client.getId()== transaction.getClientTo().getId()&& client.getId()== transaction.getClientFrom().getId()}">
                          <td headers="th7" style="color: black; font-weight:bold">

                            ${transaction.getAmount()} ${transaction.getAccountFrom().getCurrency().getSymbol()}
                          </td>
                        </c:when>
                        <c:when test="${client.getId()== transaction.getClientFrom().getId()}"> 
                          <td headers="th7" style="color: red; font-weight:bold">
                            ${transaction.getAmount()} ${transaction.getAccountFrom().getCurrency().getSymbol()}

                          </td>
                        </c:when>
                        <c:when test="${client.getId()== transaction.getClientTo().getId()}">
                          <td headers="th7" style="color: green; font-weight:bold">

                            ${transaction.getAmount()} ${transaction.getAccountFrom().getCurrency().getSymbol()}
                          </td>
                        </c:when>

                      </c:choose> 
                      <!-- Options -->
                      <td onclick="openTransactionsDetails(${transaction.getId()})"><i class="fa-solid fa-ellipsis"></i></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>

            </div>
          </div>
        </div>
      </div>     
      <script src="js/transactions.js"></script>
      <script src="js/sidebar-script.js"></script>
    </fmt:bundle>
  </body>

</html>
