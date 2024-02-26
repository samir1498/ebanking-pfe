<%-- 
    Document   : OrganisationForm
    Created on : 19 juil. 2022, 17:16:30
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<form class="organisation-form" action="OrganizationRegistration"  method="post" enctype="multipart/form-data" style="display: none">
  <h1 style="text-align:center"><fmt:message key="title.register"/></h1>

  <table>
    <c:if test="${register_error !=null}">
      <tr>
        <td colspan="2">
          <span class="error" style="display: block" ><fmt:message key="${register_error}"/></span>
        </td>
      </tr>
    </c:if>
    <tr>
      <td><label for="name"><fmt:message key="label.name"/></label></td>
      <td><input name="name" id="name" type="text" minlength="3" required></td>
    </tr>

    <tr>
      <td><label for="Industrie"><fmt:message key="label.industry"/></label></td>
      <td><input type="text" name="Industrie" id="Industrie" required /></td>
    </tr>

    <tr>
      <td><label for="ofdate"><fmt:message key="label.foundingdate"/></label></td>
      <td><input type="date" name="ofdate" id="ofdate" required></td>
    </tr>

    <tr>
      <td><label for="oemail"><fmt:message key="label.email"/></label></td>
      <td><input type="email" name="oemail" id="oemail" required></td>
    </tr>
    <tr>
      <td><label for="ophone"><fmt:message key="label.phonenumber"/></label></td>
      <td class="phone-td"> 
        <input type="tel" id="ophone" name="ophone" minlength="9" required onchange="setPhoneOrganization()">
        <input type="hidden" name="ofull_phone" id="ofull_phone">
      </td>

    </tr>
    <tr>
      <td><label for="ocountry"><fmt:message key="label.nationality"/></label></td>

      <td>
        <select name="oNationalite" id="ocountry" required>
          <option value=""><fmt:message key="option.selectnationality"/></option>
          <option value="Algeria"><fmt:message key="option.Algerian"/></option>
        </select>
      </td>
    </tr>
    <tr>
      <td><label for="oaddress"><fmt:message key="label.address"/></label></td>
      <td><input id="oaddress" type="text" name="oadress" required></td>
    </tr>


    <tr>
      <td><label><fmt:message key="label.file"/></label></td>
      <td><input id="ofile" type="file" name="ofile" accept="image/*,.pdf" multiple required /></td>
    </tr>

    <tr>
      <td><label for="ouserName"><fmt:message key="label.username"/></label></td>
      <td><input id="ouserName" type="text" name="ouserName" required /></td>
    </tr>

    <tr>
      <td><label for="opass"><fmt:message key="label.password"/></label></td>
      <td><input type="Password" id="opass" name="opass" required></td>

    </tr>

    <tr>
      <td><label for="orepass"><fmt:message key="label.confirmpassword"/></label></td>
      <td><input type="Password" id="orepass" name="orepass" required></td>
    </tr>
    
    <tr class="submit">
      <td><input type="submit" value="<fmt:message key="title.register"/>" name="register-organization" class="register-submit" /></td>
    </tr>
  </table>

</form>
