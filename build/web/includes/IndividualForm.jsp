<%-- 
    Document   : IndividualForm
    Created on : 19 juil. 2022, 17:02:26
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- Individual form-->

<form class="individual-form" action="IndividualRegistration" method="post" enctype="multipart/form-data">

  <table>
    <h1><fmt:message key="title.register"/></h1>
    <c:if test="${register_error !=null}">
      <tr>
        <td colspan="2">
          <span class="error" style="display: inherit" ><fmt:message key="${register_error}"/></span>
        </td>
      </tr>
    </c:if>
    <tr>
      <td><label for="firstname"><fmt:message key="label.firstname"/></label></td>
      <td><input name="firstname" type="text" id="firstname" minlength="3" required></td>
    </tr>
    <tr>
      <td><label for="lastname"><fmt:message key="label.lastname"/></label></td>
      <td><input type="text" id="lastname" name="lastname" minlength="3" required></td>
    </tr>
    <tr>
      <td><label><fmt:message key="label.gender"/></label></td>
      <td id="gender">

        <input type="radio" name="gender" id="male" value="male" checked>
        <label for="male"><fmt:message key="label.male"/></label>

        <input type="radio" name="gender" id="female" value="female">
        <label for="female"><fmt:message key="label.female"/></label>

      </td>
    </tr>
    <tr>
      <td><label for="birthdate"><fmt:message key="label.birthdate"/></label></td>
      <td><input type="date" name="birthdate" id="birthdate" required></td>
    </tr>
    <tr>
      <td><label for="birthplace"><fmt:message key="label.birthplace"/></label></td>
      <td><input type="text" id="birthplace" name="birthplace" minlength="5" required></td>
    </tr>
    <tr>
      <td><label for="email"><fmt:message key="label.email"/></label></td>
      <td><input type="email" name="email" id="email" required></td>
    </tr>
    <tr>
      <td><label for="phone"><fmt:message key="label.phonenumber"/></label></td>
      <td class="phone-td">
        <input type="tel" id="phone" name="phone" minlength="9" required onblur="setPhoneIndividual()" onkeyup="setPhoneIndividual()">
        <input type="hidden" name="full_phone" id="full_phone">
      </td>

    </tr>
    <tr>
      <td><label for="country"><fmt:message key="label.nationality"/></label></td>

      <td>
        <select name="Nationalite" id="country" required>
          <option value=""><fmt:message key="option.selectnationality"/></option>
          <option value="Algerian"><fmt:message key="option.Algerian"/></option>
        </select>
      </td>
    </tr>
    <tr>
      <td><label for="address"><fmt:message key="label.address"/></label></td>
      <td><input id="address" type="text" name="adresse" required></td>
    </tr>

    <tr>
      <td><label for="file"><fmt:message key="label.file"/></label></td>
      <td><input id="file" type="file" name="file" accept="image/*,.pdf" multiple required /></td>
    </tr>

    <tr>
      <td><label for="userName"><fmt:message key="label.username"/></label></td>
      <td><input id="userName" type="text" name="userName" required /></td>
    </tr>

    <tr>
      <td><label for="pass"><fmt:message key="label.password"/></label></td>
      <td><input type="Password" id="pass" name="pass" required></td>
    </tr>

    <tr>
      <td><label for="repass"><fmt:message key="label.confirmpassword"/></label></td>
      <td><input type="Password" id="repass" name="repass" required></td>
    </tr>

    <tr class="submit">
      <td><input type="submit" value="<fmt:message key="title.register" />" name="register-individu" class="register-submit" /></td>
    </tr>
  </table>

</form>