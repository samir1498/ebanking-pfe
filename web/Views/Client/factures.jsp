<%-- 
    Document   : factures
    Created on : 13 août 2022, 10:56:41
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../includes/taglibs.jsp"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <fmt:bundle basename="i18n.Bundle"><title><fmt:message key="title.bills"/></title></fmt:bundle>

      <link rel="stylesheet" href="css/factures.css">
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
      <script src="js/dataTables/html2canvas.js"></script>

      <script defer src="js/transferValidation/BillTransferValidation_${langue}.js"></script>
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
        <!-- account request div -->
        <div class="popup_div"></div>

        <div class="succes_transfer popup" style="display: none">
          <i class="fa-solid fa-circle-check"></i>
          <button type="button" onclick="closeSuccesDiv()"><fmt:message key="text.ok"/></button>
        </div>
        <div class="newBill popup">
          <h2 style="text-align: center"><fmt:message key="title.h2.paybill"/></h2>
          <form action="PayBill" method="post" id="BillFrom">
            <table class="newLoanTable">
              <tbody>
                <tr>
                  <td><fmt:message key="th.payfrom"/></td>
                  <td><select id="accounts_create_check" name="PayFrom" required>
                      <c:forEach items="${client.getAccounts()}" var="account">
                        <option value="${account.getId()}">${account.getName()}</option>
                      </c:forEach>
                    </select>
                  </td>
                </tr>

                <tr>
                  <td><fmt:message key="th.billername"/></td>
                  <td>
                    <input type="text" name="BillerName" required>
                  </td>
                </tr>

                <tr>
                  <td><fmt:message key="th.billeraccount"/></td>
                  <td>
                    <input type="text" name="BillerAccount" id="BillerAccount" required>
                  </td>
                </tr>

                <tr>
                  <td><fmt:message key="text.amount"/></td>
                  <td><input type="Number" min="0" step="0.01" name ="Amount" required></td>
                </tr>
                <tr>
                  <td><fmt:message key="text.description"/></td>
                  <td>
                    <textarea id="description" name="description" rows="5" required></textarea>
                    <input type="hidden" name="RIBorIBAN" id="RIBorIBAN">
                  </td>
                </tr>

                <tr>
                  <td><input type="submit" value="<fmt:message key="text.pay"/>"></td>
                  <td><button type="button" id="cancel" onclick="closeNewBillPopup()"><fmt:message key="text.cancel"/></button></td>
                </tr>

              </tbody>
            </table>

          </form>
        </div>
        <div class="home_content">


          <div class="Bill">
            <h2><fmt:message key="title.bills"/></h2>
            <div class="filter">

              <div id="date_filter">
                <div>
                  <span id="date-label-from" class="date-label">
                    <fmt:message key="text.from"/>
                  </span>
                  <input class="date_range_filter date" type="text" id="datepicker_from" /></div>

                <div>                
                  <span id="date-label-to" class="date-label">
                    <fmt:message key="text.to"/>
                  </span>
                  <input class="date_range_filter date" type="text" id="datepicker_to" /></div>

              </div>
              <div class="search">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" placeholder="<fmt:message key="placeholder.search"/>"
                       id="search_account">
              </div>


            </div>
            <div id="table-scroll" class="table-scroll">
              <table id="main-table" class="main-table compact stripe order-column responsive">
                <thead>
                  <tr>
                    <th id="th1"><fmt:message key="th.date"/></th>
                    <th id="th2"><fmt:message key="th.billername"/></th>
                    <th id="th3"><fmt:message key="th.payedfrom"/></th>
                    <th id="th4"><fmt:message key="th.billeraccount"/></th>
                    <th id="th5"><fmt:message key="text.amount"/></th>
                    <th id="th6"><fmt:message key="text.description"/></th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items ="${Bills}" var="bill">
                    <tr>
                      <td headers="th1">${bill.getBillDate()}</td>
                      <td headers="th2">${bill.getBillerName()}</td>
                      <td headers="th3">${bill.getPayFrom()}</td>
                      <td headers="th4">${bill.getBillerAccountNumber()}</td>
                      <td headers="th5">${bill.getAmount()} ${bill.getCurrency()} </td>
                      <td headers="th6">${bill.getDescription()}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>     
      <script src="js/sidebar-script.js"></script>
      <script src="js/bills.js">         
      </script>
    </fmt:bundle>

  </body>
</html>