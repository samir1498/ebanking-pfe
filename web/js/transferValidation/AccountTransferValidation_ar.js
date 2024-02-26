/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


var validator = $("#transfer_form").validate({
  errorClass: "error fail-alert",
  validClass: "valid success-alert",
  rules: {
    AccountTo: {
      remote: {
        url: "AccountTransferValidation",
        type: "post",
        data: {
          AccountTo: function () {
            return $('#transfer_accountto').val();
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
    },
    transfer_amount: {
      required: true
    },
    descrption: {
      required: true
    }
  }
});

function closeTransferDiv() {
  validator.resetForm();
  $('.transfer_div').hide();
  unblur();
}
