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

    <fmt:bundle basename="i18n.Bundle"><title>Loan</title></fmt:bundle>

      <link rel="stylesheet" href="css/loan.css">
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


    </head>

    <body>
    <fmt:bundle basename="i18n.Bundle">
      <div class="main">

        <%@ include file="../../includes/sidebar.jsp"%>
        <!-- account request div -->
        <div class="popup_div"></div>

        <div class="home_content">
          <div class="newLoan">
            <h2>Apply for loan</h2>
            <form action="action">
              <table class="newLoanTable">
                <tbody>
                  <tr>
                    <td>Account</td>
                    <td><select id="account">
                        <option value="first">text1</option>
                        <option value="second">text2</option>
                        <option value="third">text3</option>
                      </select>
                    </td>
                  </tr>

                  <tr>
                    <td>Amount</td>
                    <td><input type="Number" min="0" step="0.01"></td>
                  </tr>

                  <tr>
                    <td>Loan term</td>
                    <td>
                      <select id="term">
                        <option value="first">6 months</option>
                        <option value="second">12 months</option>
                        <option value="third">18 months</option>
                        <option value="third">2 years</option>
                      </select>
                    </td>
                  </tr>

                  <tr>
                    <td></td>
                    <td><input type="submit" value="Apply"></td>
                  </tr>

                </tbody>
              </table>

            </form>
          </div>
          <div class="ActiveLoans">
            <h2>Active Loan</h2>
            <form action="action">
              <table class="ActiveLoansTable compact stripe order-column responsive" >
                <thead>
                  <tr>
                    <th>type</th>
                    <th>Amount</th>
                    <th>Left</th>
                    <th>next payement</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>car loan</td>
                    <td>8000$</td>
                    <td>3500$</td>
                    <td>2022-07-31</td>
                    <td><input type="submit" name="name" value="Pay now"></td>
                  </tr>
                  <tr>
                    <td>Education</td>
                    <td>8000$</td>
                    <td>3500$</td>
                    <td>2022-07-31</td>
                    <td><input type="submit" name="name" value="Pay now"></td>
                  </tr>
                  <tr>
                    <td>car loan</td>
                    <td>8000$</td>
                    <td>3500$</td>
                    <td>2022-07-31</td>
                    <td><input type="submit" name="name" value="Pay now"></td>
                  </tr>
                  <tr>
                    <td>Education</td>
                    <td>8000$</td>
                    <td>3500$</td>
                    <td>2022-07-31</td>
                    <td><input type="submit" name="name" value="Pay now"></td>
                  </tr>

                </tbody>
              </table>

            </form>
          </div>
          <div class="Checks-History">
            <h2>Loans History</h2>
          </div>
        </div>
      </div>     
      <script src="js/sidebar-script.js"></script>
      <script>
        $(document).ready(function () {
          $('#checkdate').val(new Date().toJSON().slice(0, 10));

          if ($.cookie('langue') !== null) {
            if ($.cookie('langue') === 'ar') {
              locale = ar;
              console.log($.cookie('langue'));
            } else if ($.cookie('langue') === 'fr') {
              locale = fr;
            }
          }
          oTable = $('.ActiveLoansTable').DataTable({
            dom: 't',
            responsive: true,
            "aaSorting": [[0, "desc"]],
            "columnDefs": [
              {"className": "dt-center", "targets": "_all"}
            ],
            scrollY: '200px',
            scrollCollapse: true,
            paging: false

          });
        });


      </script>
    </fmt:bundle>

  </body>
</html>