<%-- 
    Document   : AccountTransferPopup
    Created on : 1 août 2022, 16:41:31
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- transfer popup -->
<div class="card_transfer_div popup" style="display: none;">
  <form action="TransactionByCard" method="post" id="transfer_form">
    <h2 style="text-align: center;">
      <fmt:message key="title.h2.transfer"/>
    </h2>
    <table>
      <tr>
        <td>
          <label for="AccountTo">
            <fmt:message key="text.accountTo"/>
          </label>

        </td>
        <td>
          <input type="text" name="AccountTo" id="transfer_accountto" required>
        </td>
      </tr>

      <tr>
        <td><fmt:message key="text.payee"/></td>
      <td>
        <input type="text" name="FullName" id="fullname" required>
      </td>
      </tr>

      <tr>
        <td><fmt:message key="text.amount"/></td>
      <td>
        <input type="number" name="transferAmount" step="0.01" id="transfer_amount" min="0.01" required>
      </td>
      </tr>
      <tr>
        <td>
          <label for="description_area"><fmt:message key="text.Description"/></label>
        </td>
        <td>
          <textarea id="descrption" name="description_area" rows="3" style="resize: none;" required></textarea>
          <input type="hidden" name="AccountFrom" id="transfer_accountfrom">
          <input type="hidden" name="CardFrom" id="transfer_cardfrom">
          <input type="hidden" name="RIBorIBAN" id="RIBorIBAN">
        </td>
      </tr>
      <c:if test="${transfer_error !=null and transfer_error !='SUCCES'}">
        <tr>
          <td colspan="2">
            <span class="error" style="display: block; width: 100%" ><fmt:message key="${transfer_error}"/></span>
          </td>
        </tr>
      </c:if>
      <tr>
        <td>
          <button type="button" onclick="closeTransferDiv()">
            <fmt:message key="text.cancel"/>
          </button>
        </td>
        <td>
          <input type="submit" style="background: green" value="<fmt:message key="text.send"/>" >
        </td>

      </tr>

    </table>
  </form>
</div>


<div class="succes_transfer popup" style="display: none">
  <i class="fa-solid fa-circle-check"></i>
  <button type="button" onclick="closeSuccesDiv()">Ok</button>
</div>