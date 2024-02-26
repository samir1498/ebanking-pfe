<%-- 
    Document   : RequestCardPopup
    Created on : 12 août 2022, 09:51:07
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="link_card popup" style="min-width: 22rem">
  <form action="Cards" method="post" id="link_card_from">
    <h2 style="text-align: center;"><fmt:message key="title.h2.requestCard"/></h2>

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
      <tr><td><input type="hidden" name="id_account" id="link_card_id_account"></td></tr>
      <tr>
        <td><input type="submit" value="Request"></td>
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

  function closeLinkCardDiv() {
    validator.resetForm();
    $('.link_card').hide();
    unblur();
  }
  
  $('#cpin').on('keyup', function () {
  if ($('#pin').val() === $('#cpin').val()) {
    $('#cpin').css("border", "2px solid green");
  } else
    $('#cpin').css("border", "2px solid red");
});
</script>
