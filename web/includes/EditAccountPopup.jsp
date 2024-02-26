<%-- 
    Document   : EditAccountPopup
    Created on : 1 août 2022, 13:33:11
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="edit_account_div popup" style="display: none;">
  <form action="EditAccount" method="post">
    <h2 style="text-align: center;"><fmt:message key="title.h2.editAccount"/></h2>
    <table>
      <tr>
        <td><fmt:message key="text.AccountName"/></td>
      <td><input type="text" name="accountnameedit" id="edit_account_name"></td>
      </tr>
      <tr>
        <td><label for="currencyedit"><fmt:message key="label.currency"/></label></td>
        <td><select name="currencyedit" id="currencyedit" required>
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
        <select name="AccountTypeedit" id="account_type_edit" onchange="SelectedAccountType(this.value)">
          <option value=""><fmt:message key="option.chooseOption"/></option>
          <option value="saving"><fmt:message key="option.savingsAccount"/></option>
          <option value="checking"><fmt:message key="option.checkingAccount"/></option>
        </select>
      </td>
      </tr>
      <tr class="row_savings" style="display: none;">
        <td><fmt:message key="text.savingGoal"/></td>
      <td>
        <input type="number" min="1000" max="1000000" maxlength="7" 
               oninput="this.value=this.value.slice(0,this.maxLength)" 
               class="saving_goal_edit" id="saving_goal_edit" name="saving_goal_edit">
      </td>
      </tr>
      <tr class="row_checking" style="display: none;">
        <td><fmt:message key="text.budgetPlan"/></td>
      <td>
        <select name="budget_period" id="budget_period_edit">
          <option value=""><fmt:message key="option.chooseOption"/></option>
          <option value="Daily"><fmt:message key="option.daily"/></option>
          <option value="Weekely"><fmt:message key="option.weekely"/></option>
          <option value="Monthly"><fmt:message key="option.monthly"/></option>
          <option value="Yearly"><fmt:message key="option.yearly"/></option>
        </select>
      </td>
      </tr>
      <tr class="row_checking" style="display: none;">
        <td><fmt:message key="text.budgetAmount"/></td>
      <td><input type="number" name="budget_amount" id="budget_amount" min="0"></td>
      </tr>
      <tr>
        <td><input type="submit" value="Edit account">
          <input type="hidden" id="account_to_edit" name="account_to_edit"></td>
        <td>
          <button type="button" class=".cancel_btn" onclick="closeAccountEdit()">
            <fmt:message key="text.cancel"/>
          </button>
        </td>
      </tr>
    </table>
  </form>
</div>
