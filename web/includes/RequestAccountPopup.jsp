<%-- 
    Document   : RequestAccountPopup
    Created on : 1 août 2022, 13:23:30
    Author     : Samir
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="request_account popup" style="display: none;">
  <form action="RequestAccount" method="post">
    <h2 style="text-align: center;">
      <fmt:message key="title.h2.newAccount"/>
    </h2>

    <table>
      <tr>
        <td>
      <fmt:message key="text.AccountName"/>
      </td>
      <td>
        <input type="text" id="request_account_name" name="accountnamerequest" required>
      </td>
      </tr>
      <tr>
        <td>
          <label for="currency">
            <fmt:message key="label.currency"/>
          </label>
        </td>
        <td><select name="currencyrequest" id="currencyrequest" required>
            <option value=""><fmt:message key="option.selectcurrency"/></option>
            <c:forEach items= "${currencies}" var="currency">
              <option value="${currency.getCode()}">${currency.getSymbol()}</option>
            </c:forEach>
            
          </select>
        </td>
      </tr>
      <tr>
        <td><fmt:message key="text.AccountType"/></td>
      <td>
        <select name="AccountTypeRequest" id="account_type" onchange="SelectedAccountType(this.value)">
          <option value=""><fmt:message key="option.chooseOption"/></option>
          <option value="saving"><fmt:message key="option.savingsAccount"/></option>
          <option value="checking"><fmt:message key="option.checkingaccount"/></option>
        </select>
      </td>
      </tr>

      <tr class="row_savings request" style="display: none;">
        <td><fmt:message key="text.savingGoal"/></td>
      <td>
        <input type="number" min="1000" max="1000000" maxlength="7" 
               oninput="this.value=this.value.slice(0,this.maxLength)" 
               class="saving_goal_request"  name="saving_goal_request">
      </td>
      </tr>
      <tr class="row_checking request" style="display: none;">
        <td><fmt:message key="text.budgetPlan"/></td>
      <td>
        <select name="budget_period_request" id="budget_request">
          <option value=""><fmt:message key="option.chooseOption"/></option>
          <option value="Daily"><fmt:message key="option.daily"/></option>
          <option value="Weekely"><fmt:message key="option.weekely"/></option>
          <option value="Monthly"><fmt:message key="option.monthly"/></option>
          <option value="Yearly"><fmt:message key="option.yearly"/></option>
        </select>
      </td>
      </tr>
      <tr class="row_checking" style="display: none;">
        <td>
      <fmt:message key="text.budgetAmount"/>
      </td>
      <td>
        <input type="number" name="budgetamountrequest" id="budget_amount_request" min="0">
      </td>
      </tr>
      <tr>
        <td><input type="submit" value="Requst account"></td>
        <td>
          <button type="button" class=".cancel_btn" onclick="colseAccountRequest()">
            <fmt:message key="text.cancel"/>
          </button>
        </td>
      </tr>
    </table>
  </form>
</div>