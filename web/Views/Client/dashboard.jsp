<%-- 
    Document   : profile
    Created on : 9 juil. 2022, 08:36:37
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../includes/taglibs.jsp"%>
<%@page import="java.math.RoundingMode"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/sidebar-style.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">

    <script src="js/jquery/jquery-3.6.0.min.js"></script>

    <script src="js/jquery/jquery.validate.min.js"></script>

    <script src="js/jquery/jquery.cookie.js"></script>

    <script defer src="js/sidebar-script.js"></script>

    <script src="js/chartjs/dist/chart.js"></script>
    <script defer src="js/dashboard.js"></script>

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
        .arrow{
          position: absolute;
          top: 50%;
          right: auto;
          left: 0;
          transform: translate(25%,-60%);
        }
      </style>
    </c:if>

    <fmt:bundle basename="i18n.Bundle">
      <title><fmt:message key="text.dashboard"/></title></fmt:bundle>
    </head>
    <body>
      <div class="main">
      <fmt:bundle basename="i18n.Bundle">

        <%@ include file="../../includes/sidebar.jsp"%>

        <div class="popup_div"></div>

        <div class="home_content">

          <div class="statistiques">

            <div class="outcome" style="grid-area: outcome">
              <h2>${outcome.setScale(2, RoundingMode.FLOOR)} ${lc}</h2>
              <h5><fmt:message key="text.outcome"/></h5>
              <div class="arrow">
                <i class="fa-solid fa-circle-arrow-up"></i></div>
            </div>
            <div class="income" style="grid-area: income"> 
              <h2>${income.setScale(2, RoundingMode.FLOOR)} ${lc}</h2>
              <h5><fmt:message key="text.income"/></h5>
              <div class="arrow">
                <i class="fa-solid fa-circle-arrow-down"></i>
              </div>
            </div>

            <div class="foo" style="grid-area: foo">

              <div class="pie-chart">
                <canvas id="last_month_expenses"></canvas>

                <span class="percentage">${income_per}%</span>
              </div >
            </div>

            <div class="total_savings">

              <h2 style="width: 100%"><fmt:message key="title.h2.savingstats"/></h2>
              <div class="charts">

                <div class="pie-chart">
                  <canvas id="savings_total_percentage"></canvas>

                  <span class="percentage">${data}%</span>
                </div >

                <div class="bar">
                  <canvas id="savings_percentage"></canvas>
                </div>
              </div>

            </div>

            <div class="total_budget">

              <h2 style="width: 100%"><fmt:message key="title.h2.checkingstats"/></h2>
              <div class="charts">

                <div class="pie-chart">
                  <canvas id="budget_total_percentage"></canvas>
                  <span class="percentage">${data2}%</span>
                </div >

                <div class="bar">
                  <canvas id="budget_percentage"></canvas>
                </div>
              </div>


            </div>
          </div>
        </div>

      </div>
      <script>

        var ctx = document.getElementById("savings_total_percentage");
        var myChart = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: ['<fmt:message key="chart.legend.saved"/>', '<fmt:message key="chart.legend.left"/>'],
            datasets: [
              {
                label: '',
                data: [],
                backgroundColor: [
                  'rgba(10, 99, 255, 0.5)',
                  '#888'
                ], borderWidth: 0 //this will hide border
              }]
          },
          options: {
            cutout: 60,
            plugins: {
              legend: {
                display: true, position: 'bottom'
              },
              title: {
                display: true,
                text: '<fmt:message key="chart.title.totalAccountsSavings"/>'
              }
            },
            maintainAspectRatio: false,
            responsive: true,
            layout: {
              padding: 10
            },
            ////events: []

          }
        });

        var data = '${data}';
        myChart.data.datasets[0].data = [data, 100 - data];

        // animate update of 'March' from 90 to 50.
        myChart.update();

        var bar = document.getElementById("savings_percentage");
        var barchart = new Chart(bar, {
          type: 'bar',
          data: {
            labels: [],
            datasets: [
              {
                label: '<fmt:message key="chart.legend.saved"/>',
                data: [],
                backgroundColor: [
                  'rgba(10, 99, 255, 0.5)'
                ],
                borderWidth: 0 //this will hide border
              }, {
                label: '<fmt:message key="chart.legend.left"/>',
                data: [],
                backgroundColor: [
                  '#888'
                ],
                borderWidth: 0 //this will hide border
              }
            ]
          },

          options: {

            plugins: {
              legend: {
                display: true, position: 'bottom'
              },
              title: {
                display: true,
                text: '<fmt:message key="chart.title.AccountsSavings"/>'
              }

            },
            maintainAspectRatio: false,
            responsive: true,
            ////events: [],
            indexAxis: 'y',
            // Elements options apply to all of the options unless overridden in a dataset
            // In this case, we are setting the border of each horizontal bar to be 2px wide
            elements: {
              bar: {
                borderWidth: 2
              }
            }

          }
        });

        var label = [];
        var values = [];
        var left_values = [];
        <c:forEach var="entry" items="${map}" varStatus="i">
        var key = '${entry.key}';
        var value = '${entry.value}';
        label[${i.index}] = key;
        values[${i.index}] = value;
        </c:forEach>

        <c:forEach var="entry" items="${map}" varStatus="i">
        var key = '${entry.key}';
        var value = '${entry.value}';
        label[${i.index}] = key;
        left_values[${i.index}] = 100 - value;
        </c:forEach>
        barchart.data.labels = label;
        barchart.data.datasets[0].data = values;
        barchart.data.datasets[1].data = left_values;

        // animate update of 'March' from 90 to 50.
        barchart.update();


        var ctx = document.getElementById("budget_total_percentage");
        var myChart1 = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: ['<fmt:message key="chart.legend.spent"/>', '<fmt:message key="chart.legend.left"/>'],
            datasets: [{
                label: '',
                data: [],
                backgroundColor: [
                  'rgba(255, 99, 10, 0.5)',
                  'rgba(10, 99, 255, 0.5)'
                ], borderWidth: 0 //this will hide border
              }]
          },
          options: {
            cutout: 60,
            plugins: {
              legend: {
                display: true, position: 'bottom'
              },
              title: {
                display: true,
                text: '<fmt:message key="chart.title.TotalAccountsExpenses"/>'
              }
            },
            maintainAspectRatio: false,
            responsive: true,
            layout: {
              padding: 10
            },
            ////events: []

          }
        });

        var data2 = '${data2}';
        myChart1.data.datasets[0].data = [100 - data2, data2];

        // animate update of 'March' from 90 to 50.
        myChart1.update();

        var bar1 = document.getElementById("budget_percentage");
        var barchart1 = new Chart(bar1, {
          type: 'bar',
          data: {
            labels: [],
            datasets: [{
                label: '<fmt:message key="chart.legend.left"/>',
                data: [],
                backgroundColor: [
                  'rgba(10, 99, 255, 0.5)'
                ],
                borderWidth: 0 //this will hide border
              },
              {
                label: '<fmt:message key="chart.legend.spent"/>',
                data: [],
                backgroundColor: [
                  'rgba(255, 99, 10, 0.5)'
                ],
                borderWidth: 0 //this will hide border
              }
            ]
          },
          options: {

            plugins: {
              legend: {
                display: true, position: 'bottom'
              },
              title: {
                display: true,
                text: '<fmt:message key="chart.title.CheckingAccountsExpenses"/>'
              }

            },
            maintainAspectRatio: false,
            responsive: true,
            ////events: [],
            indexAxis: 'y',
            // Elements options apply to all of the options unless overridden in a dataset
            // In this case, we are setting the border of each horizontal bar to be 2px wide
            elements: {
              bar: {
                borderWidth: 2
              }
            }

          }
        });
        var label = [];
        var values = [];
        var expenses_values = [];

        <c:forEach var="entry" items="${checking_map}" varStatus="i">
        var key = '${entry.key}';
        var value = '${entry.value}';
        label[${i.index}] = key;
        values[${i.index}] = value;

        </c:forEach>

        <c:forEach var="entry" items="${checking_map}" varStatus="i">
        var key = '${entry.key}';
        var value = '${entry.value}';
        label[${i.index}] = key;
        expenses_values[${i.index}] = 100 - value;
 
        </c:forEach>

        barchart1.data.labels = label;
        barchart1.data.datasets[1].data = expenses_values;
        barchart1.data.datasets[0].data = values;


        // animate update of 'March' from 90 to 50.
        barchart1.update();


        var ctx = document.getElementById("last_month_expenses");
        var LastMonth = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: ['<fmt:message key="text.income"/>', '<fmt:message key="text.outcome"/>'],
            datasets: [
              {
                label: '',
                data: [],
                backgroundColor: [
                  'rgba(10, 99, 255, 0.5)',
                  '#888'
                ], borderWidth: 0 //this will hide border
              }]
          },
          options: {
            cutout: 160,
            plugins: {
              legend: {
                display: true, position: 'bottom'
              },
              title: {
                display: true,
                text: ' <fmt:message key="chart.legend.income/outcome"/>'
              }
            },
            maintainAspectRatio: false,
            responsive: true,
            layout: {
              padding: 15
            }
            //events: []

          }
        });

        var data = '${income_per}';
        LastMonth.data.datasets[0].data = [data, 100 - data];
        LastMonth.update();
      </script>
    </fmt:bundle>
  </body>

</html>
