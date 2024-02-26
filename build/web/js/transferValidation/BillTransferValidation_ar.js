/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


var validator = $("#BillFrom").validate({
  errorClass: "error fail-alert",
  validClass: "valid success-alert",
  rules: {
    BillerAccount: {
      remote: {
        url: "BillTransferValidation",
        type: "post",
        data: {
          BillerAccount: function () {
            return $('#BillerAccount').val();
          }
        },

        dataFilter: function (response) {
          var result = JSON.parse(response);
          if (result.isError === false) {
            $('#RIBorIBAN').val(result.RIBorIBAN);
            return true;
          }
          return "\"" + result.ARMessage + "\"";
        }


      }
    }
  }
});

