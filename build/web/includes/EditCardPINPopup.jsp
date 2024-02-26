<%-- 
    Document   : EditCardPINPopup
    Created on : 12 août 2022, 10:04:30
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="Edit_PIN popup" style="min-width: 22rem">
  <form action="EditCardPIN" method="post" id="link_card_from">
    <h2 style="text-align: center;"><fmt:message key="title.h2.EditPin"/></h2>

    <table>
      <tr>
        <td><fmt:message key="text.pin"/></td>
      <td><input type="number" min="1000" max="9999" maxlength="4" 
               oninput="this.value=this.value.slice(0,this.maxLength)" name="PIN" id="pin" autoFocus></td>
      </tr>
      <tr>
        <td><fmt:message key="text.cpin"/></td>
      <td><input type="number" min="1000" max="9999" maxlength="4" 
               oninput="this.value=this.value.slice(0,this.maxLength)" name="CPIN" id="cpin"></td>
      </tr>
      <tr><td><input type="hidden" name="CardNumber" id="link_card_number"></td></tr>
      <tr>
        <td><input type="submit" value="<fmt:message key="input.Edit"/>"></td>
        <td><button type="button" onclick="closeLinkCardDiv()"><fmt:message key="text.cancel"/></button>
      </tr>
    </table>
  </form>
</div>

<script>
  var validator = $("#link_card_from").validate({
    errorClass: "error fail-alert",
    validClass: "valid success-alert",
    rules: {
      PIN: {
        required: true
      },
      CPIN: {
        required: true,
        equalTo: '#pin'
      }
    }
  });

  //link a card show the card div to be updated when adding java
//add a popup to chosse an exicting card or request a new one

function OpenEditPIN(value) {
    $('.Edit_PIN').show();
    $('#pin').focus();
    $('#link_card_number').val(value);
    blur();
    var expDate = new Date();
    expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
    $.cookie('active_popup_card', "Edit_PIN", {path: '/', expires: expDate});
  }

  function closeLinkCardDiv() {
    validator.resetForm();
    $('.Edit_PIN').hide();
    unblur();
  }
  
  $('#cpin').on('keyup', function () {
  if ($('#pin').val() === $('#cpin').val()) {
    $('#cpin').css("border", "2px solid green");
  } else
    $('#cpin').css("border", "2px solid red");
});
</script>
